package com.aic.classification;

import com.aic.preprocessing.PreprocessingException;
import org.junit.Test;
import weka.classifiers.Evaluation;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class EvaluationTest {

	private static final String PATH_TO_TRAINING_DATA = "training.2000.processed.noemoticon.csv";
	private static final String PATH_TO_TEST_DATA = "testdata.manual.2009.06.14.csv";

	@Test
	public void evaluate() throws ClassificationException, PreprocessingException, FileNotFoundException, URISyntaxException {
		CSVTrainingSampleLoader sampleLoader = new CSVTrainingSampleLoader();

		Iterable<TrainingSample> trainingSamples = sampleLoader.load(
				getClass().getClassLoader().getResource(PATH_TO_TRAINING_DATA).toURI());
		Iterable<TrainingSample> testSamples = sampleLoader.load(
				getClass().getClassLoader().getResource(PATH_TO_TEST_DATA).toURI());

		SentimentClassifier classifier = new SentimentClassifier(trainingSamples);
		Evaluation evaluation = classifier.evaluate(testSamples);
		System.out.println(evaluation.toSummaryString());
	}
}