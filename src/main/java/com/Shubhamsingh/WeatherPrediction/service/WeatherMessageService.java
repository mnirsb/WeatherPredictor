package com.Shubhamsingh.WeatherPrediction.service;

import com.Shubhamsingh.WeatherPrediction.model.CurrentWeather;
import com.Shubhamsingh.WeatherPrediction.model.WeatherMessagePrediction;

public interface WeatherMessageService {
    void setWeatherMessage(CurrentWeather currentWeather, WeatherMessagePrediction weatherMessagePrediction);
}