package com.aic;

import com.aic.sentiment_analysis.classification.CSVTrainingSampleLoader;
import com.aic.sentiment_analysis.classification.TrainingSample;
import com.aic.sentiment_analysis.preprocessing.ISentimentPreprocessor;
import com.aic.sentiment_analysis.preprocessing.PreprocessingException;
import com.aic.sentiment_analysis.preprocessing.SentimentTwitterPreprocessor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;

@Configuration
@EnableCaching
public class ApplicationConfig {

	@Bean
	public Iterable<TrainingSample>	trainingSamples() throws URISyntaxException,
			PreprocessingException, FileNotFoundException {
		CSVTrainingSampleLoader sampleLoader = new CSVTrainingSampleLoader();
		URI trainingSetUri = getClass().getClassLoader().
				getResource(Constants.CLASSIFIER_TRAINING_FILE_PATH).toURI();
		List<TrainingSample> samples = sampleLoader.load(trainingSetUri);
		return samples;
	}

	@Bean
	public twitter4j.conf.Configuration twitterConfiguration() throws IOException {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		Properties props = new Properties();
		props.load(getClass().getClassLoader().getResourceAsStream("twitter.properties"));
		if (props.containsKey("consumerKey")
				&& props.containsKey("consumerSecret")
				&& props.containsKey("accessToken")
				&& props.containsKey("accessTokenSecret")) {
			cb.setOAuthConsumerKey(props.getProperty("consumerKey"));
			cb.setOAuthConsumerSecret(props.getProperty("consumerSecret"));
			cb.setOAuthAccessToken(props.getProperty("accessToken"));
			cb.setOAuthAccessTokenSecret(props.getProperty("accessTokenSecret"));
			return cb.build();
		} else {
			throw new IOException("Credentials incomplete!");
		}
	}

    @Bean
    public ISentimentPreprocessor sentimentPreprocessor(){
        return new SentimentTwitterPreprocessor();
    }

	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager("sentiments");
	}
}
