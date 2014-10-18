package com.aic.preprocessing;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TweetProcess {


	private String input = new String();

	/**
	 * do all preprocessing steps at once
	 *
	 * @param String input content of a tweet
	 */
	public static String preprocess(String input){
		return null;
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
		String[] tokens = tweet.split(" ");
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
	 */
	private static List<Token> removeArticles(List<Token> tokens){
		return null;
	}

	/**
	* Remove multiple character-occurences
	* example: heyyyyyy -> hey
	*/
	private static List<Token> removeChars(List<Token> tokens){
		
		return null;
	}

	/**
	* Replace special Characters with whitespaces
	* TODO: List<Token> als Returnwert.
	* TODO: - wird nicht erkannt
	* TODO: alle werte Testen
	*/
	public static String replaceSpecialChars(List<Token> tokens){

		//search for: . + - , ! $ % ^ & * ( ) ; \ / | < > " '
		String regex ="[.+-,!$%^&*();\\/|<>\"\']";
		String testvalue = null;

		Iterator<Token> iterator = tokens.iterator();

		while(iterator.hasNext()){
			Token tn = iterator.next();
				
			testvalue = tn.getValue();

			testvalue = testvalue.replaceAll(regex, " ");
		}

		return testvalue;		

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