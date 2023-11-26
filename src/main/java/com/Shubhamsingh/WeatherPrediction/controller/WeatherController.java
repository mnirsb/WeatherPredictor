package com.Shubhamsingh.WeatherPrediction.controller;
import com.Shubhamsingh.WeatherPrediction.exception.SpecificAPIError;
import com.Shubhamsingh.WeatherPrediction.helper.OfflineModeConfig;
import com.Shubhamsingh.WeatherPrediction.model.CurrentWeather;
import com.Shubhamsingh.WeatherPrediction.service.WeatherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherController {

    private static final Logger logger = LogManager.getLogger(WeatherController.class);

    private final WeatherService weatherService;
    private final OfflineModeConfig offlineModeConfig;

    public WeatherController(WeatherService weatherService, OfflineModeConfig offlineModeConfig) {
        this.weatherService = weatherService;
        this.offlineModeConfig = offlineModeConfig;
    }

    /**
     * Toggles the offline mode.
     *
     * @return ResponseEntity with the status of the offline mode.
     */

    @GetMapping("/toggleOfflineMode")
    public ResponseEntity<String> toggleOfflineMode() {
        offlineModeConfig.setOfflineModeEnabled(!offlineModeConfig.isOfflineModeEnabled());
        String status = offlineModeConfig.isOfflineModeEnabled() ? "Offline mode enabled" : "Offline mode disabled";
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    /**
     * Gets weather data for a given city.
     *
     * @param cityName The name of the city for which weather data is requested.
     * @return ResponseEntity with the weather data.
     */

    @GetMapping("/weathers/{cityName}")
    private ResponseEntity<List<CurrentWeather>> getWeatherData(@PathVariable String cityName) {
        try {
            if (offlineModeConfig.isOfflineModeEnabled()) {
                return ResponseEntity.ok(weatherService.getSampleWeatherData());
            }

            return ResponseEntity.ok(weatherService.getWeatherData(cityName));
        } catch (SpecificAPIError e) {
            logger.error("Error while fetching weather data for city: {}", cityName, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(weatherService.getSampleWeatherData());
        } catch (Exception e) {
            logger.error("Error while processing weather data for city: {}", cityName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(weatherService.getSampleWeatherData());
        }
    }
}
