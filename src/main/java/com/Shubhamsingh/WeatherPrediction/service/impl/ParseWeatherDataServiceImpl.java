package com.Shubhamsingh.WeatherPrediction.service.impl;

import com.Shubhamsingh.WeatherPrediction.helper.DateHelper;
import com.Shubhamsingh.WeatherPrediction.helper.TemperatureHelper;
import com.Shubhamsingh.WeatherPrediction.helper.WeatherConditionHelper;
import com.Shubhamsingh.WeatherPrediction.model.CurrentWeather;
import com.Shubhamsingh.WeatherPrediction.service.ParseWeatherDataService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service implementation for parsing weather data and extracting relevant information.
 */
@Service
public class ParseWeatherDataServiceImpl implements ParseWeatherDataService {

    // Constants
    private static final int WEATHER_DAYS_TOTAL_COUNT = 3;
    private static final Logger logger = LogManager.getLogger(ParseWeatherDataServiceImpl.class);

    // Dependencies
    private final WeatherConditionHelper weatherConditionHelper;

    /**
     * Constructor to inject dependencies.
     *
     * @param weatherConditionHelper Helper class for updating weather conditions.
     */
    @Autowired
    public ParseWeatherDataServiceImpl(WeatherConditionHelper weatherConditionHelper) {
        this.weatherConditionHelper = weatherConditionHelper;
    }

    /**
     * Parses the provided JSON weather data and returns a list of CurrentWeather objects.
     *
     * @param jsonData JSON representation of weather data.
     * @return List of CurrentWeather objects representing weather conditions for specific dates.
     */
    @Override
    public List<CurrentWeather> parseWeatherData(String jsonData) {
        List<CurrentWeather> filledDates = new ArrayList<>();
        Set<String> visitedDates = new HashSet<>();

        try {
            // Check if JSON data is not empty
            if (jsonData != null && !jsonData.isEmpty()) {
                JsonElement jsonElement = JsonParser.parseString(jsonData);

                // Check if the top-level structure is a JSON object
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    JsonArray weatherList = jsonObject.getAsJsonArray("list");

                    // Iterate through weather data elements
                    for (JsonElement weatherListElement : weatherList) {
                        CurrentWeather currentWeather = new CurrentWeather();
                        JsonObject weatherData = weatherListElement.getAsJsonObject();

                        // Extract timestamp and format it to obtain the date
                        Long timestamp = weatherData.getAsJsonPrimitive("dt").getAsLong();
                        String date = DateHelper.formatEpochSecondToDate(timestamp);
                        currentWeather.setCompleteDate(date);

                        // Get temperature extremes for the day
                        TemperatureHelper.getTemperatureExtremes(date, weatherList, currentWeather);

                        // Update weather conditions based on the data
                        weatherConditionHelper.updateWeatherConditions(currentWeather, weatherList);

                        // Check if the date has not been visited and the limit is not reached
                        if (!visitedDates.contains(date) && visitedDates.size() < WEATHER_DAYS_TOTAL_COUNT) {
                            filledDates.add(currentWeather);
                            visitedDates.add(date);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Log the exception using Log4j
            logger.error("An error occurred while parsing weather data", e);
        }

        return filledDates;
    }
}
