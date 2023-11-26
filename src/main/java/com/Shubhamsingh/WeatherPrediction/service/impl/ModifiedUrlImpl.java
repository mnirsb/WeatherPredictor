package com.Shubhamsingh.WeatherPrediction.service.impl;

import com.Shubhamsingh.WeatherPrediction.service.ModifiedUrl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ModifiedUrlImpl implements ModifiedUrl {

    private static final Logger logger = LogManager.getLogger(ModifiedUrlImpl.class);

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.cnt:10}") // Default value is 10 if not specified
    private int forecastCount;

    private static final String weatherDataApiTemplate = "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&cnt=%d";

    /**
     * Constructs a modified URL based on the city name.
     *
     * @param cityName The name of the city.
     * @return The modified URL for weather data.
     */
    @Override
    public String getModifiedUrlBasedOnCity(String cityName) {
        try {
            return String.format(weatherDataApiTemplate, cityName, apiKey, forecastCount);
        } catch (Exception e) {
            logger.error("An error occurred while constructing the weather data URL", e);
            return null;
        }
    }
}

