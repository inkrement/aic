package com.aic.sentiment_analysis.fetch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter4j.*;
import twitter4j.conf.Configuration;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The class for fetching tweets.
 */
@Component
public class Fetch implements ITweetLoader {

	/** The maximum number of tweets to be fetched at once. */
	public static final int MAX_TWEETS = 50;

	private static final Log logger = LogFactory.getLog(Fetch.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
	private Configuration configuration;

	@Autowired
	public Fetch(Configuration configuration) {
		this.configuration = configuration;
	}

	/**
	 * Loads the tweets for a given {@code name} and a given period of time, i.e. calling {@link #fetch(String, Date, Date, long, long)} with the apropriate
	 * parameters.
	 * 
	 * @param name
	 *            The keyword
	 * @param start
	 *            The start date
	 * @param end
	 *            The end date
	 * @see #fetch(String, Date, Date, long, long)
	 * @see com.aic.sentiment_analysis.fetch.ITweetLoader#load(java.lang.String, java.util.Date, java.util.Date)
	 */
	public List<TwitterStatus> load(String name, Date start, Date end) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(end);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		end = cal.getTime();
		logger.debug("\n" + name + " (" + start + "-" + end + ")");

		List<TwitterStatus> result = new ArrayList<TwitterStatus>();

		long startId = 0;
		long endId;
		endId = Long.MAX_VALUE;
		result.addAll(fetch(name, start, end, startId, endId));

		return result;
	}

	/**
	 * Fetches (at most 100) tweets for the given name, filtered by the {@code start} and {@code end} date.
	 * <p>
	 * <b>Note:</b> "The Search API is not complete index of all Tweets, but instead an index of recent Tweets. At the moment that index includes between 6-9
	 * days of Tweets." (see <a href="https://dev.twitter.com/rest/public/search"> https://dev.twitter.com/rest/public/search</a>)
	 *
	 * @param name
	 *            The term the tweets should contain
	 * @param start
	 *            The start date for the search
	 * @param end
	 *            The end date for the search
	 * @param startId
	 *            The start ID for the search
	 * @param endId
	 *            The end ID for the search
	 * @return A list of tweets matching the criteria (at most 100)
	 * @see #load(String, Date, Date)
	 */
	private TwitterStatusList fetch(String name, Date start, Date end, long startId, long endId) {
		logger.debug("Fetch: " + name + ", start: " + start + ", end: " + end + " ids: " + startId + "-" + endId);
		TwitterStatusList tweetList = new TwitterStatusList(name);

		try {
			Twitter twitter = new TwitterFactory(configuration).getInstance();

			Query query = new Query(name);

			query.setSince(dateFormat.format(start));
			Calendar c = Calendar.getInstance();
			c.setTime(end);
			c.add(Calendar.DATE, 1);
			Date endPlusOne = c.getTime();
			query.setUntil(dateFormat.format(endPlusOne));
			query.setMaxId(endId);
			query.setSinceId(startId);
			query.setCount(MAX_TWEETS);
			query.setLang("en");

			QueryResult result;
			result = twitter.search(query);

			logger.debug(result.getCount());
			List<Status> tweets = result.getTweets();

			logger.debug(tweets.size());
			for (Status tweet: tweets) {
				tweetList.add(new TwitterStatus(tweet));
			}
			Collections.sort(tweetList);

		} catch (TwitterException e) {
			logger.error("Failed to search tweets", e);
		}

		tweetList.setDates(tweetList.size() == MAX_TWEETS ? tweetList.get(0).getDate() : start, end);
		return tweetList;
	}
}
