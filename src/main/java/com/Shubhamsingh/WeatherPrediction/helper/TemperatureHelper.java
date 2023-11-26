package com.Shubhamsingh.WeatherPrediction.helper;

import com.Shubhamsingh.WeatherPrediction.model.CurrentWeather;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
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
        double globalLowTemperature = Double.POSITIVE_INFINITY;
        double globalHighTemperature = Double.NEGATIVE_INFINITY;

        try {
            for (JsonElement weatherListElement : weatherList) {
                JsonObject weatherData = weatherListElement.getAsJsonObject();
                String currentDateData = DateHelper.formatEpochSecondToDate(weatherData.getAsJsonPrimitive("dt").getAsLong());

                if (currentDateData.equals(date)) {
                    Double tempMax = getTemperature(weatherData.getAsJsonObject("main"), "temp_max");
                    Double tempMin = getTemperature(weatherData.getAsJsonObject("main"), "temp_min");

                    if (tempMax != null && tempMin != null) {
                        double tempHigh = tempMax - KELVIN_TO_CELSIUS_CONVERSION;
                        double tempLow = tempMin - KELVIN_TO_CELSIUS_CONVERSION;

                        globalLowTemperature = Math.min(globalLowTemperature, tempLow);
                        globalHighTemperature = Math.max(globalHighTemperature, tempHigh);
                    } else {
                        LOGGER.warn("Temperature data is null or invalid for date: {}", date);
                    }
                }
            }

            if (globalLowTemperature != Double.POSITIVE_INFINITY && globalHighTemperature != Double.NEGATIVE_INFINITY) {
                currentWeather.setTemperatureLowForTheDay(roundTemperature(globalLowTemperature));
                currentWeather.setTemperatureHighForTheDay(roundTemperature(globalHighTemperature));
            } else {
                LOGGER.warn("No valid temperature data found for date: {}", date);
            }
        } catch (Exception e) {
            LOGGER.error("Error while getting temperature extremes", e);
        }
    }

    private static Double getTemperature(JsonObject jsonObject, String key) {
        JsonElement jsonElement = jsonObject.get(key);

        if (jsonElement != null && jsonElement.isJsonPrimitive()) {
            JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
            if (jsonPrimitive.isNumber()) {
                return jsonPrimitive.getAsDouble();
            } else {
                LOGGER.warn("Invalid temperature format for key: {}", key);
            }
        } else {
            LOGGER.warn("Temperature data is missing for key: {}", key);
        }

        return null;
    }


    /**
     * Rounds the temperature to two decimal places.
     *
     * @param temperature The temperature to round.
     * @return The rounded temperature.
     */
    private static double roundTemperature(double temperature) {
        if (Double.isInfinite(temperature)) {
            return 0.0;
        } else {
            return Math.round(temperature * 100.0) / 100.0; // Rounds to 2 decimal places
        }
    }

}

