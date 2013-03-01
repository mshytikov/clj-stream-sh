(ns clj-stream-sh.core
  (:import (java.io BufferedReader InputStreamReader))
  (:use lamina.core aleph.http))


(defn cmd [p] (.. Runtime getRuntime (exec (str p))))

(defn cmdout [o] 
  (let [r (BufferedReader. 
            (InputStreamReader. 
              (.getInputStream o)))] 
    (line-seq r)))

(defn stream-numbers [ch]
  (future
    (dorun
      (map #(enqueue ch (str % "\n"))
        (cmdout (cmd "sh test.sh"))))
    (close ch)))

(defn handler [request]
  (let [stream (channel)]
    (stream-numbers stream)
    {:status 200
     :headers {"content-type" "text/plain"}
     :body stream}))

(start-http-server (wrap-ring-handler handler) {:port 8080})

