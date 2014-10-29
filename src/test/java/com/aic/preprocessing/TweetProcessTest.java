package com.aic.preprocessing;

import org.junit.* ;
import static org.junit.Assert.* ;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import com.aic.preprocessing.*;
import com.aic.components.*;

public class TweetProcessTest{

	@Test
	public void removeValidURL() {

		List<Token> tl = new ArrayList<Token>();

		try{
			tl.add(new Token("http://orf.at"));
		} catch (WrongTokenFormatException e){
			fail("should not throw WrongTokenFormatException");
		}

    	assertTrue(TweetProcess.removeURLs(tl).isEmpty());
	}

	@Test
	public void notRemoveInvalidURL() {

		List<Token> tl = new ArrayList<Token>();

		try{
			tl.add(new Token("someword"));
		} catch (WrongTokenFormatException e){
			fail("should not throw WrongTokenFormatException");
		}

    	assertFalse(TweetProcess.removeURLs(tl).isEmpty());
	}

	@Test
	public void tokenizeSentence() {

		List<Token> tl = TweetProcess.tokenizer("some sentance");

    	assertEquals(tl.size(), 2);
	}

	@Test
	public void RemoveSingleSpecialChar() {
		ArrayList<Token> tl = new ArrayList<Token>();
		
		try{
			tl.add(new Token("he-y"));
		} catch(WrongTokenFormatException e){
			fail("should not throw WrongTokenFormatException");
		}

		List<Token> result = TweetProcess.replaceSpecialChars(tl);

		assertEquals(result.size(), 2);

		for(Token t: result) assertTrue(t.getToken().equals("he") || t.getToken().equals("y"));
	}

	@Test
	public void RemoveArticles(){
		ArrayList<Token> tl = new ArrayList<Token>();

		try{
			tl.add(new Token("this"));
			tl.add(new Token("is"));
			tl.add(new Token("the"));
			tl.add(new Token("sentence"));
		}catch (WrongTokenFormatException e){
			fail("remove Article should not throw WrongTokenFormatException");
		}

		TweetProcess.removeArticles(tl);

		assertEquals(tl.size(), 3);
	}
}