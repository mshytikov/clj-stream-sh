(ns clj-stream-sh.core
  (:import (java.io BufferedReader InputStreamReader))
  (:use
    lamina.core
    aleph.http
    [clojure.tools.cli :only [cli]])
  (:require [ring.middleware.params :as ring])
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
    (close ch)))

(defn handler [request]
  (let [stream (channel)
        command (get-in request [:query-params "cmd"] "echo 'Please provide command'")]
    (stream-sh-output stream command)
    {:status 200
     :headers {"content-type" "text/plain"}
     :body stream}))





(defn start [options]
  (start-http-server (wrap-ring-handler (ring/wrap-params handler)) options))



(defn -main [& args]
  (let [[options args banner]
        (cli args
             ["-p" "--port" "The port to listen on" :default 8080 :parse-fn #(Integer. %)]
             ["-h" "--host" "The hostname" :default "localhost"])]

    (start options)))
