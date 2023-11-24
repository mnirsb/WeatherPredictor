package com.Shubhamsingh.WeatherPrediction.model;

import java.util.List;

/**
 * Represents the current weather conditions for a specific date.
 */
public class CurrentWeather {

    private String completeDate;
    private double temperatureHighForTheDay;
    private double temperatureLowForTheDay;
    private List<WeatherMessagePrediction> weatherMessagePredictions;

    private Boolean windyCondition;

    private Boolean stormyCondition;
    private Boolean rainyCondition;
    private Boolean sunnyCondition;

    public List<WeatherMessagePrediction> getWeatherMessagePredictions() {
        return weatherMessagePredictions;
    }

    public void setWeatherMessagePredictions(List<WeatherMessagePrediction> weatherMessagePredictions) {
        this.weatherMessagePredictions = weatherMessagePredictions;
    }

    /**
     * Default constructor initializes weather conditions to false.
     */
    public CurrentWeather(){
        this.windyCondition = false;
        this.stormyCondition = false;
        this.rainyCondition = false;
        this.sunnyCondition = false;
    }


    /**
     * Resets all weather conditions to false.
     */
    public void resetConditions() {
        this.windyCondition = false;
        this.stormyCondition = false;
        this.rainyCondition = false;
        this.sunnyCondition = false;
    }

    /**
     * Checks if any weather condition is true.
     *
     * @return True if any weather condition is true, false otherwise.
     */
    public Boolean hasAnyCondition() {
        return windyCondition || stormyCondition || rainyCondition || sunnyCondition;
    }

    /**
     * Parameterized constructor for initializing with a list of weather predictions.
     *
     * @param weatherMessagePredictions List of weather predictions.
     */

    public CurrentWeather(List<WeatherMessagePrediction> weatherMessagePredictions) {
        this.weatherMessagePredictions = weatherMessagePredictions;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public double getTemperatureHighForTheDay() {
        return temperatureHighForTheDay;
    }

    public void setTemperatureHighForTheDay(double temperatureHighForTheDay) {
        this.temperatureHighForTheDay = temperatureHighForTheDay;
    }

    public double getTemperatureLowForTheDay() {
        return temperatureLowForTheDay;
    }

    public void setTemperatureLowForTheDay(double temperatureLowForTheDay) {
        this.temperatureLowForTheDay = temperatureLowForTheDay;
    }

    public Boolean getWindy() {
        return windyCondition;
    }

    public void setWindy(Boolean windy) {
        windyCondition = windy;
    }

    public Boolean getStormy() {
        return stormyCondition;
    }

    public void setStormy(Boolean stormy) {
        stormyCondition = stormy;
    }

    public Boolean getRainy() {
        return rainyCondition;
    }

    public void setRainy(Boolean rainy) {
        rainyCondition = rainy;
    }

    public Boolean getSunny() {
        return sunnyCondition;
    }

    public void setSunny(Boolean sunny) {
        sunnyCondition = sunny;
    }

}
