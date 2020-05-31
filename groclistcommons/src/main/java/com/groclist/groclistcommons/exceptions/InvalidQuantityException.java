package com.groclist.groclistcommons.exceptions;

public class InvalidQuantityException extends Exception{

    public InvalidQuantityException(String message) {
        super(message);
    }

    public InvalidQuantityException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
