(ns eq.core
  (:require [clojure.string :as cs]
            [cljs.reader :as edn]
            [cljs.pprint :as pprint]
            [cljs.nodejs :as node]))

(node/enable-util-print!) ; allows (println ..) to print to console.log

(def fs (js/require "fs"))

;; from https://gist.github.com/bostonou/a54c029fa6f29459eafe
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
     (if line
       (list line)
       ()))))
;;


(def stdin
  (js->clj (.-stdin node/process)))

(defn build-selector
  [expr]
  (cond
    ;; "", "." -> identity
    (or (nil? expr) (empty? expr) (= expr ".")) identity

    ;; ".foo" -> :foo
    (= (.lastIndexOf expr ".") 0)
      (keyword (.substring expr 1))

    :else
      ;identity
      nil
    ))

(defn select
  [o selector]
  o)

(defn -main
  [expr & _]
  (if-let [selector (build-selector expr)]
      (doseq [line (->> (. fs openSync "/dev/stdin" "rs")
                        line-seq)
              :let [o (edn/read-string line)]]
        ;; hacky way to avoid double-newlines
        (println
          (cs/replace
            (with-out-str
              (pprint/pprint (selector o)))
            #"\n$" "")))

      (do
        (println "Unrecognized argument: " expr)
        (.exit node/process 1))))

(set! *main-cli-fn* -main) ; sends node's process.argv to -main
