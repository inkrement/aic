package com.aic.sentiment_analysis;

/**
 * The different types of classifier configurations, that are supported by the
 * {@link com.aic.sentiment_analysis.SentimentAnalyzer}.
 */
public enum ClassifierConfiguration {
	SVM_C_SVC,
	SVM_NU_SVC,
	SMO,
	NAIVE_BAYES,
	BAYES_NET,
	KNN
}
