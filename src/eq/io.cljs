(ns eq.io
  (:require [clojure.string :as cs]))

(def fs (js/require "fs"))

;; The line-seq implementation below comes from the following gist:
;;   https://gist.github.com/bostonou/a54c029fa6f29459eafe
(defn- read-chunk [fd]
  (let [length 128
        b (js/Buffer. length)
        bytes-read (.readSync fs fd b 0 length nil)]
    (if (> bytes-read 0)
      (.toString b "utf8" 0 bytes-read))))

(defn line-seq
  ([fd]
    (line-seq fd nil))
  ([fd line]
   (if-let [chunk (read-chunk fd)]
     (if (re-find #"\n" (str line chunk))
       (let [lines (cs/split (str line chunk) #"\n")]
         (if (= 1 (count lines))
           (lazy-cat lines (line-seq fd))
           (lazy-cat (butlast lines) (line-seq fd (last lines)))))
       (recur fd (str line chunk)))
     (when line
       (list line)))))
