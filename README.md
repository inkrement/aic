AIC - Sentiment Analysis
===

## Setup

To start the server a twitter properties file with the
credentials for the Twitter API must be provided in the
resources directory (`src/main/resources/twitter.properties`):
```
consumerKey=YOUR_CONSUMER_KEY
consumerSecret=YOUR_CONSUMER_SECRET
accessToken=YOUR_ACCESS_TOKEN
accessTokenSecret=YOUR_ACCESS_TOKEN_SECRET
```

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
and expects `name` as POST parameter. The sentiment for
a company can be retrieved by contacting the endpoint `http://localhost:8080/sentiment`
and takes the four GET parameters `name`, `classifier`, `startDate`, `endDate`. The latter
two parameters represent dates and therefore have to conform to the pattern `yyyy-MM-dd`.
The parameter `classifier` can be set by one of the followed values: `SVM_C_SVC`,
`SVM_NU_SVC`, `SMO`, `NAIVE_BAYES`, `BAYES_NET` or `KNN`. An example query may look like this:
`http://localhost:8080/sentiment?name=Microsoft&classifier=KNN&start=2014-09-08&end=2014-09-12`.

It is also possible to use the REST service via a AngularJS Frontend which can be reached at `http://localhost:8080/`.

## Environment

Programming Language: Java (Tomcat Container)

Framework: Spring

Library: Twitter4j

Architecture: Client-Server


### Preprocessing

For preprocessing purposes the [Stanford NLP](http://nlp.stanford.edu) implementation is used. The part-of-speech
tagger makes use of the Penn Treebank, the corresponding tagset can be found
[here](http://www.comp.leeds.ac.uk/ccalas/tagsets/upenn.html). We used the
[GATE Twitter part-of-speech tagging model](https://gate.ac.uk/wiki/twitter-postagger.html) for extending the
tagging informations especially for twitter data (HT (hashtags), UH ("lol", "ikr"), USR (username mentions),
RT (retweet signifiers), URL (URLs)). The Penn Treebank Tagset can be found
[here](http://www.comp.leeds.ac.uk/ccalas/tagsets/upenn.html).

### Classification

For the classification part we used the data mining framework [WEKA](http://www.cs.waikato.ac.nz/ml/weka/) and
[LibSVM](http://www.csie.ntu.edu.tw/~cjlin/libsvm/) as a support vector machine.

## Team

**Twitter API:** Florian Taus

**Preprocessing:** Dominik Pichler, Christian Hotz-Behofsits

**Classifier:** Matthias Reisinger, Thomas Schmidleithner

