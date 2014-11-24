package com.aic;

import com.aic.sentiment_analysis.classification.CSVTrainingSampleLoader;
import com.aic.sentiment_analysis.classification.TrainingSample;
import com.aic.sentiment_analysis.preprocessing.PreprocessingException;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager("sentiments");
	}
}
