(defproject htmlparser2hiccup "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.238"]]
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
          :optimizations :none}}]})
