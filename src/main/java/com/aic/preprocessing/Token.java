package com.aic.preprocessing;

public class Token{
	TokenType type;
	String token;
	String value;

	public Token(String word) throws WrongTokenFormatException{
		if(word == null)
			throw new WrongTokenFormatException("words should not be null");

		if(word.length() < 1)
			throw new WrongTokenFormatException("words should have at least one character");

		if(word.contains(" "))
			throw new WrongTokenFormatException("words should not contain space");

		//TODO: Satzzeichen
		switch(word.charAt(0)){
			case '#':
				type = TokenType.TAG;
				value = word.substring(1);
				break;
			case '@':
				type = TokenType.MENTIONING;
				value = word.substring(1);
				break;
			default:
				type = TokenType.WORD;
				value = word;
		}

		token = word;
	}


	public String getToken(){
		return token;
	}

	public String getValue(){
		return value;
	}

	public void setValue(String value){
		this.value = value;
	}
}