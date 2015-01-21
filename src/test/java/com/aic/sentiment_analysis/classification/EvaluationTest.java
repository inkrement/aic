package com.aic.sentiment_analysis.classification;

import com.aic.Constants;
import com.aic.sentiment_analysis.preprocessing.PreprocessingException;
import org.junit.BeforeClass;
import org.junit.Test;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.core.SelectedTag;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;

public class EvaluationTest {

	private static final String PATH_TO_TEST_DATA = "testdata.manual.2009.06.14.csv";

	private static List<TrainingSample> trainingSamples;
	private static List<TrainingSample> testSamples;

	@BeforeClass
	public static void loadTrainingData() throws URISyntaxException, PreprocessingException, FileNotFoundException {
		CSVTrainingSampleLoader sampleLoader = new CSVTrainingSampleLoader();
		CSVTrainingSTSLoader trainLoader = new CSVTrainingSTSLoader();
		trainingSamples = trainLoader.load(
//		trainingSamples = sampleLoader.load(
				EvaluationTest.class.getClassLoader().getResource(Constants.CLASSIFIER_TRAINING_FILE_PATH).toURI());
		testSamples = sampleLoader.load(
				EvaluationTest.class.getClassLoader().getResource(PATH_TO_TEST_DATA).toURI());
	}

	@Test
	public void evaluateLibSVM_C_SVC() throws ClassificationException {
		LibSVM svm = new LibSVM();
		evaluate(svm);
	}

	@Test
	public void evaluateLibSVM_NU_SVC() throws ClassificationException {
		LibSVM svm = new LibSVM();
		svm.setSVMType(new SelectedTag(LibSVM.SVMTYPE_NU_SVC, LibSVM.TAGS_SVMTYPE));
		evaluate(svm);
	}

	@Test
	public void evaluateSMO() throws ClassificationException {
		SMO smo = new SMO();
		evaluate(smo);
	}

	@Test
	public void evaluateNaiveBayes() throws ClassificationException {
		NaiveBayes naiveBayes = new NaiveBayes();
		naiveBayes.setUseSupervisedDiscretization(true);
		evaluate(naiveBayes);
	}

	@Test
	public void evaluateBayesNet() throws ClassificationException {
		BayesNet bayesNet = new BayesNet();
		evaluate(bayesNet);
	}

	@Test
	public void evaluateIBk() throws ClassificationException {
		IBk ibk = new IBk();
		evaluate(ibk);
	}

	public void evaluate(Classifier algorithm) throws ClassificationException {
		SentimentClassifier classifier = new SentimentClassifier(trainingSamples, algorithm);
		Evaluation evaluation = classifier.evaluate(testSamples);
		System.out.println(evaluation.toSummaryString());
	}
}
