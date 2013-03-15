clj-stream-sh
=============
## Description ##

HTTP streaming for shell command output.

### Run ###

```
$ java -jar clj-stream-sh-0.0.1-SNAPSHOT-standalone.jar
```
[download jar](http://dl.dropbox.com/u/27498455/clj-stream-sh-0.0.1-SNAPSHOT-standalone.jar)

### Use ###

```
$ curl -v  http://127.0.0.1:8080?cmd=ping+google.com'
```

Or just open in your browser (Chrome, Firefox, Safari) http://127.0.0.1:8080

### Examples ###

##### 1. Online Chat #####

1. Download jar ``` $ wget http://dl.dropbox.com/u/27498455/clj-stream-sh-0.0.1-SNAPSHOT-standalone.jar ```
2. Download chat shell script ``` $ wget https://raw.github.com/mshytikov/clj-stream-sh/master/examples/chat/post ```
3. Make script executable ``` $ chmod +x post ```
4. Start server ``` $ java -jar clj-stream-sh-0.0.1-SNAPSHOT-standalone.jar ```
5. Open url in different browsers http://127.0.0.1:8080
6. To post your message write something like  ``` ./post hello world ``` in the input field and submit.

##### 2. Stream logs ####
This example [here](https://github.com/mshytikov/web_log)
