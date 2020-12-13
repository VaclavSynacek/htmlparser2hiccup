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


(def childless? #{:img :hr})
(def recursive-block? #{:div})
(def final-block? #{:h1 :h2 :h3 :p})
(def inline? #{:span :b :i :em :strong})


(s/def ::childless-node
  (s/tuple childless?))

(s/def ::terminal-node
  (s/or ::text0 string?
        ::text1 string?
        ::text2 string?
        ::text3 string?
        ::text4 string?
        ::text5 string?
        ::simple ::childless-node))

(def inline-gen
  (gen/recursive-gen
    (fn [inner] (gen/one-of [(gen/tuple (s/gen inline?) inner)
                             (gen/tuple (s/gen inline?) inner inner)
                             (gen/tuple (s/gen inline?) inner inner inner)
                             (gen/tuple (s/gen inline?) inner inner inner inner)
                             (gen/tuple (s/gen inline?) inner inner inner inner inner)]))
    (s/gen ::terminal-node)))


(def recursive-block-gen
  (gen/recursive-gen
    (fn [inner] (gen/one-of [(gen/tuple (s/gen recursive-block?) inner)
                             (gen/tuple (s/gen recursive-block?) inner inner)]))
    inline-gen))

(def final-block-gen
  (gen/tuple (s/gen final-block?)
             (gen/one-of [recursive-block-gen
                          inline-gen
                          (s/gen ::terminal-node)])))


(def full-body-gen
  (gen/vector final-block-gen 1 10
              final-block-gen 1 10))


(s/def ::inline-node
    (s/cat
      :tag inline?
      :children (s/+ (s/or ::terminal-node
                           ::inline-node))))

(def s (gen/generate inline-gen))
(cljs.pprint/pprint s)
(s/valid? ::inline-node s)

(s/explain ::inline-node s)


(cljs.pprint/pprint (gen/generate full-body-gen))






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


