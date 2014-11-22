package com.aic.preprocessing;

import com.aic.shared.FeatureVector;
import edu.stanford.nlp.ling.TaggedWord;

import java.util.List;

/**
 * Preprocessor for parsing messages to
 * extract a feature vector.
 */
public interface ISentimentPreprocessor {

    /**
     * Extract a feature vector by a given message.
     *
     * @param message the message intended to preprocess
     * @return the processed feature vector
     */
    public FeatureVector preprocess(String message) throws PreprocessingException;

}
