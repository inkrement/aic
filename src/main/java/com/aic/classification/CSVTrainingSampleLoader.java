package com.aic.classification;

import com.aic.preprocessing.ISentimentPreprocessor;
import com.aic.preprocessing.PreprocessingException;
import com.aic.preprocessing.SentimentTwitterPreprocessor;
import com.aic.shared.FeatureVector;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Loads the training data that needed for classification from a CVS file.
 */
public class CSVTrainingSampleLoader {

	public Map<FeatureVector, Sentiment> load(String pathToCSV)
			throws PreprocessingException {
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
}
