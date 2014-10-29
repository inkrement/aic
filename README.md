Advanced Internet Computing Sentiment Analysis
===

Setup:
------

For compiling the sources and starting the Tomcat server simply type the command
`gradlew bootRun` on your shell. You'll see some log messages during startup,
you know that the server is ready when something like this shows up:

```
2014-10-17 17:37:25.509  INFO 1151 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080/http
2014-10-17 17:37:25.519  INFO 1151 --- [           main] com.aic.Application                      : Started Application in 4.915 seconds (JVM running for 5.496)
```

When everything went ok, you can reach the server at `http://localhost:8080`.
Currently the server offers two REST endpoints, one for registering a new
company and one for querying the sentiments for a company during a specific
time period. The registration endpoint can be reached at `http://localhost:8080/register`
and expects two POST parameters, namely, `name` and `password`. The sentiment for
a company can be retrieved by contacting the endpoint `http://localhost:8080/sentiment`
and takes the four GET parameters `name`, `password`, `start` and `end`. The latter
two parameters represent dates and therefore have to conform to the pattern `MMddyyyy`. An
example query may look like this: `http://localhost:8080/sentiment?name=Microsoft&password=test123&start=01011970&end=01011972`.

To test the `fetching tweets` functionality, call `http://localhost:8080/tweets?name=Microsoft&start=20141018&end=20141020`. (Pattern for dates is yyyyddMM, similiar to the pattern used by twitter)

Misc
----

Programming Language: JAVA (Jetty Container)

Framework: Spring

Library: Twitter4j

Datenbank:

Architecture: Client-Server


Florian Taus: Twitter API

Dominik Pichler, Christian Hotz-Behofsits: Preprocessing

Matthias Reisinger, Thomas Schmidtlehner: Classify




TODO
-----

 - Serializing within the caching class
 - POS Tagging http://nlp.stanford.edu/software/tagger.shtml http://opennlp.apache.org/

http://www.thinkmind.org/download.php?articleid=immm_2012_1_10_20033