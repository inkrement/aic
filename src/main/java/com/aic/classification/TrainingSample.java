package com.aic.classification;

import com.aic.shared.FeatureVector;

public class TrainingSample {

	private FeatureVector featureVector;
	private Sentiment sentiment;

	public TrainingSample(FeatureVector featureVector, Sentiment sentiment) {
		this.featureVector = featureVector;
		this.sentiment = sentiment;
	}

	public FeatureVector getFeatureVector() {
		return featureVector;
	}

	public Sentiment getSentiment() {
		return sentiment;
	}
}
