package com.aic.sentiment_analysis.fetch;

import org.junit.* ;
import static org.junit.Assert.* ;

import java.io.File;
import java.util.Date;

import java.io.IOException;

public class CacheTest{

	@Before
	public void before() throws IOException
	{
		Cache.clear();
	}
	
	@After
	public void after() throws IOException
	{
		Cache.clear();
	}
	
	@Test
	public void fileCreated() throws IOException {
		Date timestamp = new Date();
        TwitterStatusList tweetlist = new TwitterStatusList("microsoft");
		tweetlist.add(new TwitterStatus(timestamp, "inkrement", "some #fancy tweet @twitter"));
		tweetlist.setDates(timestamp, timestamp);

        System.out.println("tweetlist to store: " + tweetlist);
		Cache.store(tweetlist);

        System.out.println("Pfad: " + Cache.PATH);
		File file = new File(Cache.PATH + "microsoft_" + timestamp.getTime() + "_" + timestamp.getTime() + ".tmp");

		System.out.println("fileCreated path: " + file.getAbsolutePath());
		System.out.println(new File(Cache.PATH).getAbsolutePath());
		assertTrue(file.exists());
	}


	@Test
	public void findAllKeywordFilePaths() throws IOException{
		String keyword = "microsoft";

			
		Date timestamp = new Date();
		TwitterStatusList tweetlist = new TwitterStatusList(keyword);
		tweetlist.add(new TwitterStatus(timestamp, "inkrement", "some #fancy tweet @twitter"));
		tweetlist.setDates(timestamp, timestamp);
		
		Cache.store(tweetlist);

		File[] files = Cache.getFilePaths(keyword);
		
		assertEquals(1, files.length);
		//System.out.println("files found: " + files[0].toString());
		//System.out.println( "should be: " + Cache.path + "/" + keyword + "_" + timestamp.getTime() + "_" + timestamp.getTime() );
		assertEquals(files[0].toString(), Cache.PATH + keyword + "_" + timestamp.getTime() + "_" + timestamp.getTime() +".tmp");
	}

	@Test
	public void clearCache() throws IOException {
		String filepath = Cache.PATH.toString() + "somerandomfile";
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
		assertEquals(new File(Cache.PATH).list().length,0);
	}


	@Test
	public void testLoadFile() throws IOException{
		Cache.clear();

		Date timestamp = new Date();
		TwitterStatusList tweetlist = new TwitterStatusList("somekeyword");
		tweetlist.add(new TwitterStatus(timestamp, "inkrement", "some #fancy tweet @twitter"));
		tweetlist.setDates(timestamp, timestamp);
		
		File filetoload = Cache.store(tweetlist);

		TwitterStatusList loadedlist = Cache.loadFile(filetoload);

		assertEquals(tweetlist, loadedlist);
	}
}