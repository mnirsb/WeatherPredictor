package com.Shubhamsingh.WeatherPrediction.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OfflineModeConfigTest {

    @Test
    void testIsOfflineModeEnabled() {
        // Create an instance of OfflineModeConfig
        OfflineModeConfig offlineModeConfig = new OfflineModeConfig();

        // By default, offline mode should be disabled
        assertFalse(offlineModeConfig.isOfflineModeEnabled(), "By default, offline mode should be disabled");

        // Enable offline mode
        offlineModeConfig.setOfflineModeEnabled(true);

        // Verify that offline mode is enabled
        assertTrue(offlineModeConfig.isOfflineModeEnabled(), "Offline mode should be enabled");

        // Disable offline mode
        offlineModeConfig.setOfflineModeEnabled(false);

        // Verify that offline mode is disabled
        assertFalse(offlineModeConfig.isOfflineModeEnabled(), "Offline mode should be disabled");
    }

    @Test
    void testSetOfflineModeEnabled() {
        // Create an instance of OfflineModeConfig
        OfflineModeConfig offlineModeConfig = new OfflineModeConfig();

        // Enable offline mode
        offlineModeConfig.setOfflineModeEnabled(true);

        // Verify that offline mode is enabled
        assertTrue(offlineModeConfig.isOfflineModeEnabled(), "Offline mode should be enabled");

        // Disable offline mode
        offlineModeConfig.setOfflineModeEnabled(false);

        // Verify that offline mode is disabled
        assertFalse(offlineModeConfig.isOfflineModeEnabled(), "Offline mode should be disabled");
    }
}