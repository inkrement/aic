package com.aic.components;

import edu.stanford.nlp.trees.Tree;
import twitter4j.*;

import java.util.*;
import java.io.Serializable;

public class TwitterStatus implements Serializable, Comparable<TwitterStatus>
{
	private static final long serialVersionUID = 7526472295654436147L;
	private List<Tree> token;
	private Date timestamp;
	private int favouriteCount;
	private int retweetCount;
	private Place location;
	private String username;
	private long id;

	public TwitterStatus(Status twitter4jstatus)
		{
			timestamp = twitter4jstatus.getCreatedAt();
			favouriteCount = twitter4jstatus.getFavoriteCount();
			retweetCount = twitter4jstatus.getRetweetCount();
			location = twitter4jstatus.getPlace();
			username = twitter4jstatus.getUser().getScreenName();
			id = twitter4jstatus.getId();
			
			token = com.aic.preprocessing.TweetProcess.preprocess(twitter4jstatus.getText());
		}

	/**
	 * only for testpurposes
	 */
	public TwitterStatus(Date timestamp, String username, String text)
		{
			this.timestamp = timestamp;
			this.favouriteCount = 0;
			this.retweetCount = 0;
			this.location = null;
			this.username = username;
			this.id = -1;

			token = com.aic.preprocessing.TweetProcess.preprocess(text);
		}

	public String toString()
	{
		return timestamp.toString() + " [" + username + "] " + join(token);
	}

	private static String join(List<Tree> treeList)
	{
		StringBuilder sb = new StringBuilder();
		for (Tree t: treeList)
		{
			sb.append(t);
		}
		return sb.toString();

	}

	public long getTimestamp()
	{
		return timestamp.getTime();
	}

	public Date getDate()
	{
		return timestamp;
	}
	
	public long getId()
	{
		return id;
	}

	@Override
	public int compareTo(TwitterStatus other)
	{
		return timestamp.compareTo(other.getDate());
	}
}
