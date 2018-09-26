(defproject org.clojars.vaclavsynacek/htmlparser2hiccup "0.1.0-SNAPSHOT"
  :description "Extremely naive html->hiccup parser based on htmlparser2"
  :url "https://github.com/VaclavSynacek/htmlparser2hiccup"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [cljs-node-io "1.1.2"]
                 [hiccups "0.3.0"]
                 [org.clojure/test.check "0.9.0"]]

  :plugins [[lein-npm "0.6.2"]
            [lein-cljsbuild "1.1.7"]
            [lein-marginalia "0.9.1"]
            [lein-auto "0.1.3"]]

  :npm {:dependencies [[source-map-support "0.4.0"]
                       [htmlparser2 "3.9.2"]]}

  :clean-targets ["target"]

  :cljsbuild
    {:builds
      [{:id "lib"
        :source-paths  ["src"]
        :compiler {
                   :main          htmlparser2hiccup.core
                   :output-dir    "target/out"
                   :output-to     "target/htmlparser2hiccup.js"
                   :target        :nodejs
                   :optimizations :none}}
       {:id "test"
        :compiler {
                   :main          htmlparser2hiccup.tests
                   :output-dir    "target/test/out"
                   :output-to     "target/test/tests.js"
                   :target        :nodejs
                   :optimizations :none}}]})
