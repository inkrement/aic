Advanced Internet Computing Sentiment Analysis
===

## Setup

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

To test the `fetching tweets` functionality, call `http://localhost:8080/tweets?name=Microsoft&start=20141018&end=20141020`. (Pattern for dates is yyyyddMM, time zone is UTC, similiar to the pattern used by twitter)

## Environment

Programming Language: JAVA (Jetty Container)

Framework: Spring

Library: Twitter4j

Architecture: Client-Server


### Preprocessing

For preprocessing purposes the [Stanford NLP](http://nlp.stanford.edu) implementation is used. The part-of-speech
tagger makes use of the Penn Treebank, the corresponding tagset can be found
[here](http://www.comp.leeds.ac.uk/ccalas/tagsets/upenn.html). We used the
[GATE Twitter part-of-speech tagging model](https://gate.ac.uk/wiki/twitter-postagger.html) for extending the
tagging informations especially for twitter data (HT (hashtags), UH ("lol", "ikr"), USR (username mentions),
RT (retweet signifiers), URL (URLs)).

### Classification

For the classification part we used the data mining framework [WEKA](http://www.cs.waikato.ac.nz/ml/weka/) and
[LibSVM](http://www.csie.ntu.edu.tw/~cjlin/libsvm/) as a support vector machine.

## Team

**Twitter API:** Florian Taus

**Preprocessing:** Dominik Pichler, Christian Hotz-Behofsits

**Classifier:** Matthias Reisinger, Thomas Schmidleithner

## TODO

 - Serializing within the caching class

http://www.thinkmind.org/download.php?articleid=immm_2012_1_10_20033
