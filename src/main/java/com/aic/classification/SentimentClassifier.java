package com.aic.classification;

import edu.stanford.nlp.ling.TaggedWord;
import weka.classifiers.Classifier;
import weka.classifiers.functions.LibSVM;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

public class SentimentClassifier implements ISentimentClassifier {

	private Classifier classifier;
	private ArrayList<Attribute> attributes;

	public SentimentClassifier() throws ClassificationException {
		classifier = new LibSVM();
		train();
	}

	private void initializeAttributes() {

	}

	private void train() throws ClassificationException {
		Instances trainingInstances = null;
		try {
			classifier.buildClassifier(trainingInstances);
		} catch (Exception e) {
			throw new ClassificationException(e);
		}
	}

	private Instances parseTrainingInstances() {
		Instances instances = new Instances("train", attributes, 0);
		return instances;
	}

	@Override
	public Sentiment classify(List<? extends TaggedWord> featureVector) throws ClassificationException {
		Instance instanceToClassify = null;
		try {
			double classification = classifier.classifyInstance(instanceToClassify);
			return sentimentFromClassification(classification);
		} catch (Exception e) {
			throw new ClassificationException(e);
		}
	}

	private Sentiment sentimentFromClassification(double classification) {
		if (classification == 0) {
			return Sentiment.NEGATIVE;
		} else {
			return Sentiment.POSITIVE;
		}
	}
}
