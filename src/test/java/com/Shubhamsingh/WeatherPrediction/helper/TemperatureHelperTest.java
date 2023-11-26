package com.Shubhamsingh.WeatherPrediction.helper;

import com.Shubhamsingh.WeatherPrediction.model.CurrentWeather;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureHelperTest {

    @Test
    void testGetTemperatureExtremes() {
        JsonArray weatherList = createSampleWeatherList();
        CurrentWeather currentWeather = new CurrentWeather();
        String date = "2023-11-22";

        TemperatureHelper.getTemperatureExtremes(date, weatherList, currentWeather);

        assertEquals(17.0, currentWeather.getTemperatureLowForTheDay(), 0.01, "Low temperature should match");
        assertEquals(28.5, currentWeather.getTemperatureHighForTheDay(), 0.01, "High temperature should match");
    }

    @Test
    void testGetTemperatureExtremesWithEmptyWeatherList() {
        JsonArray weatherList = new JsonArray();
        CurrentWeather currentWeather = new CurrentWeather();
        String date = "2023-11-22";

        TemperatureHelper.getTemperatureExtremes(date, weatherList, currentWeather);

        assertEquals(0.0, currentWeather.getTemperatureLowForTheDay(), 0.01, "Low temperature should be 0.0");
        assertEquals(0.0, currentWeather.getTemperatureHighForTheDay(), 0.01, "High temperature should be 0.0");
    }

    @Test
    void testGetTemperatureExtremesWithInvalidDate() {
        JsonArray weatherList = createSampleWeatherList();
        CurrentWeather currentWeather = new CurrentWeather();
        String date = "2023-11-23"; // Date not present in the sample weather list

        TemperatureHelper.getTemperatureExtremes(date, weatherList, currentWeather);

        assertEquals(0.0, currentWeather.getTemperatureLowForTheDay(), 0.01, "Low temperature should be 0.0");
        assertEquals(0.0, currentWeather.getTemperatureHighForTheDay(), 0.01, "High temperature should be 0.0");
    }

    private JsonArray createSampleWeatherList() {
        JsonArray weatherList = new JsonArray();

        JsonObject weatherData = new JsonObject();
        weatherData.addProperty("dt", 1700643600L); // 2023-11-22
        JsonObject main = new JsonObject();
        main.addProperty("temp_max", 301.65); // Kelvin value for 28.5°C
        main.addProperty("temp_min", 290.15); // Kelvin value for 17.0°C
        weatherData.add("main", main);

        weatherList.add(weatherData);

        return weatherList;
    }
}