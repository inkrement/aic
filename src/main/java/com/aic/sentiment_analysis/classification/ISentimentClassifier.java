package com.aic.sentiment_analysis.classification;

import com.aic.sentiment_analysis.shared.FeatureVector;

/**
 * Classifier for performing sentiment analysis.
 */
public interface ISentimentClassifier {

	/**
	 * Assign a sentiment to the piece of text represented by the given feature
	 * vector.
	 *
	 * @param featureVector the feature vector used for classification
	 * @return the assigned sentiment
	 */
	public Sentiment classify(FeatureVector featureVector) throws ClassificationException;
}
