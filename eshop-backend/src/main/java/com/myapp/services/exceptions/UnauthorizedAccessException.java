package com.myapp.services.exceptions;

public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
