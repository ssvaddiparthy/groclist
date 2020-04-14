package com.groclist.groclistapp.exceptions;

public class MissingUserException extends Exception {

    public MissingUserException(String message) {
        super(message);
    }

    public MissingUserException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
