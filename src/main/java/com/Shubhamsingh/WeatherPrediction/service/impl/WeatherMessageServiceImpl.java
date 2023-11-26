package com.Shubhamsingh.WeatherPrediction.service.impl;

import com.Shubhamsingh.WeatherPrediction.model.CurrentWeather;
import com.Shubhamsingh.WeatherPrediction.model.WeatherMessagePrediction;
import com.Shubhamsingh.WeatherPrediction.service.WeatherMessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of the WeatherCondition interface for providing weather notifications.
 */
@Service
public class WeatherMessageServiceImpl implements WeatherMessageService {

    // Logger for logging events
    private static final Logger logger = LogManager.getLogger(WeatherMessageServiceImpl.class);

    /**
     * Sets the weather message for the given current weather conditions.
     *
     * @param currentWeather            The current weather conditions.
     * @param weatherMessagePrediction  The object to store the weather message prediction.
     */
    @Override
    public void setWeatherMessage(CurrentWeather currentWeather, WeatherMessagePrediction weatherMessagePrediction) {
        String weatherPredictionMessage = null;

        try {

            weatherPredictionMessage = Stream.of(
                            currentWeather.getWindy() ? "It’s too windy, watch out! " : "",
                            currentWeather.getStormy() ? "Don’t step out! A Storm is brewing! " : "",
                            currentWeather.getRainy() ? "Carry an umbrella. " : "",
                            currentWeather.getSunny() ? "Use sunscreen lotion. " : "",
                            !currentWeather.hasAnyCondition() ? "Weather conditions are normal. " : "")
                    .collect(Collectors.joining());

            weatherMessagePrediction.setNotificationMessage(weatherPredictionMessage);

        } catch (Exception e) {
            // Log the exception using Log4j
            logger.error("Error while setting weather message for currentWeather: {}", currentWeather, e);

            // You may throw a custom exception or handle it as per your application's needs
            throw new RuntimeException("Error while setting weather message", e);
        }
    }
}
