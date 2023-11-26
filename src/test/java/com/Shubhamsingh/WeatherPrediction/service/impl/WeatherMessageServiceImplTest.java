package com.Shubhamsingh.WeatherPrediction.service.impl;

import com.Shubhamsingh.WeatherPrediction.model.CurrentWeather;
import com.Shubhamsingh.WeatherPrediction.model.WeatherMessagePrediction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeatherMessageServiceImplTest {

    private WeatherMessageServiceImpl weatherMessageService;

    @BeforeEach
    void setUp() {
        weatherMessageService = new WeatherMessageServiceImpl();
    }

    @Test
    void setWeatherMessage_AllConditionsTrue() {
        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setWindy(true);
        currentWeather.setStormy(true);
        currentWeather.setRainy(true);
        currentWeather.setSunny(true);

        WeatherMessagePrediction weatherMessagePrediction = new WeatherMessagePrediction();
        weatherMessageService.setWeatherMessage(currentWeather, weatherMessagePrediction);

        assertEquals("It’s too windy, watch out! Don’t step out! A Storm is brewing! Carry an umbrella. Use sunscreen lotion. ",
                weatherMessagePrediction.getNotificationMessage());
    }

    @Test
    void setWeatherMessage_NoConditionTrue() {
        CurrentWeather currentWeather = new CurrentWeather();

        WeatherMessagePrediction weatherMessagePrediction = new WeatherMessagePrediction();
        weatherMessageService.setWeatherMessage(currentWeather, weatherMessagePrediction);

        assertEquals("Weather conditions are normal. ", weatherMessagePrediction.getNotificationMessage());
    }

    @Test
    void setWeatherMessage_EmptyConditions() {
        CurrentWeather currentWeather = new CurrentWeather();

        WeatherMessagePrediction weatherMessagePrediction = new WeatherMessagePrediction();
        weatherMessageService.setWeatherMessage(currentWeather, weatherMessagePrediction);

        assertEquals("Weather conditions are normal. ", weatherMessagePrediction.getNotificationMessage());
    }

}
