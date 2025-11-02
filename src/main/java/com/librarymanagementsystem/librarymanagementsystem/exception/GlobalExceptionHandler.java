package com.librarymanagementsystem.librarymanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * Global exception handler for the application.
 * This class handles exceptions thrown by controllers and provides a centralized
 * mechanism for returning appropriate error responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link ResourceNotFoundException} and returns a NOT_FOUND response.
     *
     * @param ex      The exception thrown when a resource is not found.
     * @param request The current web request.
     * @return A ResponseEntity containing error details and a NOT_FOUND status.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles all other exceptions and returns an INTERNAL_SERVER_ERROR response.
     *
     * @param ex      The exception thrown.
     * @param request The current web request.
     * @return A ResponseEntity containing error details and an INTERNAL_SERVER_ERROR status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

/**
 * A class to represent the details of an error response.
 */
class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;

    /**
     * Constructs a new ErrorDetails object.
     *
     * @param timestamp The timestamp of the error.
     * @param message   A descriptive error message.
     * @param details   Additional details about the error.
     */
    public ErrorDetails(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    // Getters

    /**
     * Gets the timestamp of the error.
     * @return The error timestamp.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the error message.
     * @return The error message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the error details.
     * @return The error details.
     */
    public String getDetails() {
        return details;
    }
}
