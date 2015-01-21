package com.aic.sentiment_analysis.classification;

import com.aic.sentiment_analysis.feature.Feature;
import com.aic.sentiment_analysis.feature.FeatureVector;
import com.aic.sentiment_analysis.preprocessing.PreprocessingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.core.*;

import java.util.*;

/**
 * Implementation of {@link com.aic.sentiment_analysis.classification.ISentimentClassifier} based
 * on the WEKA classification framework.
 *
 * @see <a href="http://www.cs.waikato.ac.nz/~ml/weka/">Official WEKA Website</a>
 * @see <a href="http://weka.sourceforge.net/doc.dev/">WEKA API Reference (Javadoc)</a>
 */
public class SentimentClassifier implements ISentimentClassifier {

	private static final Log logger = LogFactory.getLog(SentimentClassifier.class);

	private final Classifier classifier;
	private final Instances trainingInstances;
	private final ArrayList<Attribute> featureList;
	private final Map<String, Integer> featureIndexMap;

	/**
	 * @param trainingSamples the data that should be used for training the classifier
	 * @param algorithm the concrete classification algorithm to use
	 * @throws ClassificationException
	 */
	public SentimentClassifier(List<TrainingSample> trainingSamples, Classifier algorithm)
			throws ClassificationException {
		classifier = new LibSVM();
		featureList = createFeatureList(trainingSamples);

		// debug output
		StringBuilder featureString = new StringBuilder();
		for (Attribute feature : featureList) {
			featureString.append('{');
			featureString.append(feature);
			featureString.append("}, ");
		}
		featureString.delete(featureString.length() - 2, featureString.length());
		logger.debug("The following " + featureList.size() + " features are used for classification: [" + featureString + "]");

		featureIndexMap = initFeatureIndexMap(featureList);
		trainingInstances = createInstances("train", trainingSamples, featureList);

		train();
	}

	private Map<String, Integer> initFeatureIndexMap(ArrayList<Attribute> featureList) {
		Map<String, Integer> featureIndexMap = new HashMap<>();
		for (int i = 0; i < featureList.size() - 1; i++) {
			Attribute feature = featureList.get(i);
			featureIndexMap.put(feature.name(), i);
		}
		return featureIndexMap;
	}

	private ArrayList<Attribute> createFeatureList(Iterable<TrainingSample>trainingSamples)
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
			Attribute sentimentAttribute = new Attribute("___sentiment___", sentiments);
			featureList.add(sentimentAttribute);

			return featureList;
		} catch (PreprocessingException e) {
			throw new ClassificationException(e);
		}
	}

	private Set<String> loadDistinctFeatures(Iterable<TrainingSample> trainingSamples)
			throws PreprocessingException {
		HashSet<String> features = new HashSet<>();
		for (TrainingSample trainingSample : trainingSamples) {
			FeatureVector featureVector = trainingSample.getFeatureVector();
			for (Feature feature : featureVector.getFeatures()) {
				features.add(feature.getWord());
			}
		}
		return features;
	}

	private void train() throws ClassificationException {
		try {
			classifier.buildClassifier(trainingInstances);
		} catch (Exception e) {
			throw new ClassificationException(e);
		}
	}

	/**
	 * Factory method to create an {@code Instances} object from the given training samples.
	 */
	private Instances createInstances(String name,
	                                  List<TrainingSample> trainingSamples,
	                                  ArrayList<Attribute> featureList) {
		Instances instances = new Instances(name, featureList, trainingSamples.size());
		instances.setClassIndex(featureList.size() - 1);

		for (TrainingSample trainingSample : trainingSamples) {
			FeatureVector featureVector = trainingSample.getFeatureVector();
			Sentiment sentiment = trainingSample.getSentiment();
			Instance instance = createInstance(instances, featureVector, sentiment);
			instances.add(instance);
		}

		return instances;
	}

	/**
	 * Factory method to create an {@code Instance} based on the given feature vector.
	 */
	private Instance createInstance(Instances instances, FeatureVector featureVector) {
		return createInstance(instances, featureVector, null);
	}

	/**
	 * Factory method to create an {@code Instance} based on the given feature vector and sentiment value.
	 */
	private Instance createInstance(Instances instances, FeatureVector featureVector, Sentiment sentiment) {
		SparseInstance instance = new SparseInstance(featureList.size());
		instance.setDataset(instances);

		for (Feature feature : featureVector.getFeatures()) {
			if (isUsedForClassification(feature.getWord())) {
				instance.setValue(featureIndexMap.get(feature.getWord()), 1.0);
			}
		}

		if (sentiment != null) {
			instance.setValue(featureList.size() - 1, intFromSentiment(sentiment));
		}

		double[] defaultValues = new double[featureList.size()];
		instance.replaceMissingValues(defaultValues);

		return instance;
	}

	/**
	 * Returns true if the string represented by {@code token} is used for classification (i.e. if it is contained
	 * in the {@code featureList} or false if it should be ignored.
	 */
	private boolean isUsedForClassification(String token) {
		return featureIndexMap.get(token) != null;
	}

	private int intFromSentiment(Sentiment sentiment) {
		if (sentiment == Sentiment.NEGATIVE) {
			return 0;
		} else {
			return 1;
		}
	}

	private String stringFromSentiment(Sentiment sentiment) {
		if (sentiment == Sentiment.NEGATIVE) {
			return "negative";
		} else {
			return "positive";
		}
	}

	@Override
	public Sentiment classify(FeatureVector featureVector) throws ClassificationException {
		Instances instances = new Instances("live", featureList, 1);
		instances.setClassIndex(featureList.size() - 1);
		Instance instanceToClassify = createInstance(instances, featureVector);
		instances.add(instanceToClassify);
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

	public Evaluation evaluate(List<TrainingSample> testSamples)
			throws ClassificationException {
		Instances testInstances = createInstances("test", testSamples, featureList);
		try {
			Evaluation evaluate = new Evaluation(trainingInstances);
			evaluate.evaluateModel(classifier, testInstances);
			return evaluate;
		} catch (Exception e) {
			throw new ClassificationException(e);
		}
	}
}
