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

		//TODO test smileys

		List<Token> tl = TweetProcess.tokenizer("some sentance #hashtag @mentioning");

    	assertEquals(tl.size(), 4);

    	Token t1 = tl.get(0);
    	Token t2 = tl.get(1);
    	Token t3 = tl.get(2);
    	Token t4 = tl.get(3);

    	assertEquals(t1.getType(), TokenType.WORD);
    	assertEquals(t2.getType(), TokenType.WORD);
    	assertEquals(t3.getType(), TokenType.TAG);
    	assertEquals(t4.getType(), TokenType.MENTIONING);
	}

	@Test
	public void RemoveSingleSpecialChar() {
		ArrayList<Token> tl = new ArrayList<Token>();
		
		try{
			tl.add(new Token("he°y"));
			tl.add(new Token("he^y"));
			tl.add(new Token("he!y"));
			tl.add(new Token("he\"y"));
			tl.add(new Token("he§y"));
			tl.add(new Token("he$y"));
			tl.add(new Token("he%y"));
			tl.add(new Token("he&y"));
			tl.add(new Token("he/y"));
			tl.add(new Token("he{y"));
			tl.add(new Token("he(y"));
			tl.add(new Token("he[y"));
			tl.add(new Token("he)y"));
			tl.add(new Token("he]y"));
			tl.add(new Token("he=y"));	
			tl.add(new Token("he}y"));
			tl.add(new Token("he?y"));
			tl.add(new Token("he\\y"));
			tl.add(new Token("he´y"));
			tl.add(new Token("he`y"));
			tl.add(new Token("he@y"));
			tl.add(new Token("he€y"));
			tl.add(new Token("he*y"));
			tl.add(new Token("he+y"));
			tl.add(new Token("he~y"));
			tl.add(new Token("he'y"));
			tl.add(new Token("he#y"));
			tl.add(new Token("he’y"));
			tl.add(new Token("he<y"));
			tl.add(new Token("he>y"));
			tl.add(new Token("he|y"));
			tl.add(new Token("he;y"));
			tl.add(new Token("he,y"));
			tl.add(new Token("he·y"));
			tl.add(new Token("he:y"));
			tl.add(new Token("he…y"));
			tl.add(new Token("he–y"));
			tl.add(new Token("he_y"));
			tl.add(new Token("he-y"));
		} catch(WrongTokenFormatException e){
			fail("should not throw WrongTokenFormatException");
		}

		List<Token> result = TweetProcess.replaceSpecialChars(tl);

		assertEquals(result.size(), 78);

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
		assertEquals(tl.get(0).getValue(), "this");
		assertEquals(tl.get(1).getValue(), "is");
		assertEquals(tl.get(2).getValue(), "sentence");
	}

	@Test
	public void removeRepeatedChars(){

		ArrayList<Token> tl = new ArrayList<Token>();

		try{
			tl.add(new Token("this"));
			tl.add(new Token("is"));
			tl.add(new Token("faalse"));
			tl.add(new Token("hahhaaa"));
		}catch (WrongTokenFormatException e){
			fail("remove Article should not throw WrongTokenFormatException");
		}

		TweetProcess.removeRepeatedChars(tl);

		assertEquals(tl.size(),4);
		
		assertEquals(tl.get(0).getValue(), "this");
		assertEquals(tl.get(1).getValue(), "is");
		assertEquals(tl.get(2).getValue(), "faalse");
		assertEquals(tl.get(3).getValue(), "hahha");
	}

}