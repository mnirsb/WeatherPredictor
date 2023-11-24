package com.Shubhamsingh.WeatherPrediction.service;

import com.Shubhamsingh.WeatherPrediction.exception.SpecificAPIError;
import org.springframework.http.ResponseEntity;

public interface WeatherApiDataService {

    ResponseEntity<String> getAllWeatherApiData(final String url) throws SpecificAPIError;

}
