package com.aic.classification;

import edu.stanford.nlp.ling.TaggedWord;

import java.util.List;

/**
 * Classifier for performing sentiment analysis.
 */
public interface IClassifier {

	/**
	 * Assigns a sentiment to the given input.
	 *
	 * @param input the input to classify
	 * @return the assigned sentiment
	 */
	public Sentiment classify(List<? extends TaggedWord> input);
}
