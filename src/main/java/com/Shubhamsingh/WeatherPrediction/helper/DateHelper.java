package com.Shubhamsingh.WeatherPrediction.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Helper class for date-related operations.
 */
public class DateHelper {

    private static final Logger logger = LogManager.getLogger(DateHelper.class);
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final LocalDate DEFAULT_DATE = LocalDate.of(1970, 1, 1);

    /**
     * Formats epoch second to a date string using the specified date format.
     *
     * @param timeStamp The epoch second to be formatted.
     * @return A formatted date string or the default date if an error occurs.
     */
    public static String formatEpochSecondToDate(Long timeStamp) {
        try {
            Instant instant = Instant.ofEpochSecond(timeStamp);
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            return localDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        } catch (Exception e) {
            // Log the exception using a logging framework (e.g., Log4j)
            logger.error("Error while formatting date", e);
            // Return a default date instead of a string
            return DEFAULT_DATE.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        }
    }
}
