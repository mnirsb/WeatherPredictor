package com.Shubhamsingh.WeatherPrediction.helper;

import com.Shubhamsingh.WeatherPrediction.model.CurrentWeather;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Helper class for extracting temperature extremes from weather data.
 */
@Service
public class TemperatureHelper {

    private static final Logger LOGGER = LogManager.getLogger(TemperatureHelper.class);
    private static final double KELVIN_TO_CELSIUS_CONVERSION = 273.15;

    /**
     * Extract temperature extremes for a specific date from weather data.
     *
     * @param date           The date for which temperature extremes are to be extracted.
     * @param weatherList    The list of weather data.
     * @param currentWeather The CurrentWeather object to update with temperature extremes.
     */
    public static void getTemperatureExtremes(String date, JsonArray weatherList, CurrentWeather currentWeather) {
        double globalLowTemperature = Double.MAX_VALUE;
        double globalHighTemperature = Double.MIN_VALUE;

        try {

            for (JsonElement weatherListElement : weatherList) {
                JsonObject weatherData = weatherListElement.getAsJsonObject();
                String currentDateData = DateHelper.formatEpochSecondToDate(weatherData.getAsJsonPrimitive("dt").getAsLong());

                if (currentDateData.equals(date)) {
                    double tempHigh = weatherData.getAsJsonObject("main").getAsJsonPrimitive("temp_max").getAsDouble() - KELVIN_TO_CELSIUS_CONVERSION;
                    double tempLow = weatherData.getAsJsonObject("main").getAsJsonPrimitive("temp_min").getAsDouble() - KELVIN_TO_CELSIUS_CONVERSION;

                    globalLowTemperature = Math.min(globalLowTemperature, tempLow);
                    globalHighTemperature = Math.max(globalHighTemperature, tempHigh);
                }
            }

            currentWeather.setTemperatureLowForTheDay(roundTemperature(globalLowTemperature));
            currentWeather.setTemperatureHighForTheDay(roundTemperature(globalHighTemperature));
        }catch (Exception e) {
            // Log the exception using Log4j
            LOGGER.error("Error while getting temperature extremes", e);
            // You may choose to throw the exception or handle it accordingly
        }
    }

    /**
     * Rounds the temperature to two decimal places.
     *
     * @param temperature The temperature to round.
     * @return The rounded temperature.
     */
    private static double roundTemperature(double temperature) {
        return Math.round(temperature * 100.0) / 100.0; // Rounds to 2 decimal places
    }

}

