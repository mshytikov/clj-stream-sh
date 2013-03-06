(defproject clj-stream-sh "0.0.1-SNAPSHOT"
  :description "HTTP streaming for shell command output."
  :dependencies [[org.clojure/clojure "1.5.0-alpha5"]
                 [aleph "0.3.0-beta14"]
                 [org.clojure/tools.cli "0.2.2"]
                 [ring/ring-core "1.1.8"]]
            :resources-path "resources"
            :main clj-stream-sh.core)

