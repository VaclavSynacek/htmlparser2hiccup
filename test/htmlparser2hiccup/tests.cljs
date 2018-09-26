(ns htmlparser2hiccup.tests
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]
            [htmlparser2hiccup.core :refer [html->hiccup]]
            [cljs-node-io.core :as io]
            [clojure.spec.alpha :as s]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop :include-macros true]))


(deftest by-example
  (is (= (html->hiccup 
           "<p>bla<a href=\"http://example.com\">link</a><img src=\"bla.png\"></p>")
         [:p {} "bla"
             [:a {:href "http://example.com"} "link"]
             [:img {:src "bla.png"}]])))

(deftest multifragment-parse-only-first
  (is (= (html->hiccup 
           "<p>first paragraph</p><p>second paragraph</p>")
         [:p {} "first paragraph"])))

(enable-console-print!)

(cljs.test/run-tests)



(s/valid? string? 123)

(def sort-idempotent-prop
  (prop/for-all [v (gen/vector gen/int)]
    (= (sort v) (sort (sort v)))))

(tc/quick-check 1000 sort-idempotent-prop)

(comment

  (require '[cljs.repl :as repl])
  (require '[cljs.repl.node :as node])
  (def env (node/repl-env))
  (repl/repl env) 

  (println "*"))


