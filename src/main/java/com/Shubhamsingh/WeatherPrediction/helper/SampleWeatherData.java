package com.Shubhamsingh.WeatherPrediction.helper;

import com.Shubhamsingh.WeatherPrediction.model.CurrentWeather;
import com.Shubhamsingh.WeatherPrediction.model.WeatherMessagePrediction;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample weather data.
 */
public class SampleWeatherData {

    /**
     * Get a list of sample weather message predictions.
     *
     * @return List of sample weather message predictions.
     */
    public static List<WeatherMessagePrediction> getSampleWeatherMessagePredictions() {
        List<WeatherMessagePrediction> predictions = new ArrayList<>();

        WeatherMessagePrediction prediction1 = new WeatherMessagePrediction();
        prediction1.setPredictionTimestamp("06:00");
        prediction1.setNotificationMessage("Expect a sunny day!");

        WeatherMessagePrediction prediction2 = new WeatherMessagePrediction();
        prediction2.setPredictionTimestamp("12:00");
        prediction2.setNotificationMessage("Cloudy skies ahead.");

        WeatherMessagePrediction prediction3 = new WeatherMessagePrediction();
        prediction3.setPredictionTimestamp("18:00");
        prediction3.setNotificationMessage("Cloudy skies ahead.");

        predictions.add(prediction1);
        predictions.add(prediction2);
        predictions.add(prediction3);

        return predictions;
    }

    /**
     * Get a list of sample current weather data.
     *
     * @return List of sample current weather data.
     */
    public static List<CurrentWeather> getSampleCurrentWeather() {
        CurrentWeather currentWeatherA = new CurrentWeather();
        currentWeatherA.setCompleteDate("2023-11-22");
        currentWeatherA.setTemperatureHighForTheDay(28.5);
        currentWeatherA.setTemperatureLowForTheDay(18.0);
        currentWeatherA.setWindy(false);
        currentWeatherA.setStormy(false);
        currentWeatherA.setRainy(true);
        currentWeatherA.setSunny(false);

        CurrentWeather currentWeatherB = new CurrentWeather();
        currentWeatherB.setCompleteDate("2023-11-23");
        currentWeatherB.setTemperatureHighForTheDay(27.5);
        currentWeatherB.setTemperatureLowForTheDay(17.0);
        currentWeatherB.setWindy(false);
        currentWeatherB.setStormy(true);
        currentWeatherB.setRainy(false);
        currentWeatherB.setSunny(false);

        CurrentWeather currentWeatherC = new CurrentWeather();
        currentWeatherC.setCompleteDate("2023-11-24");
        currentWeatherC.setTemperatureHighForTheDay(29.5);
        currentWeatherC.setTemperatureLowForTheDay(19.0);
        currentWeatherC.setWindy(false);
        currentWeatherC.setStormy(false);
        currentWeatherC.setRainy(false);
        currentWeatherC.setSunny(true);

        List<WeatherMessagePrediction> predictions = getSampleWeatherMessagePredictions();
        currentWeatherA.setWeatherMessagePredictions(predictions);
        currentWeatherB.setWeatherMessagePredictions(predictions);
        currentWeatherC.setWeatherMessagePredictions(predictions);

        List<CurrentWeather> sampleDataResponse = new ArrayList<>();
        sampleDataResponse.add(currentWeatherA);
        sampleDataResponse.add(currentWeatherB);
        sampleDataResponse.add(currentWeatherC);

        return sampleDataResponse;
    }
}