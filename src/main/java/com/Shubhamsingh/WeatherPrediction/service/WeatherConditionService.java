package com.Shubhamsingh.WeatherPrediction.service;

import com.Shubhamsingh.WeatherPrediction.model.CurrentWeather;
import com.google.gson.JsonArray;

public interface WeatherConditionService {
    double KELVIN_TO_CELSIUS_CONVERSION = 273.15;
    double MPS_TO_MPH_CONVERSION = 2.23694;
    void updateWeatherConditions(CurrentWeather currentWeather, JsonArray weatherList);
}
