(defproject eq "0.2.2"
  :description "Like jq, for EDN"
  :url "https://github.com/oscaro/eq"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure            "1.8.0"]
                 [org.clojure/clojurescript      "1.7.228"]
                 [org.clojure/core.async         "0.2.374"]
                 [org.clojure/tools.nrepl        "0.2.10"] ; non-teletype repl; needed for vim-fireplace
                 [io.nervous/cljs-nodejs-externs "0.2.0"]]

  :profiles {:dev {:dependencies [[com.cemerick/piggieback "0.2.1"]
                                  [org.clojure/tools.nrepl "0.2.10"]]

                   :plugins [[org.bodil/lein-noderepl "0.1.11"] ; required to launch the node repl
                             [lein-cljsbuild           "1.1.2"] ; required to compile cljs to js
                             [lein-npm                 "0.6.1"] ; lein interface with npm (see below)
                             [lein-doo                 "0.1.6"]] ; the current "standard" testing framework

                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}

  :cljsbuild {:builds {:main
                        {:source-paths ["src"]
                         :compiler {:optimizations :advanced
                                    :target        :nodejs
                                    :output-dir    "out-advanced"
                                    :output-to     "target/eq.js"
                                    :externs       ["externs.js"]
                                    :verbose       true
                                    :pretty-print  true}}

                       :none
                        {:source-paths ["src"]
                         :compiler {:optimizations :none
                                    :target        :nodejs
                                    :output-dir    "out-none"
                                    :output-to     "target/eq-none.js"
                                    :externs       ["externs.js"]
                                    :verbose       true
                                    :pretty-print  true}}

                       ;; lein doo uses this profile
                       :test-none
                        {:source-paths ["src" "test"] ; note the added "test" directory
                         :compiler {:optimizations :none
                                    :target        :nodejs
                                    :output-dir    "out-test-none"
                                    :output-to     "target/eq-test-none.js"
                                    :externs       ["externs.js"]
                                    :verbose       true
                                    :main          eq.runner
                                    :pretty-print  true}}
 
                       ;; lein doo uses this profile
                       :test-advanced
                        {:source-paths ["src" "test"] ; note the added "test" directory
                         :compiler {:optimizations :advanced
                                    :target        :nodejs
                                    :output-dir    "out-test-advanced"
                                    :output-to     "target/eq-test-advanced.js"
                                    :externs       ["externs.js"]
                                    :verbose       true
                                    :main          eq.runner
                                    :pretty-print  true}}}})
