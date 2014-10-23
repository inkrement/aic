package com.aic.fetch;

import org.junit.* ;
import static org.junit.Assert.* ;

import com.aic.preprocessing.*;
import com.aic.components.*;
import com.aic.fetch.Cache;
import java.io.File;
import java.util.Date;

public class CacheTest{

	@Test
	public void fileCreated() {

		TwitterStatusList tweetlist = new TwitterStatusList("microsoft");
		Date timestamp = new Date();
		tweetlist.add(new TwitterStatus(timestamp, "inkrement", "some #fancy tweet @twitter"));

		Cache.store(tweetlist);

		File file = new File("/tmp/" + "microsoft_" + timestamp.getTime() + "_" + timestamp.getTime());
		assertTrue(file.exists());
	}
}