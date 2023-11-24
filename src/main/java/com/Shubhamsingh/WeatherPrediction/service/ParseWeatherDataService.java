package com.Shubhamsingh.WeatherPrediction.service;

import com.Shubhamsingh.WeatherPrediction.model.CurrentWeather;

import java.util.List;

public interface ParseWeatherDataService {

    List<CurrentWeather> parseWeatherData(String jsonData);

}
