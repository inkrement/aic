package com.aic.sentiment_analysis.classification;

import com.aic.sentiment_analysis.feature.FeatureVector;

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
