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
(def block? #{:div :h1 :h2 :h3})
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

(s/def ::inline-node
  (s/or
    :c1 (s/tuple inline?
                 (s/or ::inline ::inline-node ::terminal ::terminal-node))
    :c2 (s/tuple inline?
                 (s/or ::inline ::inline-node ::terminal ::terminal-node)
                 (s/or ::inline ::inline-node ::terminal ::terminal-node))))
;    :c3 (s/tuple inline?
;                 (s/or ::inline ::inline-node ::terminal ::terminal-node)
;                 (s/or ::inline ::inline-node ::terminal ::terminal-node)
 ;                (s/or ::inline ::inline-node ::terminal ::terminal-node))))

(s/def ::block-node
  (s/or
    :c1 (s/tuple block?
                 (s/or ::inline ::inline-node ::inner-block ::block-node))
    :c2 (s/tuple block?
                 (s/or ::inline ::inline-node ::inner-block ::block-node)
                 (s/or ::inline ::inline-node ::inner-block ::block-node))
    :c3 (s/tuple block?
                 (s/or ::inline ::inline-node ::inner-block ::block-node)
                 (s/or ::inline ::inline-node ::inner-block ::block-node)
                 (s/or ::inline ::inline-node ::inner-block ::block-node))))


(s/def ::block-node
  (s/or
    :c0 (s/tuple block?)
    :c1 (s/tuple block? ::node)
    :c2 (s/tuple block? ::node ::node)
    :c3 (s/tuple block? ::node ::node ::node)
    :c4 (s/tuple block? ::node ::node ::node ::node)))



(gen/recursive-gen (fn [inner] (gen/one-of [(gen/vector inner)
                                            (gen/map inner inner)]))
                  (gen/one-of [gen/boolean gen/int]))



(binding
  [s/*recursion-limit* 3])


(gen/sample (s/gen ::inline-node))

(cljs.pprint/pprint (gen/generate (s/gen ::block-node)))

(binding
  [s/*recursion-limit* 1]
  (cljs.pprint/pprint (gen/generate (s/gen ::childless-node))))

(binding
  [s/*recursion-limit* 1]
  (cljs.pprint/pprint (gen/generate (s/gen ::terminal-node))))

(binding
  [s/*recursion-limit* 3])

(cljs.pprint/pprint (gen/generate (s/gen ::inline-node)))



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


