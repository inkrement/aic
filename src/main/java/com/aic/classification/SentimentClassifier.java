package com.aic.classification;

import com.aic.preprocessing.ISentimentPreprocessor;
import com.aic.preprocessing.PreprocessingException;
import com.aic.preprocessing.SentimentTwitterPreprocessor;
import com.aic.shared.Feature;
import com.aic.shared.FeatureVector;
import weka.classifiers.Classifier;
import weka.classifiers.functions.LibSVM;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;

import java.util.*;

public class SentimentClassifier implements ISentimentClassifier {

	private final Classifier classifier;
	private final ArrayList<Attribute> featureList;
	private final Map<String, Integer> featureIndexMap;

	public SentimentClassifier(Iterable<TrainingSample> trainingSamples)
			throws ClassificationException {
		classifier = new LibSVM();
		featureList = loadFeatureList(trainingSamples);
		featureIndexMap = initFeatureIndexMap(featureList);
		train(trainingSamples);
	}

	private Map<String, Integer> initFeatureIndexMap(ArrayList<Attribute> featureList) {
		Map<String, Integer> featureIndexMap = new HashMap<>();
		for (int i = 0; i < featureList.size() - 1; i++) {
			Attribute feature = featureList.get(i);
			featureIndexMap.put(feature.name(), i);
		}
		return featureIndexMap;
	}

	private ArrayList<Attribute> loadFeatureList(Iterable<TrainingSample>trainingSamples)
			throws ClassificationException {
		try {
			Set<String> featureStrings = loadDistinctFeatures(trainingSamples);
			ArrayList<Attribute> featureList = new ArrayList<>();
			for (String feature : featureStrings) {
				featureList.add(new Attribute(feature));
			}

			List<String> sentiments = new ArrayList<>();
			sentiments.add("negative");
			sentiments.add("positive");
			Attribute sentimentAttribute = new Attribute("sentiment", sentiments);
			featureList.add(sentimentAttribute);

			return featureList;
		} catch (PreprocessingException e) {
			throw new ClassificationException(e);
		}
	}

	private Set<String> loadDistinctFeatures(Iterable<TrainingSample> trainingSamples)
			throws PreprocessingException {
		HashSet<String> features = new HashSet<>();
		ISentimentPreprocessor preprocessor = new SentimentTwitterPreprocessor();
		for (TrainingSample trainingSample : trainingSamples) {
			FeatureVector featureVector = trainingSample.getFeatureVector();
			for (Feature feature : featureVector.getFeatures()) {
				features.add(feature.getWord());
			}
		}
		return features;
	}

	private void train(Iterable<TrainingSample> trainingSamples) throws ClassificationException {
		Instances trainingInstances = loadTrainingInstances(trainingSamples);
		try {
			classifier.buildClassifier(trainingInstances);
		} catch (Exception e) {
			throw new ClassificationException(e);
		}
	}

	private Instances loadTrainingInstances(Iterable<TrainingSample> trainingSamples) {
		Instances instances = new Instances("train", featureList, 0);
		for (TrainingSample trainingSample : trainingSamples) {
			Instance instance = loadInstance(trainingSample);
			instances.add(instance);
			instance.setDataset(instances);
		}
		return instances;
	}

	private Instance loadInstance(TrainingSample trainingSample) {
		TreeMap<Integer, Double> featureMap = new TreeMap<>();
		FeatureVector featureVector = trainingSample.getFeatureVector();
		for (Feature feature : featureVector.getFeatures()) {
			if (isUsedAsFeature(feature.getWord())) {
				featureMap.put(featureIndexMap.get(feature.getWord()), 1.0);
			}
		}

		int indices[] = new int[featureMap.size() + 1];
		double values[] = new double[featureMap.size() + 1];
		int i = 0;
		for (Map.Entry<Integer, Double> entry : featureMap.entrySet()) {
			indices[i] = entry.getKey();
			values[i] = entry.getValue();
			i++;
		}

		indices[i] = featureList.size() - 1;
		values[i] = intFromSentiment(trainingSample.getSentiment());

		Instance instance = new SparseInstance(1.0, values, indices,
				featureList.size() - 1);
		return instance;
	}

	private boolean isUsedAsFeature(String token) {
		return featureIndexMap.get(token) != null;
	}

	private int intFromSentiment(Sentiment sentiment) {
		if (sentiment == Sentiment.NEGATIVE) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public Sentiment classify(FeatureVector featureVector) throws ClassificationException {
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
