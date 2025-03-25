package com.myapp.exceptions;

public class StockInsufficientException extends RuntimeException {

    public StockInsufficientException(Long productId) {
        super("Insufficient stock for product with ID: " + productId);
    }
}
