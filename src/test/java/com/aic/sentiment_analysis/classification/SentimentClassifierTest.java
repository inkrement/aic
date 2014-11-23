package com.aic.sentiment_analysis.classification;

import com.aic.sentiment_analysis.shared.Feature;
import com.aic.sentiment_analysis.shared.FeatureVector;
import org.junit.Test;
import weka.classifiers.Evaluation;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Black-box tests for {@link com.aic.sentiment_analysis.classification.SentimentClassifier}.
 */
public class SentimentClassifierTest {

	/**
	 * Generates a list containing a single {@code TrainingSample} that is
	 * initialized with the given {@code featureString} and {@code sentiment}.
	 *
	 * @param featureString
	 * @param sentiment
	 * @return the according list
	 */
	private List<TrainingSample> generateTrainingSamples(String featureString,
	                                                     Sentiment sentiment) {
		Feature feature = new Feature();
		feature.setWord(featureString);

		List<Feature> features = new ArrayList<>();
		features.add(feature);

		FeatureVector featureVector = new FeatureVector();
		featureVector.setFeatures(features);

		TrainingSample trainingSample = new TrainingSample(featureVector,
				sentiment);
		List<TrainingSample> trainingSamples = new ArrayList<>();
		trainingSamples.add(trainingSample);

		return trainingSamples;
	}

	@Test
	public void testWithSinglePositiveTrainingSample() throws ClassificationException {
		List<TrainingSample> trainingSamples = generateTrainingSamples(
				"Happy", Sentiment.POSITIVE);

		SentimentClassifier classifier = new SentimentClassifier(trainingSamples);
		Sentiment classificationResult = classifier.classify(trainingSamples.
				get(0).getFeatureVector());

		assertEquals(classificationResult, Sentiment.POSITIVE);

		Evaluation evaluation = classifier.evaluate(trainingSamples);
		System.out.println(evaluation.toSummaryString());
	}

	@Test
	public void testWithSingleNegativeTrainingSample() throws ClassificationException {
		List<TrainingSample> trainingSamples = generateTrainingSamples(
				"Unhappy", Sentiment.NEGATIVE);

		SentimentClassifier classifier = new SentimentClassifier(trainingSamples);
		Sentiment classificationResult = classifier.classify(trainingSamples.
				get(0).getFeatureVector());

		assertEquals(classificationResult, Sentiment.NEGATIVE);
	}
}
