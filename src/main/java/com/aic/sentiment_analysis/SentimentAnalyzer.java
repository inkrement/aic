package com.aic.sentiment_analysis;

import com.aic.Constants;
import com.aic.sentiment_analysis.classification.*;
import com.aic.sentiment_analysis.feature.FeatureVector;
import com.aic.sentiment_analysis.fetch.Fetch;
import com.aic.sentiment_analysis.fetch.ITweetLoader;
import com.aic.sentiment_analysis.fetch.TwitterStatus;
import com.aic.sentiment_analysis.preprocessing.ISentimentPreprocessor;
import com.aic.sentiment_analysis.preprocessing.PreprocessingException;
import com.aic.sentiment_analysis.preprocessing.SentimentTwitterPreprocessor;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

/**
 * This class provides means to retrieve the public perception of a company,
 * by performing sentiment analysis on correlating twitter messages.
 */
public class SentimentAnalyzer {

	private ITweetLoader tweetLoader;
	private ISentimentClassifier classifier;
	private ISentimentPreprocessor preprocessor;

	public SentimentAnalyzer() throws SentimentAnalysisException {
		try {
			tweetLoader = new Fetch();
			CSVTrainingSampleLoader sampleLoader = new CSVTrainingSampleLoader();
			List<TrainingSample> samples = sampleLoader.load(new URI(Constants.CLASSIFIER_TRAINING_FILE_PATH));
			classifier = new SentimentClassifier(samples);
			preprocessor = new SentimentTwitterPreprocessor();
		} catch (PreprocessingException e) {
			throw new SentimentAnalysisException(e);
		} catch (FileNotFoundException e) {
			throw new SentimentAnalysisException(e);
		} catch (URISyntaxException e) {
			throw new SentimentAnalysisException(e);
		} catch (ClassificationException e) {
			throw new SentimentAnalysisException(e);
		}
	}

	/**
	 * Calculate the public perception of the company with the given name, based
	 * on the tweets that have been posted during a specific observation period.
	 *
	 * @param companyName the according company's name
	 * @param start the start of the observation period
	 * @param end the end of the observation period
	 * @return a value between 0.0 and 1.0, whereas higher values denote a more
	 *         positive public perception.
	 * @throws SentimentAnalysisException
	 */
	public float averageSentiment(String companyName, Date start, Date end)
			throws SentimentAnalysisException {
		try {
			List<TwitterStatus> tweets = tweetLoader.load(companyName, start, end);
			int positiveTweets = 0;

			for (TwitterStatus tweet : tweets) {
				FeatureVector featureVector = null;
				featureVector = preprocessor.preprocess(tweet.getText());

				Sentiment sentiment = classifier.classify(featureVector);
				if (sentiment == Sentiment.POSITIVE) {
					positiveTweets++;
				}
			}

			return (float) positiveTweets / tweets.size();
		} catch (PreprocessingException e) {
			throw new SentimentAnalysisException(e);
		} catch (ClassificationException e) {
			throw new SentimentAnalysisException(e);
		}
	}
}
