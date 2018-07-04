(ns htmlparser2hiccup.core
  "Extrmely naive html->hiccup parser based on htmlparser2"
  (:require [htmlparser2 :as h2]
            [cljs.reader :as reader]))


(defn html->hiccup
  "Parses html string to normalized hiccup-compatible data structure.
  Even if tag has no attributes, attribute map is present (empty map in that case)."
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
        (reader/read-string (str result-accumulator))))


