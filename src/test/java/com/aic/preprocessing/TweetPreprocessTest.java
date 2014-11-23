package com.aic.preprocessing;

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
	public void testIftokensCorrectlyParsed() throws PreprocessingException {
        FeatureVector featureVector = preprocessor.preprocess("5 7/8 U.K. na you're ur http://orf.at @stum450n Hi. " +
                "Thank u for you the follow. Nice website... :-) #funny lololol won't gonna " +
                " check your carriage returns though, the bottom of 'Website &amp; lol brand identity' is out (unhappy)");
        List<String> features = new ArrayList<>();
        for (Feature f : featureVector.getFeatures()) {
            features.add(f.getWord());
        }
        Assert.assertTrue("'nice' not found",  features.contains("nice"));
        Assert.assertFalse("'happy' found but not contained in message",  features.contains("happy"));
	}

    /**
     * Tests if the preprocessor finds the normalized word tokens
     * within a given message.
     */
    @Test
    public void testIfWordsNormalized() throws PreprocessingException {
        FeatureVector featureVector = preprocessor.preprocess("Make use of LOWERCASE and remove #hashtags and" +
                        " URLs like http://wikipedia.org, https://wikipedia.org and http://www.wikipedia.org");
        List<String> features = new ArrayList<>();
        for (Feature f : featureVector.getFeatures()) {
            features.add(f.getWord());
        }
        Assert.assertFalse("'#funny' found but should not be part of the message", features.contains("#funny"));
        Assert.assertFalse("'Make' found but not contained in message",  features.contains("Make"));
        Assert.assertTrue("'lowercase' not found",  features.contains("lowercase"));
        Assert.assertTrue("'urls' not found",  features.contains("urls"));
        Assert.assertFalse("'URLs' not found",  features.contains("URLs"));
        Assert.assertFalse("'http://wikipedia.org' not found",  features.contains("http://wikipedia.org"));
        Assert.assertFalse("'https://wikipedia.org' not found",  features.contains("https://wikipedia.org"));
        Assert.assertFalse("'http://www.wikipedia.org' not found",  features.contains("http://www.wikipedia.org"));
    }
}