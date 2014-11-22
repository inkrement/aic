package com.aic.classification;

import edu.stanford.nlp.ling.TaggedWord;
import weka.classifiers.Classifier;
import weka.classifiers.functions.LibSVM;
import weka.core.Instance;
import weka.core.Instances;

import java.util.List;

public class SentimentClassifier implements ISentimentClassifier {

	@Override
	public Sentiment classify(List<? extends TaggedWord> featureVector) {
		try {
			Instances testInstances = null;
			Instance instanceToClassify = null;
			Classifier classifier = new LibSVM();
			classifier.buildClassifier(testInstances);
			double classification = classifier.classifyInstance(instanceToClassify);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
