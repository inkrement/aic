package com.aic.preprocessing;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import com.aic.components.*;

public class TweetProcess {


	private String input = new String();

	/**
	 * do all preprocessing steps at once
	 *
	 * @param String input content of a tweet
	 */
	public static List<Token> preprocess(String input){
		return tokenizer(input);
	}

	/**
	 * filter unuseful words
	 */
	public static List<Token> filter(List<Token> tokens){
		//TODO remove URLS
		return removeArticles(tokens);
	}

	/**
	 * transform word
	 * example: verb to noun
	 *
	 * @param String input multiple words
	 */
	public static Token transform(Token input){
		return null;
	}

	public static List<Token> tokenizer(String tweet){
		//plus added to remove multiple spaces
		String[] tokens = tweet.split(" +");
		ArrayList<Token> tl = new ArrayList<Token>();

		for (String t: tokens) {

			//skip punctuation
			if(t.length() < 2) continue;

			//trim punctuation
			t = t.replaceAll("[^a-zA-Z0-9]", "");

			try{
				tl.add(new Token(t));
			} catch (WrongTokenFormatException e){
				//TODO: log skipped word.
			}
		}

		return tl;
	}

	/**
	 *
	 */
	private static Token verbToNoun(Token verb){
		return null;
	}

	/**
	 * remove articles
	 * TODO add more articles and others (prepositions, etc.). Maybe rename method
	 */
	public static List<Token> removeArticles(List<Token> tokens){
		
		//TODO add all articles and preposition, etc. maybe use lib/dict
		String[] articles = {"the"};

		Iterator<Token> iterator = tokens.iterator();
		
		while(iterator.hasNext()){

			Token tn = iterator.next();
			
			if(Arrays.asList(articles).contains(tn.getValue()))
				iterator.remove();
		}

		return tokens;
	}

	/**
	* Remove multiple character-occurences
	* TODO: Filter fuer Woerter mit doppelbuchstaben, z.B. beer
	* example: heyyyyyy -> hey
	*/
	private static List<Token> removeMultipleChars(List<Token> tokens){

		

		return null;
	}


	/**
	* Trimms Token-Values
	* example: haaa   llo -> haaa llo
	*/
	private static List<Token> removeMultipleWhitespaces(List<Token> tokens){

		//TODO implement

		return null;
	}

	/**
	* Split Token with special characters (spc) and remove the spc
	* TODO alle werte Testen
	*/
	public static List<Token> replaceSpecialChars(List<Token> tokens){
		
		//String regex ="[_:.+-,!$%^&*();\\/|<>\"'§{}]+";
		String regex = "[^\\dA-Za-z ]+";

		List<Token> nl = new ArrayList<Token>();
      
        Token tn;
		Iterator<Token> iterator = tokens.iterator();

		while(iterator.hasNext()){
			tn = iterator.next();

			//do not handle smileys
			if(tn.getType() == TokenType.SMILEY)
				continue;
          
			String[] subtoken = tn.getValue().split(regex);
          	
          	try{
				for(String st : subtoken)
					nl.add(new Token(st));
			} catch(WrongTokenFormatException e){
				System.err.println("wrong formatted tweet token");
				continue;
			}
          
          	//save memory and delete old token
			iterator.remove();
		}

		return nl;
	}

	
	/**
	* Remove URLS
	*/
	public static List<Token> removeURLs(List<Token> tokens){
		String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		
		final Pattern p = Pattern.compile(regex);
		Matcher m;
		
		Iterator<Token> iterator = tokens.iterator();

		while(iterator.hasNext()){
			m = p.matcher(iterator.next().getToken());

			if(m.matches())
				iterator.remove();
		}

		return tokens;
	}
}