(defproject tweetlog "0.0.1-SNAPSHOT"
  :description "Cool new project to do things and stuff"
  :permacode-paths ["src"]
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :profiles {:dev {:dependencies [[midje "1.7.0"]
                                  [permacode "0.1.1-SNAPSHOT"]]
                   :plugins [[permacode "0.1.1-SNAPSHOT"]]}
             ;; You can add dependencies that apply to `lein midje` below.
             ;; An example would be changing the logging destination for test runs.
             :midje {}})
             ;; Note that Midje itself is in the `dev` profile to support
             ;; running autotest in the repl.

  
