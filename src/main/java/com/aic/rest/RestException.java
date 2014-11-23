package com.aic.rest;

public class RestException extends Exception {

    public RestException(String message) {
        super(message);
    }

    public RestException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public RestException(Throwable throwable) {
        super(throwable);
    }
}
