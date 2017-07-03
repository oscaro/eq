(ns eq.core
  (:require [clojure.string :as cs]
            [cljs.reader :as edn]
            [cljs.pprint :as pprint]
            [cljs.nodejs :as node]
            [eq.io :refer [line-seq]]))

(node/enable-util-print!) ; allows (println ..) to print to console.log

(def fs (js/require "fs"))

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
