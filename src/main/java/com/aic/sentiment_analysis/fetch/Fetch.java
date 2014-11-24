package com.aic.sentiment_analysis.fetch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import twitter4j.*;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The class for fetching tweets.
 * 
 * @author Florian Taus
 */
public class Fetch implements ITweetLoader {

	private static final Log logger = LogFactory.getLog(Fetch.class);
	public static final int MAX_TWEETS = 100;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
	private static final String CONFIGURATION_FILE = "twitter.properties";

	public List<TwitterStatus> load(String name, Date start, Date end) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(end);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		end = cal.getTime();
		logger.debug("\n" + name + " (" + start + "-" + end + ")");
		
		List<TwitterStatus> result = new ArrayList<TwitterStatus>();
//		List<TwitterStatusList> cached_tweets = Cache.load(name, start, end);
//		System.out.println("1: " + start);
//		Collections.sort(cached_tweets);
//		System.out.println("2: " + start);
//		System.out.println("Number of cached tweet lists: " + cached_tweets.size());

		long startId = 0;
		long endId;
//		for (TwitterStatusList tsl: cached_tweets)
//		{
//			System.out.println("Cached file: start: " + tsl.getStart() + ", end: " + tsl.getEnd());
//			endId = tsl.getStartId() - 1;
//			System.out.println("3: " + start);
//			System.out.println("Start of range: " + start + ", start of file: " + tsl.getStart());
//			if (!start.equals(tsl.getStart()))
//			{
//				System.out.println("4: " + start);
//				result.addAll(fetch(name, start, tsl.getStart(), startId, tsl.getStartId() - 1));
//			}
//			result.addAll(tsl);
//			start = tsl.getEnd();
//			startId = tsl.getEndId() + 1;
//
//		}
//		if (!start.equals(end))
//		{
			endId = Long.MAX_VALUE;
//			System.out.println("end of last file: " + start + ", end of range: " + end);
			result.addAll(fetch(name, start, end, startId, endId));
//		}

		return result;
	}

	/**
	 * Fetches (at most 100) tweets for the given name, filtered by the
	 * {@code start} and {@code end} date.
	 * <p>
	 * <b>Note:</b>
	 * "The Search API is not complete index of all Tweets, but instead an index
	 * of recent Tweets. At the moment that index includes between 6-9 days of
	 * Tweets."
	 * (see <a href="https://dev.twitter.com/rest/public/search">
	 *     https://dev.twitter.com/rest/public/search</a>)
	 *
	 * @param name The term the tweets should contain
	 * @param start The start date for the search
	 * @param end The end date for the search
	 * @param endId
	 * @param startId
	 * @return A list of tweets matching the criteria (at most 100)
	 */
	private TwitterStatusList fetch(String name, Date start, Date end, long startId, long endId) {
		logger.debug("Fetch: " + name + ", start: " + start + ", end: " + end +
					 " ids: " + startId + "-" + endId);
		TwitterStatusList tweetList = new TwitterStatusList(name);

		try {
			Twitter twitter = new TwitterFactory(getConfiguration()).getInstance();

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
		} catch (IOException e) {
			logger.error("Failed to open configuration", e);
		}

		// if (!tweetList.isEmpty())
		// {
		tweetList.setDates(tweetList.size() == MAX_TWEETS ? tweetList.get(0).getDate() : start, end);
		Cache.store(tweetList);
		logger.debug("Stored");
		// }

		return tweetList;
	}

	private Configuration getConfiguration() throws IOException {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		Properties props = new Properties();
		props.load(getClass().getClassLoader().getResourceAsStream(CONFIGURATION_FILE));
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
}
