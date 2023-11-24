package com.Shubhamsingh.WeatherPrediction.exception;

/**
 * Custom exception class for specific API errors.
 * Extends the Exception class to represent exceptional conditions that occur within the application.
 */
public class SpecificAPIError extends Exception {

    private final String errorMessage;

    /**
     * Constructs a SpecificAPIError with the specified detail message.
     *
     * @param errorMessage The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public SpecificAPIError(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    /**
     * Retrieves the detail message provided during the creation of the exception.
     *
     * @return The detail message.
     */
    @Override
    public String getMessage() {
        return errorMessage;
    }
}