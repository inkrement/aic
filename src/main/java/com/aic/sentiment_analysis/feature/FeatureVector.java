package com.aic.sentiment_analysis.feature;

import java.util.List;

/**
 * Represents a feature vector
 */
public class FeatureVector {

    private List<Feature> features;
    private String rawMessage;

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }

    @Override
    public String toString(){
        return features.toString();
    }
}
