(ns clj-stream-sh.core
  (:import (java.io BufferedReader InputStreamReader))
  (:use
    clojure.java.io
    lamina.core
    aleph.http
    [clojure.tools.cli :only [cli]])
  (:require 
    [ring.middleware.params :as ring]
    [clojure.java.io :as io])
  (:gen-class))


(defn cmd [p]
  (.. Runtime getRuntime (exec (str p))))

(defn cmdout [o] 
  (let [r (BufferedReader. 
            (InputStreamReader. 
              (.getInputStream o)))] 
    (line-seq r)))

(defn stream-sh-output [ch, command]
  (future
    (dorun
      (map #(enqueue ch (str % "\n"))
           (cmdout (cmd command))))
    (close ch))
  {:status 200
   :headers {"content-type" "text/plain"}
   :body ch})

(defn stream-html [ch filename]
  (let [html (io/resource filename)]
    (future
      (with-open [rdr (io/reader html)]
        (doseq [line (line-seq rdr)]
          (enqueue ch (str line "\n"))))
      ;(enqueue ch html)
      (close ch))
    {:status 200
     :headers {"content-type" "text/html"}
     :body ch}))

(defn handler [request]
  (let [stream (channel)
        command (get-in request [:query-params "cmd"])]
    (if command
      (stream-sh-output stream command)
      (stream-html stream "index.html"))))


(defn start [options]
  (start-http-server (wrap-ring-handler (ring/wrap-params handler)) options))


(defn -main [& args]
  (let [[options args banner]
        (cli args
             ["-p" "--port" "The port to listen on" :default 8080 :parse-fn #(Integer. %)]
             ["-h" "--host" "The hostname" :default "localhost"])]

    (start options)))
