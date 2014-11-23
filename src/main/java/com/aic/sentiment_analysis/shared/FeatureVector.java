package com.aic.sentiment_analysis.shared;

import java.util.List;

/**
 * Represents a feature vector
 */
public class FeatureVector {

    private List<Feature> features;

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
    public List<Feature> getFeatures() {
        return features;
    }
}
