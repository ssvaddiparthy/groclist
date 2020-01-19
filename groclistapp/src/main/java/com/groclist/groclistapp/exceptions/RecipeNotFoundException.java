package com.groclist.groclistapp.exceptions;

public class RecipeNotFoundException extends Exception {

    public RecipeNotFoundException(String message) {
        super(message);
    }

    public RecipeNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
