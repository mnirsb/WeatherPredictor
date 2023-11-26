package com.Shubhamsingh.WeatherPrediction.service.impl;

import com.Shubhamsingh.WeatherPrediction.model.CurrentWeather;
import com.Shubhamsingh.WeatherPrediction.model.WeatherMessagePrediction;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WeatherConditionServiceImplTest {

    @Mock
    private WeatherMessageServiceImpl weatherMessageServiceImpl;

    @InjectMocks
    private WeatherConditionServiceImpl weatherConditionService;

    private static final double KELVIN_TO_CELSIUS_CONVERSION = 273.15;
    private static final double MPS_TO_MPH_CONVERSION = 2.23694;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateWeatherConditions() {
        // Given
        CurrentWeather currentWeather = new CurrentWeather();
        JsonArray weatherList = new JsonArray();

        JsonObject weatherData1 = createWeatherData(280.0, "Clear", 2.0);
        JsonObject weatherData2 = createWeatherData(290.0, "Rain", 15.0);

        weatherList.add(weatherData1);
        weatherList.add(weatherData2);
        currentWeather.setCompleteDate("2023-11-22");

        // When
        weatherConditionService.updateWeatherConditions(currentWeather, weatherList);

        // Then
        List<WeatherMessagePrediction> weatherMessagePredictions = currentWeather.getWeatherMessagePredictions();
        assertNotNull(weatherMessagePredictions);
        assertEquals(1, weatherMessagePredictions.size());

        WeatherMessagePrediction prediction1 = weatherMessagePredictions.get(0);
        assertEquals("12:00", prediction1.getPredictionTimestamp());


        verify(weatherMessageServiceImpl, times(1)).setWeatherMessage(eq(currentWeather), any(WeatherMessagePrediction.class));
    }

    @Test
    void testUpdateWeatherConditionsEmptyList() {
        // Given
        CurrentWeather currentWeather = new CurrentWeather();
        JsonArray weatherList = new JsonArray();

        // When
        weatherConditionService.updateWeatherConditions(currentWeather, weatherList);

        // Then
        List<WeatherMessagePrediction> weatherMessagePredictions = currentWeather.getWeatherMessagePredictions();
        assertNotNull(weatherMessagePredictions);
        assertTrue(weatherMessagePredictions.isEmpty());

        verify(weatherMessageServiceImpl, never()).setWeatherMessage(eq(currentWeather), any(WeatherMessagePrediction.class));
    }


    private JsonObject createWeatherData(double temperature, String weatherCondition, double windSpeed) {
        JsonObject mainWeatherData = new JsonObject();
        mainWeatherData.addProperty("temp", temperature);

        JsonObject weatherObject = new JsonObject();
        weatherObject.addProperty("main", weatherCondition);

        JsonArray weatherArray = new JsonArray();
        weatherArray.add(weatherObject);

        JsonObject windObject = new JsonObject();
        windObject.addProperty("speed", windSpeed);

        JsonObject weatherData = new JsonObject();
        weatherData.add("main", mainWeatherData);
        weatherData.add("weather", weatherArray);
        weatherData.add("wind", windObject);
        weatherData.addProperty("dt", 1700643600L); //2023-11-22
        weatherData.addProperty("dt_txt", "2023-11-22 12:00:00");

        return weatherData;
    }
}
