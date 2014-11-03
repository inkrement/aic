package com.aic.fetch;

import org.junit.* ;
import static org.junit.Assert.* ;

import com.aic.preprocessing.*;
import com.aic.components.*;
import com.aic.fetch.Cache;
import java.io.File;
import java.util.Date;

import java.io.IOException;

public class CacheTest{

	@Test
	public void fileCreated() {
		Cache.clear();

		Date timestamp = new Date();
        TwitterStatusList tweetlist = new TwitterStatusList("microsoft");
		tweetlist.add(new TwitterStatus(timestamp, "inkrement", "some #fancy tweet @twitter"));

        System.out.println("tweetlist to store: " + tweetlist);
		Cache.store(tweetlist);

        System.out.println("Pfad: " + Cache.path);
		File file = new File(Cache.path + File.separator + "microsoft_" + timestamp.getTime() + "_" + timestamp.getTime());

		System.out.println("fileCreated path: " + file);
		assertTrue(file.exists());

		Cache.clear();
	}


	@Test
	public void findAllKeywordFilePaths(){
		String keyword = "microsoft";

		Cache.clear();

		Date timestamp = new Date();
		TwitterStatusList tweetlist = new TwitterStatusList(keyword);
		tweetlist.add(new TwitterStatus(timestamp, "inkrement", "some #fancy tweet @twitter"));

		Cache.store(tweetlist);

		File[] files = Cache.getFilePaths(keyword);
		
		assertEquals(files.length, 1);
		//System.out.println("files found: " + files[0].toString());
		//System.out.println( "should be: " + Cache.path + "/" + keyword + "_" + timestamp.getTime() + "_" + timestamp.getTime() );
		assertEquals(files[0].toString(), Cache.path + File.separator + keyword + "_" + timestamp.getTime() + "_" + timestamp.getTime() );

		Cache.clear();
	}

	@Test
	public void clearCache() {
		String filepath = Cache.path.toString() + File.separator + "somerandomfile";
		System.out.println("filepath: " + filepath);

		File f = new File(filepath);
		f.mkdirs();
		try{
			f.createNewFile();
		} catch (IOException e){
			fail("this assertion should never be thrown");
		}

		Cache.clear();

		File file = new File(filepath);
		assertFalse(file.exists());
	}


	@Test
	public void testLoadFile(){
		Cache.clear();

		Date timestamp = new Date();
		TwitterStatusList tweetlist = new TwitterStatusList("somekeyword");
		tweetlist.add(new TwitterStatus(timestamp, "inkrement", "some #fancy tweet @twitter"));

		File filetoload = Cache.store(tweetlist);

		TwitterStatusList loadedlist = Cache.loadFile(filetoload);

		assertEquals(tweetlist, loadedlist);

		Cache.clear();
	}
}