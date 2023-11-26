package com.Shubhamsingh.WeatherPrediction.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeHelperTest {

    @Test
    void testExtractTimeFromDateTimeString() {
        String dateTimeString = "2023-11-22 12:30:45";

        String extractedTime = TimeHelper.extractTimeFromDateTimeString(dateTimeString);

        assertEquals("12:30:45", extractedTime, "Extracted time should match");
    }

    @Test
    void testExtractTimeFromInvalidDateTimeString() {
        String invalidDateTimeString = "invalidDateTime";

        String extractedTime = TimeHelper.extractTimeFromDateTimeString(invalidDateTimeString);

        assertEquals("00:00", extractedTime, "Default time should be returned for invalid input");
    }

    @Test
    void testExtractTimeFromNullDateTimeString() {
        String extractedTime = TimeHelper.extractTimeFromDateTimeString(null);

        assertEquals("00:00", extractedTime, "Default time should be returned for null input");
    }

    @Test
    void testExtractTimeFromEmptyDateTimeString() {
        String emptyDateTimeString = "";

        String extractedTime = TimeHelper.extractTimeFromDateTimeString(emptyDateTimeString);

        assertEquals("00:00", extractedTime, "Default time should be returned for empty input");
    }
}