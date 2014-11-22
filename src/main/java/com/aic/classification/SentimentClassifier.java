package com.aic.classification;

import cmu.arktweetnlp.Tagger;
import com.aic.preprocessing.ISentimentPreprocessor;
import com.aic.preprocessing.PreprocessingException;
import com.aic.preprocessing.SentimentTwitterPreprocessor;
import com.aic.shared.Feature;
import com.aic.shared.FeatureVector;
import com.fasterxml.jackson.core.JsonFactory;
import org.w3c.dom.Attr;
import weka.classifiers.Classifier;
import weka.classifiers.functions.LibSVM;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;

import java.util.*;

public class SentimentClassifier implements ISentimentClassifier {

	private static final String pathToTrainingData = "";

	private final Classifier classifier;
	private final ArrayList<Attribute> featureList;
	private final Map<String, Integer> featureIndexMap;

	public SentimentClassifier() throws ClassificationException {
		try {
			classifier = new LibSVM();
			Map<FeatureVector, Sentiment> trainingData = loadTrainingData();
			featureList = loadFeatureList(trainingData);
			featureIndexMap = initFeatureIndexMap(featureList);
			train(trainingData);
		} catch (PreprocessingException e) {
			throw new ClassificationException(e);
		}
	}

	private Map<FeatureVector, Sentiment> loadTrainingData() throws PreprocessingException {
		Map<FeatureVector, Sentiment> trainingData = new HashMap<>();
		ISentimentPreprocessor preprocessor = new SentimentTwitterPreprocessor();
		Scanner scanner = new Scanner("");
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] columns = line.substring(1, line.length() - 1).split("\",\"");

			String sentimentString = columns[0];
			Sentiment sentiment;
			if (sentimentString.equals("0")) {
				sentiment = Sentiment.NEGATIVE;
			} else if (sentimentString.equals("4")) {
				sentiment = Sentiment.POSITIVE;
			} else {
				continue;
			}

			String tweet = columns[columns.length - 1];
			FeatureVector featureVector = preprocessor.preprocess(tweet);

			trainingData.put(featureVector, sentiment);
		}
		scanner.close();
		return trainingData;
	}

	private Map<String, Integer> initFeatureIndexMap(ArrayList<Attribute> featureList) {
		Map<String, Integer> featureIndexMap = new HashMap<>();
		for (int i = 0; i < featureList.size() - 1; i++) {
			Attribute feature = featureList.get(i);
			featureIndexMap.put(feature.name(), i);
		}
		return featureIndexMap;
	}

	private ArrayList<Attribute> loadFeatureList(Map<FeatureVector, Sentiment> trainingData)
			throws ClassificationException {
		try {
			Set<String> featureStrings = loadDistinctFeatures(trainingData);
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

	private Set<String> loadDistinctFeatures(Map<FeatureVector, Sentiment> trainingData)
			throws PreprocessingException {
		HashSet<String> features = new HashSet<>();
		ISentimentPreprocessor preprocessor = new SentimentTwitterPreprocessor();
		for (FeatureVector featureVector : trainingData.keySet()) {
			for (Feature feature : featureVector.getFeatures()) {
				features.add(feature.getWord());
			}
		}
		return features;
	}

	private void train(Map<FeatureVector, Sentiment> trainingData) throws ClassificationException {
		Instances trainingInstances = loadTrainingInstances(trainingData);
		try {
			classifier.buildClassifier(trainingInstances);
		} catch (Exception e) {
			throw new ClassificationException(e);
		}
	}

	private Instances loadTrainingInstances(Map<FeatureVector, Sentiment> trainingData) {
		Instances instances = new Instances("train", featureList, 0);
		for (Map.Entry<FeatureVector, Sentiment> trainingDataEntry : trainingData.entrySet()) {
			FeatureVector featureVector = trainingDataEntry.getKey();
			Sentiment sentiment = trainingDataEntry.getValue();
			Instance instance = loadInstance(featureVector, sentiment);
			instances.add(instance);
			instance.setDataset(instances);
		}
		return instances;
	}

	private Instance loadInstance(FeatureVector featureVector, Sentiment sentiment) {
		Map<Integer, Double> featureMap = new HashMap<>();
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
		values[i] = intFromSentiment(sentiment);

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
