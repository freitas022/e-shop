package com.myapp.exceptions;

public record FieldValidationError(
        String field,
        String message
) {
}
