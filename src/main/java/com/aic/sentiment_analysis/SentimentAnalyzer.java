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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

/**
 * This class provides means to retrieve the public perception of a company,
 * by performing sentiment analysis on correlating twitter messages.
 */
@Component
public class SentimentAnalyzer {

	private static final Log logger = LogFactory.getLog(SentimentAnalyzer.class);

	private ITweetLoader tweetLoader;
	private ISentimentClassifier classifier;
	private ISentimentPreprocessor preprocessor;

	@Autowired
	public SentimentAnalyzer(ITweetLoader tweetLoader,
	                         ISentimentClassifier classifier,
	                         ISentimentPreprocessor preprocessor)
			throws SentimentAnalysisException {
		this.tweetLoader = tweetLoader;
		this.classifier = classifier;
		this.preprocessor = preprocessor;
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
	@Cacheable("sentiments")
	public float averageSentiment(String companyName, Date start, Date end)
			throws SentimentAnalysisException {
		try {
			List<TwitterStatus> tweets = tweetLoader.load(companyName, start, end);

			if (tweets.size() == 0) {
				throw new SentimentAnalysisException("No tweets for available.");
			}

			int positiveTweets = 0;

			for (TwitterStatus tweet : tweets) {
				FeatureVector featureVector = null;
				featureVector = preprocessor.preprocess(tweet.getText());

				Sentiment sentiment = classifier.classify(featureVector);
				logger.debug("Classified \"" + tweet.getText() +
						"\" as " + sentiment.name());

				if (sentiment == Sentiment.POSITIVE) {
					positiveTweets++;
				}
			}

			float aggregatedSentiment = (float) positiveTweets / tweets.size();
			logger.debug(String.format("Aggregated sentiment is %.2f",
					aggregatedSentiment));
			return aggregatedSentiment;
		} catch (PreprocessingException e) {
			throw new SentimentAnalysisException(e);
		} catch (ClassificationException e) {
			throw new SentimentAnalysisException(e);
		}
	}
}
