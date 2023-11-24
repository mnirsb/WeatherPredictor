package com.Shubhamsingh.WeatherPrediction.service.impl;

import com.Shubhamsingh.WeatherPrediction.model.CurrentWeather;
import com.Shubhamsingh.WeatherPrediction.model.WeatherMessagePrediction;
import com.Shubhamsingh.WeatherPrediction.service.WeatherCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Implementation of the WeatherCondition interface for providing weather notifications.
 */
@Service
public class WeatherNotification implements WeatherCondition {

    // Logger for logging events
    private static final Logger logger = LogManager.getLogger(WeatherNotification.class);

    /**
     * Sets the weather message for the given current weather conditions.
     *
     * @param currentWeather            The current weather conditions.
     * @param weatherMessagePrediction  The object to store the weather message prediction.
     */
    @Override
    public void setWeatherMessage(CurrentWeather currentWeather, WeatherMessagePrediction weatherMessagePrediction) {
        try {
            // Check weather conditions and set the appropriate notification message
            if (currentWeather.getWindy()) {
                weatherMessagePrediction.setNotificationMessage("It’s too windy, watch out!");
            } else if (currentWeather.getStormy()) {
                weatherMessagePrediction.setNotificationMessage("Don’t step out! A Storm is brewing!");
            } else if (currentWeather.getRainy()) {
                weatherMessagePrediction.setNotificationMessage("Carry an umbrella");
            } else if (currentWeather.getSunny()) {
                weatherMessagePrediction.setNotificationMessage("Use sunscreen lotion");
            } else {
                weatherMessagePrediction.setNotificationMessage("Weather conditions are normal");
            }
        } catch (Exception e) {
            // Log the exception using Log4j
            logger.error("Error while setting weather message for currentWeather: {}", currentWeather, e);

            // You may throw a custom exception or handle it as per your application's needs
            throw new RuntimeException("Error while setting weather message", e);
        }
    }
}
