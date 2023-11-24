package com.Shubhamsingh.WeatherPrediction.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler to handle various types of exceptions in the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles generic exceptions that may occur during request processing.
     *
     * @param e The exception.
     * @return ResponseEntity containing an error response.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        LOGGER.error("An error occurred during request processing", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.value())
        );
    }

    /**
     * Handles specific API errors, such as invalid city names.
     *
     * @param e The specific API error.
     * @return ResponseEntity containing an error response.
     */
    @ExceptionHandler(SpecificAPIError.class)
    public ResponseEntity<ErrorResponse> handleSpecificAPIError(SpecificAPIError e) {
        LOGGER.error("Invalid city name provided: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse("Invalid city name provided: " + e.getMessage(), HttpStatus.BAD_REQUEST.value())
        );
    }
}

