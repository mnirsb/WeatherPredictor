package com.Shubhamsingh.WeatherPrediction.service;

import com.Shubhamsingh.WeatherPrediction.model.CurrentWeather;
import com.Shubhamsingh.WeatherPrediction.model.WeatherMessagePrediction;

public interface WeatherMessageService {

    String WINDY_MESSAGE = "It’s too windy, watch out! ";
    String STORMY_MESSAGE = "Don’t step out! A Storm is brewing! ";
    String RAINY_MESSAGE = "Carry an umbrella. ";
    String SUNNY_MESSAGE = "Use sunscreen lotion. ";
    String NORMAL_CONDITION_MESSAGE = "Weather conditions are normal. ";
    void setWeatherMessage(CurrentWeather currentWeather, WeatherMessagePrediction weatherMessagePrediction);
}