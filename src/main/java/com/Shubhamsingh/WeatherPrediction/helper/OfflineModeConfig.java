package com.Shubhamsingh.WeatherPrediction.helper;

import org.springframework.stereotype.Component;

/**
 * Configuration component for managing offline mode.
 */
@Component
public class OfflineModeConfig {

    private boolean offlineModeEnabled;

    /**
     * Check if offline mode is enabled.
     *
     * @return true if offline mode is enabled, false otherwise.
     */
    public boolean isOfflineModeEnabled() {
        return offlineModeEnabled;
    }

    /**
     * Set the offline mode status.
     *
     * @param offlineModeEnabled true to enable offline mode, false to disable.
     */
    public void setOfflineModeEnabled(boolean offlineModeEnabled) {
        this.offlineModeEnabled = offlineModeEnabled;
    }
}
