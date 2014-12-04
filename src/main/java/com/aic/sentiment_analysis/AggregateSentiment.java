package com.aic.sentiment_analysis;

import com.aic.sentiment_analysis.classification.Sentiment;
import com.aic.sentiment_analysis.fetch.TwitterStatus;

import java.util.*;

/**
 * Represents an aggregation of tweets {@link com.aic.sentiment_analysis.fetch.TwitterStatus} which
 * are classified by a sentiment value {@link com.aic.sentiment_analysis.classification.Sentiment}.
 */
public class AggregateSentiment {

    private Map<TwitterStatus, Sentiment> sentiments;
    private int positiveTweetsCount;

    private ArrayList<TwitterStatus> stats;

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

        if (sentiment == Sentiment.POSITIVE) {
            positiveTweetsCount++;
        }
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


    /**
     * Calculate the sentiment weighted by date
     * @return weighted sentiment value
     */
    public float calculateDateWeightedAggregateSentimentRatio() {

        stats = new ArrayList<>(sentiments.keySet());

        float overallWeight = 0;
        float singleWeight = 0;
        float decreaseFactor = 0.2f;
        int statsSize = stats.size();

        orderTweetsByDate();

        TwitterStatus ts;

        for(int i = 0; i < statsSize; i++){
            ts = stats.get(i);
            if(sentiments.get(ts) == Sentiment.POSITIVE){
                singleWeight += 1-i*decreaseFactor/(statsSize-1);
                overallWeight += 1-i*decreaseFactor/(statsSize-1);
            }else{
                overallWeight++;
            }
        }

        return singleWeight/overallWeight;
    }

    /**
     * Order tweets by date
     */
    private void orderTweetsByDate(){

        Collections.sort(stats, new Comparator<TwitterStatus>() {

            public int compare(TwitterStatus tw1, TwitterStatus tw2) {
                return (int) tw2.getDate().getTime() - (int) tw1.getDate().getTime();
            }
        });
    }
}
