(ns htmlparser2hiccup.tests
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]
            [htmlparser2hiccup.core :refer [html->hiccup]]
            [cljs-node-io.core :as io]
            [hiccups.runtime :as hiccupsrt]
            [clojure.spec.alpha :as s]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop :include-macros true])
  (:require-macros [hiccups.core :as hiccups :refer [html]]))


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
    (println v)
    (= (sort v) (sort (sort v)))))

(tc/quick-check 10 sort-idempotent-prop)


(deftest roundtrip-stable
  (let [hicc [:p {} [:a {:href "http://google.com"}]]]
    (is (= hicc
           (html->hiccup (html hicc))))))



(comment

  (require '[cljs.repl :as repl])
  (require '[cljs.repl.node :as node])
  (def env (node/repl-env))
  (repl/repl env) 

  (println "*"))


