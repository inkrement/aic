package com.aic.sentiment_analysis.preprocessing;

import com.aic.sentiment_analysis.feature.FeatureVector;
import org.junit.Test;

/**
 * Created by chris on 25.11.14.
 */


public class SmileyTest {

    @Test
    public void testPreprocessing() throws PreprocessingException {

        String tweet = "Need a hug :)";

        SentimentTwitterPreprocessor tp = new SentimentTwitterPreprocessor();

        FeatureVector fv = tp.preprocess(tweet);

        System.out.println(fv);
    }
}
