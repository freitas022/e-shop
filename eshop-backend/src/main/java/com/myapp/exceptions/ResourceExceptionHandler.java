package com.myapp.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

	private static final Instant now = Instant.now();

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFoundHandler(ResourceNotFoundException e, HttpServletRequest request) {
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(now, status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> databaseHandler(DatabaseException e, HttpServletRequest request) {
		String error = "Database error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(now, status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(UnauthorizedAccessException.class)
	public ResponseEntity<StandardError> unauthorizedAccessHandler(UnauthorizedAccessException e, HttpServletRequest request) {
		String error = "Unauthorized";
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		StandardError err = new StandardError(now, status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(JWTGenerationException.class)
	public ResponseEntity<StandardError> JWTGenerationHandler(JWTGenerationException e, HttpServletRequest request) {
		String error = "Token is null or empty.";
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		StandardError err = new StandardError(now, status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(CartEmptyException.class)
	public ResponseEntity<StandardError> cartEmptyHandler(CartEmptyException e, HttpServletRequest request) {
		String error = "The shopping cart cannot be empty.";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(now, status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<StandardError> invalidCredentialsExceptionHandler(InvalidCredentialsException e, HttpServletRequest request) {
		String error = "Failure to authentication.";
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		StandardError err = new StandardError(now, status.value(), error,e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> validationExceptionHandler(MethodArgumentNotValidException e, HttpServletRequest request) {
		var error = "Validation Error";
		var errors = e.getBindingResult().getFieldErrors()
				.stream()
				.map(fieldError -> new FieldValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
				.toList();

		var err = new ValidationErrorResponse(now, HttpStatus.BAD_REQUEST.value(), error, request.getRequestURI(), errors);
		return ResponseEntity.badRequest().body(err);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<StandardError> anyExceptionHandler(RuntimeException e, HttpServletRequest request) {
		String error = "Something went wrong";
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		StandardError err = new StandardError(now, status.value(), error,e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}
