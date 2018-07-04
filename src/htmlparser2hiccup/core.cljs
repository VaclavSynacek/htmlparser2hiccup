(ns htmlparser2hiccup.core
  (:require [htmlparser2 :as h2]
            [clojure.string :as string]))

(enable-console-print!)


(defn html->hiccup
  [html-text]
  (let [result-accumulator (goog.string/StringBuffer.)
        open-tag (fn [tag attrs]
                     (.append result-accumulator
                              " [:"
                              tag
                              " "
                              (js->clj attrs :keywordize-keys true)))
        close-tag (fn [tag]
                     (.append result-accumulator
                              "]"))
        text (fn [text]
                     (.append result-accumulator
                              " "
                              (pr-str text)))
        parser (h2/Parser. #js {"onopentag" open-tag
                               "onclosetag" close-tag
                               "ontext" text})]
        (.write parser html-text)
        (string/trim (str result-accumulator))))

(def t "<p>bla<a href=\"http://example.com\">link</a></p>")
(println (html->hiccup t))
