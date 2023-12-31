package com.Shubhamsingh.WeatherPrediction.service.impl;

import com.Shubhamsingh.WeatherPrediction.helper.DateHelper;
import com.Shubhamsingh.WeatherPrediction.helper.TemperatureHelper;
import com.Shubhamsingh.WeatherPrediction.model.CurrentWeather;
import com.Shubhamsingh.WeatherPrediction.service.ParseWeatherDataService;
import com.Shubhamsingh.WeatherPrediction.service.WeatherConditionService;
import com.google.gson.*;
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
    private WeatherConditionService weatherConditionService;

    @Autowired
    public ParseWeatherDataServiceImpl(WeatherConditionService weatherConditionService) {
        this.weatherConditionService = weatherConditionService;
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
                if (jsonElement != null && jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    JsonArray weatherList = jsonObject.getAsJsonArray("list");

                    // Iterate through weather data elements
                    if (weatherList != null) {
                        for (JsonElement weatherListElement : weatherList) {
                            CurrentWeather currentWeather = new CurrentWeather();
                            JsonObject weatherData = weatherListElement.getAsJsonObject();

                            // Extract timestamp and format it to obtain the date
                            JsonPrimitive timestampJson = weatherData.getAsJsonPrimitive("dt");
                            if (timestampJson != null) {
                                Long timestamp = timestampJson.getAsLong();
                                String date = DateHelper.formatEpochSecondToDate(timestamp);
                                currentWeather.setCompleteDate(date);

                                // Get temperature extremes for the day
                                TemperatureHelper.getTemperatureExtremes(date, weatherList, currentWeather);

                                // Update weather conditions based on the data
                                weatherConditionService.updateWeatherConditions(currentWeather, weatherList);

                                // Check if the date has not been visited and the limit is not reached
                                if (date != null && !visitedDates.contains(date) && visitedDates.size() < WEATHER_DAYS_TOTAL_COUNT) {
                                    filledDates.add(currentWeather);
                                    visitedDates.add(date);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred while parsing weather data", e);
        }

        return filledDates;
    }

}
