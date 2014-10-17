package com.aic.preprocessing;

import java.util.List;

public class Process {


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
	* Remove URLS
	*/
	private static List<Token> removeURLs(List<Token> tokens){
		
		//1. Weg regex
		String pattern = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		String tokenValue = new String();
		

		for(Token t : tokens){

		}

		//2. Weg URL-Validator
		/*
		String[] schemes = {"http","https"};
		UrlValidator urlValidator = new UrlValidator(schemes);

		if (urlValidator.isValid("ftp://foo.bar.com/")) {
			System.out.println("url is valid");
		} else {
			System.out.println("url is invalid");
		}
		*/

		return null;
	}
}