package com.aic.preprocessing;

import com.aic.shared.Feature;
import com.aic.shared.FeatureVector;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.PTBTokenizer;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentimentTwitterPreprocessor implements ISentimentPreprocessor {

    private static final String URL_PATTERN = "((https?|ftp|gopher|telnet|file|Unsure|http):" +
            "((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";

    @Override
    public FeatureVector preprocess(String message) throws PreprocessingException {
        PTBTokenizer.PTBTokenizerFactory<CoreLabel> fac = PTBTokenizer.PTBTokenizerFactory.newPTBTokenizerFactory(true, true);
        List<Feature> features = new ArrayList<Feature>();

        for (CoreLabel label : fac.getTokenizer(new StringReader(message)).tokenize()) {
            String word = normalizeWord(label.word());
            if (!word.isEmpty()) {
                Feature feature = new Feature();
                feature.setWord(word);
                features.add(feature);
            }
        }
        FeatureVector featureVector = new FeatureVector();
        featureVector.setFeatures(features);

        return featureVector;
    }

    public String normalizeWord(String word) {
        // lowercase word
        word = word.toLowerCase();
        // remove URL
        word = removeUrl(word);

        return word;
    }

    private String removeUrl(String word) {
        Pattern p = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(word);
        int i = 0;

        while (m.find()) {
            word = word.replaceAll(m.group(i), "").trim();
            i++;
        }

        return word;
    }
}
