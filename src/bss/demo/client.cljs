(ns bss.demo.client
  (:require [bss.demo.messages :refer [message-component]]
            [cemerick.url :refer [url]]
            [chord.client :refer [ws-ch]]
            [cljs.core.async :refer [chan <! >! put! close! timeout sliding-buffer pipe]]
            [flow.core :as f :include-macros true])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]]))

(enable-console-print!)

(defn receive-msgs!
  "Read from server, appending anything we get to the given msgs atom-list"
  [*msgs* server-ch]
  (go-loop []
    (when-let [msg (<! server-ch)]
      (swap! *msgs* conj msg)
      (recur))))

(defn get-ws-url
  "Parses a ws:// url based on the current page location and a given relative path"
  [path]
  (-> js/window
      .-location
      .-href
      url
      (assoc :protocol "ws")
      (assoc :path path)
      (assoc :query nil)
      (assoc :anchor nil)
      str))

(defn print-error [msg]
  (f/root
    js/document.body
    (f/el [:div "Websocket connection failed: " (pr-str msg)])))

(defn start-app [ws-channel]
  (let [*msgs* (atom [])
        user-input (chan (sliding-buffer 10))]

    ;; Listed to everything the server sends, posting to *msgs*
    (receive-msgs! *msgs* ws-channel)

    ;; Pipe user input to the web socket channel
    (pipe user-input ws-channel)

    ;; Show UI
    (f/root
      (.getElementById js/document "content")
      (f/el [message-component *msgs* user-input]))))

(defn connect []
  (go
    (let [{:keys [ws-channel error]} (<! (ws-ch (get-ws-url "/ws") {:format :json}))]
      (if error
        (print-error error)
        (start-app ws-channel)))))

(set! (.-onload js/window) connect)

