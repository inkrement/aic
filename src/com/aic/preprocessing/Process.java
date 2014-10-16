package com.aic.preprocessor

public class Process {


	/**
	 * do all preprocessing steps at once
	 *
	 * @param String input content of a tweet
	 */
	public static String do(String input){

	}

	/**
	 * filter unuseful words
	 */
	public static List<Token> filter(List<Token> tokens){
		return removeArtiles(input);
	}

	/**
	 * transform word
	 * example: verb to noun
	 *
	 * @param String input multiple words
	 */
	public static Token transform(Token input){

	}







	/**
	 *
	 */
	private static Token verbToNoun(Token verb){

	}





	/**
	 * remove articles
	 */
	private static List<Token> removeArtiles(List<Token> tokens){}

}