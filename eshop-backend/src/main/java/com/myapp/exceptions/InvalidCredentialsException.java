package com.myapp.exceptions;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Invalid credentials provided. Try again.");
    }

    public InvalidCredentialsException(String msg) {
        super(msg);
    }
}
