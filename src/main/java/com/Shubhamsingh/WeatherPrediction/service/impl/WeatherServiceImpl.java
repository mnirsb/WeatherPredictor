package com.Shubhamsingh.WeatherPrediction.service.impl;

import com.Shubhamsingh.WeatherPrediction.exception.SpecificAPIError;
import com.Shubhamsingh.WeatherPrediction.helper.SampleWeatherData;
import com.Shubhamsingh.WeatherPrediction.model.CurrentWeather;
import com.Shubhamsingh.WeatherPrediction.service.ModifiedUrl;
import com.Shubhamsingh.WeatherPrediction.service.ParseWeatherDataService;
import com.Shubhamsingh.WeatherPrediction.service.WeatherApiDataService;
import com.Shubhamsingh.WeatherPrediction.service.WeatherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class WeatherServiceImpl implements WeatherService {

    private static final Logger logger = LogManager.getLogger(WeatherServiceImpl.class);

    private final ModifiedUrl modifiedUrl;
    private final WeatherApiDataService weatherApiDataService;
    private final ParseWeatherDataService parseWeatherDataService;
    private List<CurrentWeather> weatherDataCache = new CopyOnWriteArrayList<>();

    @Autowired
    public WeatherServiceImpl(
            ModifiedUrl modifiedUrl,
            WeatherApiDataService weatherApiDataService,
            ParseWeatherDataService parseWeatherDataService) {
        this.modifiedUrl = modifiedUrl;
        this.weatherApiDataService = weatherApiDataService;
        this.parseWeatherDataService = parseWeatherDataService;
        this.weatherDataCache.addAll(SampleWeatherData.getSampleCurrentWeather());
    }

    /**
     * Get weather data for a specific city.
     *
     * @param cityName The name of the city for which weather data is requested.
     * @return List of CurrentWeather objects representing the weather data.
     * @throws SpecificAPIError If there is an error specific to the external API.
     */
    @Override
    public List<CurrentWeather> getWeatherData(String cityName) throws SpecificAPIError {
        try {
            // Construct the modified URL based on the city name
            String weatherDataApi = modifiedUrl.getModifiedUrlBasedOnCity(cityName);

            // Make a request to the external weather API
            ResponseEntity<String> responseEntity = weatherApiDataService.getAllWeatherApiData(weatherDataApi);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                // Parse the weather data from the API response
                String jsonWeatherData = responseEntity.getBody();
                List<CurrentWeather> fetchedData = parseWeatherDataService.parseWeatherData(jsonWeatherData);

                // Update the cache with the fetched data
                weatherDataCache.clear();
                weatherDataCache.addAll(fetchedData);

                return fetchedData;
            } else {
                logger.error("Error response from weather API for city: {}. Status code: {}", cityName, responseEntity.getStatusCode());
                throw new SpecificAPIError("Invalid city name provided: " + cityName);

            }
        } catch (Exception e) {

            logger.error("An error occurred while fetching weather data for city: {}", cityName, e);
            // Return the data from the cache
            return new ArrayList<>(weatherDataCache);
        }
    }

    /**
     * Get sample weather data when in offline mode.
     *
     * @return List of CurrentWeather objects representing sample weather data.
     */
    @Override
    public List<CurrentWeather> getSampleWeatherData() {
        if (weatherDataCache.isEmpty()) {
            return SampleWeatherData.getSampleCurrentWeather();
        }
        return new ArrayList<>(weatherDataCache);
    }

}

