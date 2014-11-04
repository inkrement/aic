package com.aic.components;

import twitter4j.Place;
import twitter4j.Status;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.aic.preprocessing.TweetProcess;

public class TwitterStatus implements Serializable, Comparable<TwitterStatus>
{
	private static final long serialVersionUID = 7526472295654436147L;
	private List<TaggedTwitterWord> content;
	private Date timestamp;

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public int getFavouriteCount() {
        return favouriteCount;
    }

    public void setFavouriteCount(int favouriteCount) {
        this.favouriteCount = favouriteCount;
    }

    public Place getLocation() {
        return location;
    }

    public void setLocation(Place location) {
        this.location = location;
    }

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
			
			content = TweetProcess.preprocess(twitter4jstatus.getText());
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

			content = TweetProcess.preprocess(text);
		}

	public String toString()
	{
		return timestamp.toString() + " [" + username + "] " + join(content);
	}

	private static String join(List<TaggedTwitterWord> treeList)
	{
		StringBuilder sb = new StringBuilder();
		for (TaggedTwitterWord t: treeList)
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

	@Override
	public int hashCode()
	{
		return (int) (id ^ (id >>> 32));
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		TwitterStatus other = (TwitterStatus) obj;
		if (id != other.id) return false;
		return true;
	}
}
