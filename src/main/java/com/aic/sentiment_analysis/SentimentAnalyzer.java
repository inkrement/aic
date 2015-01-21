package com.aic.sentiment_analysis;

import com.aic.sentiment_analysis.classification.ClassificationException;
import com.aic.sentiment_analysis.classification.ISentimentClassifier;
import com.aic.sentiment_analysis.classification.Sentiment;
import com.aic.sentiment_analysis.feature.FeatureVector;
import com.aic.sentiment_analysis.fetch.ITweetLoader;
import com.aic.sentiment_analysis.fetch.TwitterStatus;
import com.aic.sentiment_analysis.preprocessing.ISentimentPreprocessor;
import com.aic.sentiment_analysis.preprocessing.PreprocessingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * This class provides means to retrieve the public perception of a company,
 * by performing sentiment analysis on correlating twitter messages.
 */
@Component
public class SentimentAnalyzer {

	private static final Log logger = LogFactory.getLog(SentimentAnalyzer.class);

    @Autowired
	private ITweetLoader tweetLoader;
    @Autowired
	private ISentimentPreprocessor preprocessor;
    @Autowired
    @Qualifier("svm_C_SVC")
	private ISentimentClassifier svm_C_SVC;
    @Autowired
    @Qualifier("svm_NU_SVC")
	private ISentimentClassifier svm_NU_SVC;
    @Autowired
    @Qualifier("smo")
	private ISentimentClassifier smo;
    @Autowired
    @Qualifier("naiveBayes")
	private ISentimentClassifier naiveBayes;
    @Autowired
    @Qualifier("bayesNet")
	private ISentimentClassifier bayesNet;
    @Autowired
    @Qualifier("kNN")
	private ISentimentClassifier kNN;

    /**
     * Calculates a sentiment value based on an aggregation of tweets and
     * each tweet classified by a sentiment
     * {@link com.aic.sentiment_analysis.classification.Sentiment} value based
     * on the tweets that have been posted during a specific observation period.
     *
     * @param companyName the according company's name
     * @param start the start of the observation period
     * @param end the end of the observation period
     * @return an aggregated sentiment
     * @throws SentimentAnalysisException
     */
    @Cacheable("aggregateSentiment")
    public AggregateSentiment aggregateSentiment(String companyName, Date start, Date end, ClassifierConfiguration config)
            throws SentimentAnalysisException {
        AggregateSentiment aggregateSentiment = new AggregateSentiment();
        try {
            List<TwitterStatus> tweets = tweetLoader.load(companyName, start, end);

            if (tweets.size() == 0) {
                throw new SentimentAnalysisException("No tweets available for analysis.");
            }

            for (TwitterStatus tweet : tweets) {
                FeatureVector featureVector = null;
                featureVector = preprocessor.preprocess(tweet.getText());

	            ISentimentClassifier classifier = classifierForConfiguration(config);
                Sentiment sentiment = classifier.classify(featureVector);

                aggregateSentiment.putSentiment(tweet, sentiment);

                logger.debug("Classified \"" + tweet.getText() +
                        "\" as " + sentiment.name());
            }

            logger.debug(String.format("Aggregated sentiment is %.2f",
                    aggregateSentiment.calculateAggregateSentimentRatio()));
			logger.debug(String.format("Weighted Aggregated sentiment is %.2f",
					aggregateSentiment.calculateDateWeightedAggregateSentimentRatio()));
            return aggregateSentiment;
        } catch (PreprocessingException e) {
            throw new SentimentAnalysisException(e);
        } catch (ClassificationException e) {
            throw new SentimentAnalysisException(e);
        }
    }

	private ISentimentClassifier classifierForConfiguration(ClassifierConfiguration config) {
		switch (config) {
            case SVM_NU_SVC:
                return svm_NU_SVC;
            case SMO:
                return smo;
			case NAIVE_BAYES:
				return naiveBayes;
            case BAYES_NET:
                return bayesNet;
            case KNN:
                return kNN;
			default:
				return svm_C_SVC;
		}
	}
}
