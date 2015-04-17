(defproject jandorfer/basic-signaling-service-demo "0.1.0-SNAPSHOT"
  :description "A basic message exchange to demo the basic-signaling-service project functionality."
  :url "https://github.com/jandorfer/basic-signaling-service-demo"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[com.cemerick/url "0.1.1"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.4"]
                 [jandorfer/basic-signaling-service "0.1.0-SNAPSHOT"]
                 [jarohen/chord "0.6.0"]
                 [jarohen/flow "0.3.0-alpha3"]
                 [org.clojure/clojure "1.7.0-beta1"]
                 [org.clojure/clojurescript "0.0-2727"]
                 [org.omcljs/om "0.8.8"]
                 [ring/ring-core "1.2.0"]]
  :exclusions [org.clojure/clojure]
  :plugins [[lein-cljsbuild "1.0.4"]]
  :hooks [leiningen.cljsbuild]
  :cljsbuild {
    :builds [{
      :source-paths ["src"]
      :compiler {
        :output-to "target/resources/js/bss.js"
        :optimizations :whitespace
        :pretty-print true}}]}
  :resource-paths ["target/resources"]
  :main bss.demo.server)
