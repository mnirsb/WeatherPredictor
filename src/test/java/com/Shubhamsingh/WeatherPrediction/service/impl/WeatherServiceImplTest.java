package com.Shubhamsingh.WeatherPrediction.service.impl;

import com.Shubhamsingh.WeatherPrediction.exception.SpecificAPIError;
import com.Shubhamsingh.WeatherPrediction.model.CurrentWeather;
import com.Shubhamsingh.WeatherPrediction.service.ModifiedUrl;
import com.Shubhamsingh.WeatherPrediction.service.ParseWeatherDataService;
import com.Shubhamsingh.WeatherPrediction.service.WeatherApiDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class WeatherServiceImplTest {

    @Mock
    private ModifiedUrl modifiedUrl;

    @Mock
    private WeatherApiDataService weatherApiDataService;

    @Mock
    private ParseWeatherDataService parseWeatherDataService;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getWeatherData_Success() throws SpecificAPIError {
        // Arrange
        String cityName = "London";
        String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?q=London&appid=testApiKey&cnt=25";
        String jsonResponse = "{...}"; // Sample JSON response

        when(modifiedUrl.getModifiedUrlBasedOnCity(cityName)).thenReturn(apiUrl);
        when(weatherApiDataService.getAllWeatherApiData(apiUrl)).thenReturn(new ResponseEntity<>(jsonResponse, HttpStatus.OK));
        when(parseWeatherDataService.parseWeatherData(jsonResponse)).thenReturn(List.of(new CurrentWeather()));

        // Act
        List<CurrentWeather> result = weatherService.getWeatherData(cityName);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(weatherApiDataService, times(1)).getAllWeatherApiData(apiUrl);
        verify(parseWeatherDataService, times(1)).parseWeatherData(jsonResponse);
    }

    @Test
    void getWeatherData_InvalidCity_ThrowsSpecificAPIError() throws SpecificAPIError {
        // Arrange
        String cityName = "InvalidCity";
        String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?q=InvalidCity&appid=testApiKey&cnt=25";

        when(modifiedUrl.getModifiedUrlBasedOnCity(cityName)).thenReturn(apiUrl);

        // Mock a null response, simulating an invalid city
        when(weatherApiDataService.getAllWeatherApiData(apiUrl)).thenReturn(new ResponseEntity<>("", HttpStatus.NOT_FOUND));

    }



    @Test
    void getWeatherData_Exception_ReturnsCachedData() throws SpecificAPIError {
        // Arrange
        String cityName = "London";
        String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?q=London&appid=testApiKey&cnt=25";

        when(modifiedUrl.getModifiedUrlBasedOnCity(cityName)).thenReturn(apiUrl);
        when(weatherApiDataService.getAllWeatherApiData(apiUrl)).thenThrow(new RuntimeException("API not available"));

        // Act
        List<CurrentWeather> result = weatherService.getWeatherData(cityName);

        // Assert
        assertNotNull(result);

        // Verify that the cache is returned when an exception occurs
        verify(weatherApiDataService, times(1)).getAllWeatherApiData(apiUrl);
        verifyNoInteractions(parseWeatherDataService);
    }

    @Test
    void getWeatherData_NullApiResponse_ReturnsCachedData() throws SpecificAPIError {
        // Arrange
        String cityName = "London";
        String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?q=London&appid=testApiKey&cnt=25";

        when(modifiedUrl.getModifiedUrlBasedOnCity(cityName)).thenReturn(apiUrl);
        when(weatherApiDataService.getAllWeatherApiData(apiUrl)).thenReturn(null);

        // Act
        List<CurrentWeather> result = weatherService.getWeatherData(cityName);

        // Assert
        assertNotNull(result);
        // Verify that the cache is returned when the API response is null
        verify(weatherApiDataService, times(1)).getAllWeatherApiData(apiUrl);
        verifyNoInteractions(parseWeatherDataService);
    }

    @Test
    void getWeatherData_ParseException_ReturnsCachedData() throws SpecificAPIError {
        // Arrange
        String cityName = "London";
        String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?q=London&appid=testApiKey&cnt=25";
        String jsonResponse = "{...}"; // Sample JSON response

        when(modifiedUrl.getModifiedUrlBasedOnCity(cityName)).thenReturn(apiUrl);
        when(weatherApiDataService.getAllWeatherApiData(apiUrl)).thenReturn(new ResponseEntity<>(jsonResponse, HttpStatus.OK));
        when(parseWeatherDataService.parseWeatherData(jsonResponse)).thenThrow(new RuntimeException("Parse error"));

        // Act
        List<CurrentWeather> result = weatherService.getWeatherData(cityName);

        // Assert
        assertNotNull(result);

        // Verify that the cache is returned when a parse exception occurs
        verify(weatherApiDataService, times(1)).getAllWeatherApiData(apiUrl);
        verify(parseWeatherDataService, times(1)).parseWeatherData(jsonResponse);
    }


    @Test
    void getSampleWeatherData_CacheEmpty_ReturnsSampleData() throws SpecificAPIError {
        // Arrange
        when(modifiedUrl.getModifiedUrlBasedOnCity(anyString())).thenReturn(null);
        when(weatherApiDataService.getAllWeatherApiData(anyString())).thenReturn(null);

        // Act
        List<CurrentWeather> result = weatherService.getSampleWeatherData();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

}
