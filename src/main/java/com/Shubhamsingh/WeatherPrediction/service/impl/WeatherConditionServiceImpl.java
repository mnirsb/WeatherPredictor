package com.Shubhamsingh.WeatherPrediction.service.impl;

import com.Shubhamsingh.WeatherPrediction.helper.DateHelper;
import com.Shubhamsingh.WeatherPrediction.helper.TimeHelper;
import com.Shubhamsingh.WeatherPrediction.model.CurrentWeather;
import com.Shubhamsingh.WeatherPrediction.model.WeatherMessagePrediction;
import com.Shubhamsingh.WeatherPrediction.service.WeatherConditionService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Helper class for updating weather conditions based on received weather data.
 */
@Service
public class WeatherConditionServiceImpl implements WeatherConditionService {

    private static final Logger logger = LogManager.getLogger(WeatherConditionServiceImpl.class);
    private final WeatherMessageServiceImpl weatherMessageServiceImpl;

    @Autowired
    public WeatherConditionServiceImpl(WeatherMessageServiceImpl weatherMessageServiceImpl) {
        this.weatherMessageServiceImpl = Objects.requireNonNull(weatherMessageServiceImpl);
    }

    /**
     * Updates the weather conditions for the given date based on the provided weather data.
     *
     * @param currentWeather The current weather object to update.
     * @param weatherList    The weather data received for the date.
     */
    @Override
    public void updateWeatherConditions(CurrentWeather currentWeather, JsonArray weatherList) {
        try {
            if (currentWeather == null) {
                throw new IllegalArgumentException("currentWeather must not be null");
            }
            if (weatherList == null) {
                throw new IllegalArgumentException("weatherList must not be null");
            }

            List<WeatherMessagePrediction> currentDateWeatherMessage = new ArrayList<>();
            for (JsonElement weatherListElement : weatherList) {
                processWeatherData(currentWeather, currentDateWeatherMessage, weatherListElement.getAsJsonObject());
            }
            currentWeather.setWeatherMessagePredictions(currentDateWeatherMessage);
        } catch (JsonParseException e) {
            logger.error("Error parsing JSON data", e);
            // Handle or rethrow the exception based on your application's requirements
        } catch (Exception e) {
            logger.error("An error occurred while updating weather conditions", e);
        }
    }

    private void processWeatherData(CurrentWeather currentWeather, List<WeatherMessagePrediction> currentDateWeatherMessage, JsonObject weatherData) {
        if (currentWeather == null) {
            throw new IllegalArgumentException("currentWeather must not be null");
        }
        if (currentDateWeatherMessage == null) {
            throw new IllegalArgumentException("currentDateWeatherMessage must not be null");
        }
        if (weatherData == null) {
            throw new IllegalArgumentException("weatherData must not be null");
        }

        JsonObject mainWeatherData = weatherData.getAsJsonObject("main");
        String extractDtText = TimeHelper.extractTimeFromDateTimeString(weatherData.getAsJsonPrimitive("dt_txt").getAsString());
        Long timestamp = weatherData.getAsJsonPrimitive("dt").getAsLong();
        String currDate = DateHelper.formatEpochSecondToDate(timestamp);

        if (currDate.equals(currentWeather.getCompleteDate())) {
            WeatherMessagePrediction weatherMessagePrediction = new WeatherMessagePrediction();
            updateCurrentWeatherConditions(currentWeather, mainWeatherData, weatherData);
            if (currentWeather.hasAnyCondition()) {
                setWeatherMessageAndResetConditions(currentWeather, currentDateWeatherMessage, extractDtText, weatherMessagePrediction);
            }
        }
    }

    private void updateCurrentWeatherConditions(CurrentWeather currentWeather, JsonObject mainWeatherData, JsonObject weatherData) {
        if (currentWeather == null) {
            throw new IllegalArgumentException("currentWeather must not be null");
        }
        if (mainWeatherData == null) {
            throw new IllegalArgumentException("mainWeatherData must not be null");
        }
        if (weatherData == null) {
            throw new IllegalArgumentException("weatherData must not be null");
        }

        double temperature = mainWeatherData.getAsJsonPrimitive("temp").getAsDouble() - KELVIN_TO_CELSIUS_CONVERSION;
        currentWeather.setSunny(temperature > 40.0);
        JsonArray weather = weatherData.getAsJsonArray("weather");
        String weatherCondition = weather.get(0).getAsJsonObject().getAsJsonPrimitive("main").getAsString();
        currentWeather.setRainy(weatherCondition.contains("Rain"));
        currentWeather.setStormy(weatherCondition.contains("Thunderstorm"));

        double windSpeedMps = weatherData.getAsJsonObject("wind").getAsJsonPrimitive("speed").getAsDouble();
        double windSpeedMph = windSpeedMps * MPS_TO_MPH_CONVERSION;
        currentWeather.setWindy(windSpeedMph > 10.0);
    }

    private void setWeatherMessageAndResetConditions(CurrentWeather currentWeather, List<WeatherMessagePrediction> currentDateWeatherMessage, String extractDtText, WeatherMessagePrediction weatherMessagePrediction) {
        if (currentWeather == null) {
            throw new IllegalArgumentException("currentWeather must not be null");
        }
        if (currentDateWeatherMessage == null) {
            throw new IllegalArgumentException("currentDateWeatherMessage must not be null");
        }
        if (extractDtText == null) {
            throw new IllegalArgumentException("extractDtText must not be null");
        }
        if (weatherMessagePrediction == null) {
            throw new IllegalArgumentException("weatherMessagePrediction must not be null");
        }

        weatherMessagePrediction.setPredictionTimestamp(extractDtText);
        weatherMessageServiceImpl.setWeatherMessage(currentWeather, weatherMessagePrediction);
        currentWeather.resetConditions();
        currentDateWeatherMessage.add(weatherMessagePrediction);
    }

}
