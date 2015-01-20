package com.aic.sentiment_analysis.classification;

import com.aic.sentiment_analysis.feature.FeatureVector;
import com.aic.sentiment_analysis.preprocessing.ISentimentPreprocessor;
import com.aic.sentiment_analysis.preprocessing.PreprocessingException;
import com.aic.sentiment_analysis.preprocessing.SentimentTwitterPreprocessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Provides means to load classification training data from a CSV file that
 * follows the STS-Gold-Dataset format.
 *
 * @see <a href="http://tweenator.com/index.php?page_id=13">STS-Gold-Dataset</a>
 */
public class CSVTrainingSTSLoader implements ICSVTrainingLoader {

	/**
	 * Loads the training data that needed for classification from a CVS file.
	 */
	@Override
	public List<TrainingSample> load(URI uri)
			throws PreprocessingException, FileNotFoundException {
		List<TrainingSample> trainingSamples = new ArrayList<>();
		ISentimentPreprocessor preprocessor = new SentimentTwitterPreprocessor();
		Scanner scanner = new Scanner(new File(uri));

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] columns = line.substring(0, line.length() - 1).split(";");

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

			TrainingSample trainingSample = new TrainingSample(featureVector, sentiment);
			trainingSamples.add(trainingSample);
		}

		scanner.close();
		return trainingSamples;
	}
}
