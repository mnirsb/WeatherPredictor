package com.Shubhamsingh.WeatherPrediction.service;

import com.Shubhamsingh.WeatherPrediction.exception.SpecificAPIError;
import com.Shubhamsingh.WeatherPrediction.model.CurrentWeather;

import java.util.List;

public interface WeatherService {
    List<CurrentWeather> getWeatherData(String cityName) throws SpecificAPIError;
    List<CurrentWeather> getSampleWeatherData();
}
