package com.aic.preprocessing;

import cmu.arktweetnlp.Tagger;
import com.aic.shared.Feature;
import com.aic.shared.FeatureVector;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TweetPreprocessTest {

    ISentimentPreprocessor preprocessor;

    public TweetPreprocessTest() throws PreprocessingException {
        preprocessor = new SentimentTwitterPreprocessor();
    }

    /**
     * Tests if the preprocessor finds the correct word
     * tokens in a given message.
     */
	@Test
	public void tokensCorrectlyParsed() throws PreprocessingException {
        FeatureVector featureVector = preprocessor.preprocess("5 7/8 U.K. na you're ur http://orf.at @stum450n Hi. Thank u for you the follow. Nice website..." +
                " check your carriage returns though, the bottom of 'Website &amp; lol brand identity' is out (unhappy)");
        List<String> features = new ArrayList<String>();
        for (Feature f : featureVector.getFeatures()) {
            features.add(f.getWord());
        }
        Assert.assertTrue("'Nice' not found",  features.contains("Nice"));
        Assert.assertFalse("'happy' found but not contained in message",  features.contains("happy"));
	}
}