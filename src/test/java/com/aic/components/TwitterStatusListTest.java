package com.aic.components;

import org.junit.* ;
import static org.junit.Assert.* ;

import com.aic.components.*;
import java.util.Date;

public class TwitterStatusListTest{

	@Test
	public void CheckRightBehaviourOfTrim() {
		TwitterStatusList tweetlist = new TwitterStatusList("microsoft");
		tweetlist.add(new TwitterStatus(new Date(10), "inkrement", "some #fancy tweet @twitter"));


		assertEquals(tweetlist.trim(new Date(20), new Date(30)).size(), 0 );
		assertEquals(tweetlist.trim(new Date(0), new Date(9)).size(), 0 );
		assertEquals(tweetlist.trim(new Date(9), new Date(12)).size(), 1 );
	}


}