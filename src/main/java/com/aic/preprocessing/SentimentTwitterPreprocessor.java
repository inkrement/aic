package com.aic.preprocessing;

import cmu.arktweetnlp.Tagger;
import com.aic.shared.Feature;
import com.aic.shared.FeatureVector;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.PTBTokenizer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class SentimentTwitterPreprocessor implements ISentimentPreprocessor {

    @Override
    public FeatureVector preprocess(String message) throws PreprocessingException {
        PTBTokenizer.PTBTokenizerFactory<CoreLabel> fac = PTBTokenizer.PTBTokenizerFactory.newPTBTokenizerFactory(true, true);
        List<Feature> features = new ArrayList<Feature>();
        for (CoreLabel label : fac.getTokenizer(new StringReader(message)).tokenize()) {
            Feature feature = new Feature();
            feature.setWord(label.word());
            features.add(feature);
        }
        FeatureVector featureVector = new FeatureVector();
        featureVector.setFeatures(features);

        return featureVector;
    }
}
