package com.Shubhamsingh.WeatherPrediction.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Helper class for extracting time from date-time strings.
 */
public class TimeHelper {

    private static final Logger LOGGER = LogManager.getLogger(TimeHelper.class);

    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final LocalTime DEFAULT_TIME = LocalTime.of(0, 0);; // Choose your default date

    /**
     * Extract time from a date-time string.
     *
     * @param dateTimeString The date-time string to extract time from.
     * @return The extracted time as a string.
     */
    public static String extractTimeFromDateTimeString(String dateTimeString) {
        try {
            if (dateTimeString == null) {
                LOGGER.warn("DateTimeString is null. Returning default time.");
                return DEFAULT_TIME.toString();
            }

            // Parse the string to LocalDateTime
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT));

            // Extract the time portion
            return dateTime.toLocalTime().toString();
        } catch (Exception e) {
            LOGGER.error("Error while formatting Time", e);
            // Return a default time instead of a string
            return DEFAULT_TIME.toString();
        }
    }
}
