package com.aic.sentiment_analysis.fetch;

import java.util.Date;
import java.util.List;

/**
 * Provides means to load tweets from some data source.
 */
public interface ITweetLoader {

	/**
	 * Loads tweets that concern the company with the given {@code companyName}
	 * and that have been posted between {@code start} and {@code end}.
	 *
	 * @param companyName the according company's name
	 * @param start the according start date
	 * @param end the according end date
	 */
	public List<TwitterStatus> load(String companyName, Date start, Date end);
}
