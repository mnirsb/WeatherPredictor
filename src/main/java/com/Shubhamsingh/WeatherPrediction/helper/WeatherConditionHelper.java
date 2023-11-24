package com.Shubhamsingh.WeatherPrediction.helper;

import com.Shubhamsingh.WeatherPrediction.model.CurrentWeather;
import com.Shubhamsingh.WeatherPrediction.model.WeatherMessagePrediction;
import com.Shubhamsingh.WeatherPrediction.service.impl.WeatherNotification;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for updating weather conditions based on received weather data.
 */
@Service
public class WeatherConditionHelper {

    private WeatherNotification weatherNotification;

    @Autowired
    public void setWeatherNotification(WeatherNotification weatherNotification) {
        this.weatherNotification = weatherNotification;
    }

    private static final Logger logger = LogManager.getLogger(WeatherConditionHelper.class);
    private static final double KELVIN_TO_CELSIUS_CONVERSION = 273.15;
    private static final double MPS_TO_MPH_CONVERSION = 2.23694;

    /**
     * Updates the weather conditions for the given date based on the provided weather data.
     *
     * @param currentWeather The current weather object to update.
     * @param weatherList    The weather data received for the date.
     */
    public void updateWeatherConditions(CurrentWeather currentWeather, JsonArray weatherList) {
        try {
            List<WeatherMessagePrediction> currentDateWeatherMessage = new ArrayList<>();
            for (JsonElement weatherListElement : weatherList) {
                JsonObject weatherData = weatherListElement.getAsJsonObject();
                JsonObject mainWeatherData = weatherData.getAsJsonObject("main");
                String extractDtText = TimeHelper.extractTimeFromDateTimeString(weatherData.getAsJsonPrimitive("dt_txt").getAsString());
                Long timestamp = weatherData.getAsJsonPrimitive("dt").getAsLong();
                String currDate = DateHelper.formatEpochSecondToDate(timestamp);


                if (currDate.equals(currentWeather.getCompleteDate())){
                    WeatherMessagePrediction weatherMessagePrediction = new WeatherMessagePrediction();
                    double temperature = mainWeatherData.getAsJsonPrimitive("temp").getAsDouble() - KELVIN_TO_CELSIUS_CONVERSION;
                    currentWeather.setSunny(temperature > 40.0);
                    JsonArray weather = weatherData.getAsJsonArray("weather");
                    String weatherCondition = weather.get(0).getAsJsonObject().getAsJsonPrimitive("main").getAsString();
                    currentWeather.setRainy(weatherCondition.contains("Rain"));
                    currentWeather.setStormy(weatherCondition.contains("Thunderstorm"));

                    double windSpeedMps = weatherData.getAsJsonObject("wind").getAsJsonPrimitive("speed").getAsDouble();
                    double windSpeedMph = windSpeedMps * MPS_TO_MPH_CONVERSION;
                    currentWeather.setWindy(windSpeedMph > 10.0);

                    if (currentWeather.hasAnyCondition()){
                        weatherMessagePrediction.setPredictionTimestamp(extractDtText);
                        weatherNotification.setWeatherMessage(currentWeather, weatherMessagePrediction);
                        currentWeather.resetConditions();
                        currentDateWeatherMessage.add(weatherMessagePrediction);
                    }
                }
            }
            currentWeather.setWeatherMessagePredictions(currentDateWeatherMessage);
        }catch(Exception e){
                // Log the exception using Log4j
                logger.error("An error occurred while updating weather conditions", e);
                // Optionally, rethrow the exception or handle it based on your application's requirements
            }
    }
}
