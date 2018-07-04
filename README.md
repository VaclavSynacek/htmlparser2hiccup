# htmlparser2hiccup

Extremely naive html->hiccup parser based on htmlparser2.

## Overview

 - Runs on nodejs (unlike [hickory](https://github.com/davidsantiago/hickory) or clj-only solutions).
 - Compatible with [hiccup](https://github.com/weavejester/hiccup).
 - Easy to read and understand source - under 20 LOC.
 - Extremely naive implementation that can probably fail in hundred ways:
   - loads [htmlparser2](https://www.npmjs.com/package/htmlparser2)
   - parses html into string(buffer) representing the hiccup datastructure
   - parses the string into clojure(script) datastructure with clj.reader/read-string (all in memory, no streaming, probably fails on large html).
 - Pull requests to make this a little bit more robust are welcome. However for serious
   production usage either use hickory or if you need serious implementation
   that runs on nodejs, conside contributing to [hiccory pull request #33](https://github.com/davidsantiago/hickory/pull/33) first.

## Usage

```clojure
   (:require [htmlparser2hiccup :as h2])
   (h2/html->hiccup "<p class=\"nice-text\">First paragraph</p>)
   ;; => [:p {:class "nice-text"} "First paragraph"]
```

**Only** pass html fragments that are wrapped in one top level element. If you
pass more top level elemets (such as `<p>one</p><p>two</p>` then the rest is
ignored.

## License

Copyright © 2018 Vaclav Synacek
BSD License
