package com.aic.shared;

import cmu.arktweetnlp.Tagger;

import java.util.List;

/**
 * Represents a feature vector
 */
public class FeatureVector {

    List<Tagger.TaggedToken> taggedTokens;

    public void setFeatures(List<Tagger.TaggedToken> taggedTokens) {
        this.taggedTokens = taggedTokens;
    }

    public List<Tagger.TaggedToken> getFeatures() {
        return taggedTokens;
    }
}
