(ns htmlparser2hiccup.core
  (:require [htmlparser2 :as h2]))

(enable-console-print!)

(defn- open-tag
  [tag attrs]
  (println "TAG: " tag (js->clj attrs :keywordize-keys true)))

(defn- close-tag
  [tag]
  (println "END: " tag))

(defn- text
  [text]
  (println "TEXT: " text))

(def parser (h2/Parser. #js {"onopentag" open-tag
                             "onclosetag" close-tag
                             "ontext" text}))

(.write parser "<p>bla <a href=\"http://example.com\">link</a></p>")

