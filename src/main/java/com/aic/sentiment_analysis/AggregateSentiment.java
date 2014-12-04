package com.aic.sentiment_analysis;

import com.aic.sentiment_analysis.classification.Sentiment;
import com.aic.sentiment_analysis.fetch.TwitterStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an aggregation of tweets {@link com.aic.sentiment_analysis.fetch.TwitterStatus} which
 * are classified by a sentiment value {@link com.aic.sentiment_analysis.classification.Sentiment}.
 */
public class AggregateSentiment {

    private Map<TwitterStatus, Sentiment> sentiments;
    private int positiveTweetsCount;

    public AggregateSentiment() {
        this.sentiments = new HashMap<>();
    }

    /**
     * Adds a tweet and its corresponding sentiment value to the aggregation.
     *
     * @param status the twitter status
     * @param sentiment the sentiment value
     */
    public void putSentiment(TwitterStatus status, Sentiment sentiment) {
        sentiments.put(status, sentiment);

        if (sentiment == Sentiment.POSITIVE)
            positiveTweetsCount++;
    }

    /**
     * Returns a map of tweets with the corresponding sentiment.
     *
     * @return a map of tweets with the corresponding sentiment
     */
    public Map<TwitterStatus, Sentiment> getSentimentAggregation() {
        return sentiments;
    }

    /**
     * Calculates the positive/negative ratio for given tweets and sentiment values.
     *
     * @return the positive/negative sentiment ratio
     */
    public float calculateAggregateSentimentRatio() {
        return (float) positiveTweetsCount / sentiments.size();
    }
}
