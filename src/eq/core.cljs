(ns eq.core
  (:require [clojure.string :as cs]
            [cljs.reader :as edn]
            [cljs.pprint :as pprint]
            [cljs.nodejs :as node]
            [eq.io :refer [line-seq]]))

(node/enable-util-print!) ; allows (println ..) to print to console.log

(def fs (js/require "fs"))

(defn ->selector
  "Build an eq selector to be used by eq/select."
  [expr]
  (cond
    ;; "", "." -> identity
    (or (nil? expr) (empty? expr) (= expr ".")) identity

    ;; ":foo" -> :foo
    ;; ":foo.bar" -> :foo.bar
    (= (.indexOf expr ":") 0)
      (keyword (.substring expr 1))

    ;; ".foo.bar" -> (-> :foo :bar)
    (re-matches #"(?:\.[a-z][a-zA-Z0-9-]*)+" expr)
      (let [kws (->> (cs/split (.substring expr 1) #"\.")
                     (map keyword))]
        (fn [o]
          (reduce (fn [o kw]
                    (get o kw))
                  o
                  kws)))

    :else
      ;identity
      nil
    ))

(defn select
  [selector xs]
  (map selector xs))

(defn -main
  [expr & _]
  (if-let [selector (->selector expr)]
    (doseq [line (->> (. fs openSync "/dev/stdin" "rs")
                      line-seq
                      (map edn/read-string)
                      (select selector))]
      ;; hacky way to avoid double-newlines
      (println
        (cs/replace
          (with-out-str
            (pprint/pprint line))
          #"\n$" "")))

    (do
      (println "Unrecognized argument: " expr)
      (.exit node/process 1))))

(set! *main-cli-fn* -main) ; sends node's process.argv to -main
