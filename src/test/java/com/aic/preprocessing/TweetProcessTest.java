package com.aic.preprocessing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TweetProcessTest{

	@Test
	public void removeValidURL() {
    	assertTrue(TweetProcess.removeURLs("http://orf.at").isEmpty());
        assertEquals(TweetProcess.removeURLs("hey http://orf.at"), "hey ");
        assertEquals(TweetProcess.removeURLs("hey du"), "hey du");
	}


	@Test
	public void removeRepeatedChars(){
        System.out.println(TweetProcess.removeRepeatedChars("thissss") + "(thissss)");
        System.out.println(TweetProcess.removeRepeatedChars("thisss") + "(thisss)");
        System.out.println(TweetProcess.removeRepeatedChars("thiss") + "(thiss)");
        System.out.println(TweetProcess.removeRepeatedChars("this") + "(this)");

		assertEquals(TweetProcess.removeRepeatedChars("this"), "this");
        assertEquals(TweetProcess.removeRepeatedChars("thisssss"), "this");
        assertEquals(TweetProcess.removeRepeatedChars("thiis"), "thiis");
        assertEquals(TweetProcess.removeRepeatedChars("thisss"), "thisss");
	}

}