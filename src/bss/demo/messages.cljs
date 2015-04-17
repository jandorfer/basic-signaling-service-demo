(ns bss.demo.messages
  (:require [cljs.core.async :refer [put!]]
            [clojure.string :as str]
            [flow.core :as f :include-macros true]))

;; Tracks which topic the user is chatting to by default
(def *active-channel* (atom ""))

(defn handle-input!
  "Takes some user-typed string and figures out what to send to the server.

  Commands:
    /join x       => joins the channel named x
    /leave x      => leaves the channel named x
    /send x y     => sends message y to channel x
    /default x => sets the default channel to x

  Anything else is interpreted as messages to send to the default channel."
  [ch input]
  (let [parts (str/split input #" ")
        cmd (first parts)
        data (str/join " " (rest parts))]
    (case cmd
      "/join" (put! ch {:type :sub :topic data})
      "/leave" (put! ch {:type :unsub :topic data})
      "/send" (put! ch {:type :pub :topic (second parts) :message (str/join " " (rest (rest parts)))})
      "/default" (reset! *active-channel* data)
      (put! ch {:type :pub :topic @*active-channel* :message input}))))

(defn on-enter [f]
  (fn [e]
    (when (= 13 (.-keyCode e))
      (let [target (.-target e)]
        (f (.-value target))
        (set! (.-value target) ""))
      (.preventDefault e))))

(defn text-input
  "Creates a text input that when enter is pressed calls the given fn with
  what the user had written, then resetting the input."
  [f]
  (f/el
    [:input {:type "text", :size 50, :autofocus true
             ::f/on {:keyup (on-enter f)}}]))

(defn message-list [*msgs*]
  (f/el
    [:div
      (if-let [msgs (seq (<< *msgs*))]
        (for [{msg :message} msgs]
          (if msg
            [:pre (str/join " " ["[" (get msg "topic") "]" (get msg "data")])]
            [:div [:i "Server disconnected."]]))
        [:pre "No messages received."])]))

(defn message-component [*msgs* input-chan]
  (f/el
    [:div
     [text-input (partial handle-input! input-chan)]
     [message-list *msgs*]]))
