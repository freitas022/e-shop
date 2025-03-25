package com.myapp.exceptions;

public class CartEmptyException extends RuntimeException {

    public CartEmptyException() {
        super("Cart can not be empty.");
    }
}
