package com.aic.preprocessor;

public class WrongTokenFormatException extends Exception{
      public WrongTokenFormatException() {}

      //Constructor that accepts a message
      public WrongTokenFormatException(String message){
         super(message);
      }
 }