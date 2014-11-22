package com.aic.preprocessing;

import cmu.arktweetnlp.Tagger;
import com.aic.shared.FeatureVector;

import java.io.IOException;
import java.util.List;

public class SentimentTwitterPreprocessor implements ISentimentPreprocessor {

    // model for the part-of-speech tagger
    public static final String MODEL_PATH = "/preprocessing.model";

    private Tagger tagger;

    public SentimentTwitterPreprocessor() throws PreprocessingException {
        tagger = new Tagger();
        try {
            tagger.loadModel(MODEL_PATH);
        } catch (IOException e) {
            throw new PreprocessingException(e);
        }
    }

    @Override
    public FeatureVector preprocess(String message) throws PreprocessingException {
        List<Tagger.TaggedToken> taggedTokens = tagger.tokenizeAndTag(message);

        FeatureVector featureVector = new FeatureVector();
        featureVector.setFeatures(taggedTokens);

        return featureVector;
    }
}
