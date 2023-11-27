package com.Shubhamsingh.WeatherPrediction.service.impl;

import com.Shubhamsingh.WeatherPrediction.exception.SpecificAPIError;
import com.Shubhamsingh.WeatherPrediction.service.WeatherApiDataService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Implementation of WeatherApiDataService for fetching weather data from a third-party API.
 */
@Service
public class WeatherApiDataServiceImpl implements WeatherApiDataService {

    private static final Logger logger = LogManager.getLogger(WeatherApiDataServiceImpl.class);

    // Cached JSON data
    private String jsonData = null;

    /**
     * Fetches weather data from the specified API URL.
     *
     * @param url The URL of the weather API.
     * @return ResponseEntity containing the JSON data if successful, or an error response if unsuccessful.
     * @throws SpecificAPIError Thrown for specific API-related errors (e.g., city not found).
     */
    @Override
    public ResponseEntity<String> getAllWeatherApiData(final String url) throws SpecificAPIError {
        try {
            // Create an OkHttpClient
            OkHttpClient client = new OkHttpClient();

            // Create an HTTP request
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            // Execute the request and get the response
            Response response = client.newCall(request).execute();

            // Check if the response is successful (status code 200)
            if (response.isSuccessful()) {
                // Extract JSON data from the response
                jsonData = response.body().string();


                return ResponseEntity.ok(jsonData);
            } else {
                logger.error("Error: {}", response.code());

                // Check if the error is due to an incorrect city name
                if (response.code() == 404) {
                    throw new SpecificAPIError("City not found");
                }

                return ResponseEntity.status(response.code()).body(null);
            }
        } catch (IOException e) {
            logger.error("An error occurred while fetching weather data", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

