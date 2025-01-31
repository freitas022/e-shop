package com.myapp.services.exceptions;

public class JWTGenerationException extends RuntimeException {

    public JWTGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}