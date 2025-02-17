package com.pizza.exception;

public class ToppingAlreadyExistsException extends RuntimeException{
    public ToppingAlreadyExistsException(String message) {
        super(message);
    }
}
