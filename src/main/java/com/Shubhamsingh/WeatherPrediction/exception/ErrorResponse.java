package com.Shubhamsingh.WeatherPrediction.exception;

import java.time.LocalDateTime;

/**
 * Represents an error response containing details about the error.
 */
public class ErrorResponse {
    private final String message;
    private final int status;
    private final LocalDateTime timestamp;

    /**
     * Constructs an ErrorResponse object.
     *
     * @param message   A description of the error.
     * @param status    HTTP status code indicating the error.
     */
    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Gets the error message.
     *
     * @return The error message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the HTTP status code.
     *
     * @return The HTTP status code.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Gets the timestamp when the error occurred.
     *
     * @return The timestamp.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
