(ns bss.demo.server
  (:require [bss.signaling :refer [signaling-channel]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [resources]]
            [hiccup.page :refer [html5 include-js]]
            [org.httpkit.server :refer [run-server]]
            [ring.util.response :refer [response]]))

(defn index
  "Simple HTML page that just pulls in the compiled cljs file."
  []
  (html5 [:head
           [:title "BSS Sample"]
           (include-js "/js/bss.js")]
         [:body {:style "font-family: verdana, sans-serif; font-size: 14px;"}
           [:div#content]
           [:div {:style "position:fixed;top:10px;right:10px;width:300px;background:#ddd;padding:2em;"}
            "Commands:"
            [:ul
             [:li "/join [channel]"]
             [:li "/leave [channel]"]
             [:li "/send [channel] [message]"]
             [:li "/default [channel]"]
             [:li "[message]"]]]]))

(defroutes routes
  (GET "/" [] (response (index)))
  (GET "/ws" [] signaling-channel)
  (resources "/js" {:root "js"}))

(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    ;; graceful shutdown: wait 100ms for existing requests to be finished
    ;; :timeout is optional, when no timeout, stop immediately
    (@server :timeout 100)
    (reset! server nil)))

(defn -main [& args]
  ;; The #' is useful when you want to hot-reload code
  ;; You may want to take a look: https://github.com/clojure/tools.namespace
  ;; and http://http-kit.org/migration.html#reload
  (reset! server (run-server #'routes {:port 8080})))