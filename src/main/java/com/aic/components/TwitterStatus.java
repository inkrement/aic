package com.aic.components;

import twitter4j.*;
import java.util.*;

public class TwitterStatus{
	private List<Token> token;
	private Date timestamp;
	private int favouriteCount;
	private int retweetCount;
	private Place location;
	private String username;


	public TwitterStatus(Status twitter4jstatus){
		timestamp = twitter4jstatus.getCreatedAt();
		favouriteCount = twitter4jstatus.getFavoriteCount();
		retweetCount = twitter4jstatus.getRetweetCount();
		location = twitter4jstatus.getPlace();
		username = twitter4jstatus.getUser().getScreenName();

		token = com.aic.preprocessing.TweetProcess.preprocess(twitter4jstatus.getText());
	}


	public String toString(){
		return username;
	}

}