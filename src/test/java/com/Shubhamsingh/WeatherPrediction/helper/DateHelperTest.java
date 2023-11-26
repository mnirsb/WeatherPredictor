package com.Shubhamsingh.WeatherPrediction.helper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**/

class DateHelperTest {

    @Test
    void formatEpochSecondToDate_ValidInput() {
        // Valid Test case

        Long validEpochSecond = 1700643600L; /* 2023-11-22 */
        String expectedValue = "2023-11-22";

        String formattedValidDate = DateHelper.formatEpochSecondToDate(validEpochSecond);

        assertEquals(expectedValue, formattedValidDate, "Valid epoch second should be formatted correctly");
    }

    @Test
    void formatEpochSecondToDate_InvalidInput() {
        // Invalid Test case

        Long invalidEpochSecond = null;

        String defaultDate = DateHelper.formatEpochSecondToDate(invalidEpochSecond);

        assertNotNull(defaultDate, "Result should not be null");
        assertEquals("1970-01-01", defaultDate, "Invalid epoch second should return default date");
    }
}
