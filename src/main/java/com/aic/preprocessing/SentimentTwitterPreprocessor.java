package com.aic.preprocessing;

import com.aic.shared.Feature;
import com.aic.shared.FeatureVector;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation of {@link com.aic.preprocessing.ISentimentPreprocessor} based
 * on the Stanford NLP framework.
 *
 * @see <a href="http://nlp.stanford.edu/software/index.shtml">Stanford NLP</a>
 * @see <a href="https://gate.ac.uk/wiki/twitter-postagger.html">POS Tagger Model file</a>
 */
public class SentimentTwitterPreprocessor implements ISentimentPreprocessor {

    private static final String URL_PATTERN = "((https?|ftp|gopher|telnet|file|Unsure|http):" +
            "((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
    private static final String HASHTAG_PATTERN = "#";
    private static final String TAGGER_PATH = "gate-EN-twitter-fast.model";

    private MaxentTagger tagger;

    public SentimentTwitterPreprocessor() {
        tagger = new MaxentTagger(TAGGER_PATH);
    }

    @Override
    public FeatureVector preprocess(String message) throws PreprocessingException {
        PTBTokenizer.PTBTokenizerFactory<CoreLabel> fac = PTBTokenizer.PTBTokenizerFactory.newPTBTokenizerFactory(true, true);
        List<Feature> features = new ArrayList<>();

        List<CoreLabel> coreLabels = fac.getTokenizer(new StringReader(message)).tokenize();

        // tag labels
        tagger.tagCoreLabels(coreLabels);

        for (CoreLabel label : coreLabels) {
            String word = normalizeWord(label.word(), label.tag());
            if (!word.isEmpty() && !containsNotAllowedTag(label.tag())) {
                Feature feature = new Feature();
                feature.setWord(word);
                feature.setTag(label.tag());
                features.add(feature);
            }
        }
        FeatureVector featureVector = new FeatureVector();
        featureVector.setFeatures(features);

        return featureVector;
    }

    /**
     * Boolean function for filtering unnecessary tags
     *
     * @param tag the tag that needs to get checked
     * @return boolean value
     */
    private boolean containsNotAllowedTag(String tag) {
        if (tag.equals(","))
            return true;
        if (tag.equals(":"))
            return true;
        if (tag.equals("."))
            return true;
        if (tag.equals("URL"))
            return true;

        return false;
    }

    private String normalizeWord(String word, String tag) {
        // lowercase word
        word = word.toLowerCase();

        // remove URL
        word = removeUrl(word);

        // remove hashtag character
        if (tag.equals("HT")) {
            word = removeHashtagCharacter(word);
        }

        return word;
    }

    private String removeHashtagCharacter(String word) {
        return word.replaceAll(HASHTAG_PATTERN,"");
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
