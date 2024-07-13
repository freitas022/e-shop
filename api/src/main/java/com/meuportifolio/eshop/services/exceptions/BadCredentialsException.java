package com.meuportifolio.eshop.services.exceptions;

public class BadCredentialsException extends RuntimeException {

    public BadCredentialsException(String msg) {
        super(msg);
    }
}
