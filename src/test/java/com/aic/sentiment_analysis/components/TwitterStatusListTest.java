package com.aic.sentiment_analysis.components;

import com.aic.sentiment_analysis.fetch.TwitterStatusList;
import org.junit.* ;
import static org.junit.Assert.* ;

import java.util.Date;

public class TwitterStatusListTest{

	@Test
	public void CheckRightBehaviourOfTrim() {
		TwitterStatusList tweetlist = new TwitterStatusList("microsoft");
		tweetlist.add(new TwitterStatus(new Date(25), "inkrement", "some #fancy tweet @twitter"));

        //TODO remove this line and override add in TwitterStatusList
        //tweetlist.setDates(new Date(20), new Date(30));

		assertEquals(tweetlist.trim(new Date(20), new Date(30)).size(), 1 );
	    assertEquals(tweetlist.trim(new Date(0), new Date(9)).size(), 0 );
		assertEquals(tweetlist.trim(new Date(9), new Date(12)).size(), 0 );
	}

	@Test
	public void changeStartEndDate(){

		TwitterStatusList tweetlist = new TwitterStatusList("Hotzinger");
		tweetlist.add(new TwitterStatus(new Date(25), "inkrement", "some #fancy tweet @twitter"));

		//Start and End is the same
		assertEquals(tweetlist.getStart().getTime(), 25);
		assertEquals(tweetlist.getEnd().getTime(), 25);

		//Add a new tweet with former timestamp
		tweetlist.add(new TwitterStatus(new Date(20), "pichlbaer", "yiha it's a #tweet"));

		//Start-Date should have changed
		assertEquals(tweetlist.getStart().getTime(), 20);
		assertEquals(tweetlist.getEnd().getTime(), 25);

		//Add a new tweet with later timestamp
		tweetlist.add(new TwitterStatus(new Date(30), "tausinger", "wohu it hopefully works"));

		//End-Date should have changed
		assertEquals(tweetlist.getStart().getTime(), 20);
		assertEquals(tweetlist.getEnd().getTime(), 30);


		//Add more tweets with various timestamps
		tweetlist.add(new TwitterStatus(new Date(13), "Hugo", "wohu it hopefully works"));
		tweetlist.add(new TwitterStatus(new Date(330), "Hugo1", "wohu it hopefully works"));
		tweetlist.add(new TwitterStatus(new Date(85), "Hugo2", "wohu it hopefully works"));
		tweetlist.add(new TwitterStatus(new Date(13), "Hugo3", "wohu it hopefully works"));
		tweetlist.add(new TwitterStatus(new Date(2), "Hugo4", "wohu it hopefully works"));

		//Start and Ende-Date should have changed
		assertEquals(tweetlist.getStart().getTime(), 2);
		assertEquals(tweetlist.getEnd().getTime(), 330);
	}


}