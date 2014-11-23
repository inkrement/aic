package com.aic.sentiment_analysis.fetch;

import twitter4j.Place;
import twitter4j.Status;

import java.io.Serializable;
import java.util.Date;

public class TwitterStatus implements Serializable, Comparable<TwitterStatus>
{
	private static final long serialVersionUID = 7526472295654436147L;

	private long id;
    private int favouriteCount;
	private int retweetCount;
	private Place location;
	private String username;
	private String text;
	private Date timestamp;

	public TwitterStatus(Status twitter4jstatus) {
		id = twitter4jstatus.getId();
		timestamp = twitter4jstatus.getCreatedAt();
		favouriteCount = twitter4jstatus.getFavoriteCount();
		retweetCount = twitter4jstatus.getRetweetCount();
		location = twitter4jstatus.getPlace();
		username = twitter4jstatus.getUser().getScreenName();
		text = twitter4jstatus.getText();
	}

	/**
	 * only for testpurposes
	 */
	public TwitterStatus(Date timestamp, String username, String text) {
		this.timestamp = timestamp;
		this.favouriteCount = 0;
		this.retweetCount = 0;
		this.location = null;
		this.username = username;
		this.id = -1;
	}

	public long getId()
	{
		return id;
	}

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

	public String toString() {
		return timestamp.toString() + " [" + username + "] " + text;
	}

	public long getTimestamp() {
		return timestamp.getTime();
	}

	public Date getDate() {
		return timestamp;
	}

	public String getText() {
		return text;
	}

	@Override
	public int compareTo(TwitterStatus other) {
		return timestamp.compareTo(other.getDate());
	}

	@Override
	public int hashCode() {
		return (int) (id ^ (id >>> 32));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof TwitterStatus)) return false;
		TwitterStatus other = (TwitterStatus) obj;
		if (id != other.id) return false;
		return true;
	}
}
