package com.myapp.services.exceptions;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Username or password incorrect.");
    }
}
