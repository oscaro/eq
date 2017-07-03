(require '[cljs.repl])
(require '[cljs.repl.node])

(cljs.repl/repl 
  (cljs.repl.node/repl-env)
  :watch "src"
  :output-dir "out-repl"
  :repl-requires '[[cljs.nodejs :as node]
                   [cljs.core.async :refer [put! chan <! >!]]]) ; throw in some stock core.async for good measure
