package com.groclist.groclistapp.exceptions;

public class BadUserDetailException extends Exception {

    public BadUserDetailException(String message) {
        super(message);
    }

    public BadUserDetailException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
