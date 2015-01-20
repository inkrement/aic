package com.aic.sentiment_analysis.classification;

import com.aic.Constants;
import com.aic.sentiment_analysis.ClassifierConfiguration;
import com.aic.sentiment_analysis.preprocessing.PreprocessingException;
import org.junit.BeforeClass;
import org.junit.Test;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
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
				EvaluationTest.class.getClassLoader().getResource(Constants.CLASSIFIER_TRAINING_FILE_PATH).toURI());
		testSamples = sampleLoader.load(
				EvaluationTest.class.getClassLoader().getResource(PATH_TO_TEST_DATA).toURI());
	}

	@Test
	public void evaluateLibSVM_C_SVC() throws ClassificationException {
		LibSVM svm = new LibSVM();
		svm.setCacheSize(40);
		svm.setCoef0(0.0);
		svm.setCost(1);
		svm.setDegree(3);
		svm.setDoNotReplaceMissingValues(false);
		svm.setEps(0.001);
		svm.setGamma(0.0);
		svm.setKernelType(new SelectedTag(LibSVM.KERNELTYPE_RBF, LibSVM.TAGS_KERNELTYPE));
		svm.setLoss(0.1);
		svm.setNormalize(false);
		svm.setNu(0.5);
		svm.setProbabilityEstimates(false);
		svm.setSeed(1);
		svm.setShrinking(true);
		evaluate(svm);
	}

	@Test
	public void evaluateLibSVM_EPSILON_SVR() throws ClassificationException {
		LibSVM svm = new LibSVM();
		svm.setSVMType(new SelectedTag(LibSVM.SVMTYPE_EPSILON_SVR, LibSVM.TAGS_SVMTYPE));
		evaluate(svm);
	}

	@Test
	public void evaluateLibSVM_NU_SVC() throws ClassificationException {
		LibSVM svm = new LibSVM();
		svm.setSVMType(new SelectedTag(LibSVM.SVMTYPE_NU_SVC, LibSVM.TAGS_SVMTYPE));
		evaluate(svm);
	}

	@Test
	public void evaluateLibSVM_NU_SVR() throws ClassificationException {
		LibSVM svm = new LibSVM();
		svm.setSVMType(new SelectedTag(LibSVM.SVMTYPE_NU_SVR, LibSVM.TAGS_SVMTYPE));
		evaluate(svm);
	}

	@Test
	public void evaluateLibSVM_ONE_CLASS() throws ClassificationException {
		LibSVM svm = new LibSVM();
		svm.setSVMType(new SelectedTag(LibSVM.SVMTYPE_ONE_CLASS_SVM, LibSVM.TAGS_SVMTYPE));
		evaluate(svm);
	}

	@Test
	public void evaluateNaiveBayes() throws ClassificationException {
		NaiveBayes naiveBayes = new NaiveBayes();
		naiveBayes.setUseSupervisedDiscretization(true);
		evaluate(naiveBayes);
	}

	@Test
	public void evaluateIBk() throws ClassificationException {
		IBk ibk = new IBk();
		evaluate(ibk);
	}

	@Test
	public void evaluateJ48() throws ClassificationException {
		J48 j48 = new J48();
		evaluate(j48);
	}

	public void evaluate(Classifier algorithm) throws ClassificationException {
		SentimentClassifier classifier = new SentimentClassifier(trainingSamples, algorithm);
		Evaluation evaluation = classifier.evaluate(testSamples);
		System.out.println(evaluation.toSummaryString());
	}
}
