package com.aic.fetch;

import com.aic.components.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * The class for fetching tweets.
 * 
 * @author Florian Taus
 */
public class Fetch
{
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
	private final static String CONFIGURATION_FILE = "twitter.properties";



	public static List<TwitterStatus> get(String name, Date start, Date end){

		List<TwitterStatus> result = new ArrayList<TwitterStatus>();

		List<TwitterStatusList> cached_tweets = Cache.load(name, start, end);

		//TODO: calculate missing time ranges

		//TODO: fetch missing timeranges

		return result;
	}

	/**
	 * Fetches (at most 100) tweets for the given name, filtered by the
	 * {@code start} and {@code end} date.
	 * <p>
	 * <b>Note:</b>
	 * "The Search API is not complete index of all Tweets, but instead an index of recent Tweets. At the moment that index includes between 6-9 days of Tweets."
	 * (see {@link https://dev.twitter.com/rest/public/search})
	 * </p>
	 * 
	 * @param name
	 *            The term the tweets should conatin
	 * @param start
	 *            The start date for the search
	 * @param end
	 *            The end date for the search
	 * @return A list of tweets matching the criterias (at most 100)
	 */
	public static TwitterStatusList fetch(String name, Date start, Date end)
	{
		TwitterStatusList tweetList = new TwitterStatusList(name);

		try
		{
			twitter4j.Twitter twitter = new twitter4j.TwitterFactory(getConfiguration()).getInstance();

			twitter4j.Query query = new twitter4j.Query(name);

			query.setSince(dateFormat.format(start));
			query.setUntil(dateFormat.format(end));
			query.setCount(100);

			twitter4j.QueryResult result;
			result = twitter.search(query);

			List<twitter4j.Status> tweets = result.getTweets();

			for (twitter4j.Status tweet: tweets)
			{
				tweetList.add(new TwitterStatus(tweet));
			}
			
		}
		catch (twitter4j.TwitterException e)
		{
			e.printStackTrace();
			System.err.println("Failed to search tweets: " + e.getMessage());
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.err.println("Failed to open configuration: " + e.getMessage());
		}

		Cache.store(tweetList);

		return tweetList;
	}

	private static twitter4j.conf.Configuration getConfiguration() throws IOException
	{
		twitter4j.conf.ConfigurationBuilder cb = new twitter4j.conf.ConfigurationBuilder();
		cb.setDebugEnabled(true);
		Properties props = new Properties();
		props.load(new FileInputStream(CONFIGURATION_FILE));
		if (props.containsKey("consumerKey") && props.containsKey("consumerSecret") && props.containsKey("accessToken") && props.containsKey("accessTokenSecret"))
		{
			cb.setOAuthConsumerKey(props.getProperty("consumerKey"));
			cb.setOAuthConsumerSecret(props.getProperty("consumerSecret"));
			cb.setOAuthAccessToken(props.getProperty("accessToken"));
			cb.setOAuthAccessTokenSecret(props.getProperty("accessTokenSecret"));
			return cb.build();
		}
		else
		{
			throw new IOException("Credentials incomplete!");
		}
	}
}
