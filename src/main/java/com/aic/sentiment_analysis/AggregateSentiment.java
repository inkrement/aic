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
            positiveStats.add(status);
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
     * ################## Weight tweets based on Date ####################
     * if location does not work
     * TODO delete section if no neccessary
     */

    private ArrayList<TwitterStatus> positiveStats = new ArrayList<>();

    /**
     *
     * @return weighted aggregated setiment ratio
     */
    public float calculateAggregateSentimentRatio1() {

        float sent = 0;
        //The last tweet has 90% of the weight of the first one
        double endRatio = 0.8;
        int size = positiveStats.size();

        orderTweets();

        if(size == 1){
            return 1/sentiments.size();
        }

        for(int i = 0; i < positiveStats.size(); i++){
            sent += 1-i*(1-endRatio)/(size-1);
        }

        //System.out.println("############### sent new: " + sent/sentiments.size());

        return sent/sentiments.size();
    }

    private void orderTweets(){

        Collections.sort(positiveStats, new Comparator<TwitterStatus>() {

            public int compare(TwitterStatus tw1, TwitterStatus tw2) {
                return (int) tw2.getDate().getTime() - (int) tw1.getDate().getTime();
            }
        });

        /*
        System.out.println("############################");
        System.out.println(positiveStats.size());
        for(TwitterStatus t : positiveStats){
            System.out.println(t.getDate().getTime());
        }
        System.out.println("############################");
        */
    }
}
