package com.aic.preprocessing;

import cmu.arktweetnlp.Tagger;
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
        FeatureVector featureVector = preprocessor.preprocess("@stum450n Hi. Thanks for the follow. Nice website..." +
                " check your carriage returns though, the bottom of 'Website &amp; brand identity' is out (unhappy)");
        List<String> features = new ArrayList<String>();
        for (Tagger.TaggedToken t : featureVector.getFeatures()) {
            features.add(t.token);
        }
        Assert.assertTrue("'Nice' not found",  features.contains("Nice"));
        Assert.assertFalse("'happy' found but not contained in message",  features.contains("happy"));
	}
}