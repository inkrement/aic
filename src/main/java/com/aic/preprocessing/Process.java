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
		return removeArtiles(tokens);
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
	private static List<Token> removeArtiles(List<Token> tokens){
		return null;
	}

}