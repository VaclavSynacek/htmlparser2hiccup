(require '[cljs.build.api :as b])

(b/watch "src"
  {:main 'htmlparser2hiccup.core
   :output-to "out/htmlparser2hiccup.js"
   :output-dir "out"})
