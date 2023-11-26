package com.Shubhamsingh.WeatherPrediction.service.impl;

import com.Shubhamsingh.WeatherPrediction.exception.SpecificAPIError;
import com.Shubhamsingh.WeatherPrediction.service.WeatherApiDataService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WeatherApiDataServiceImplTest {

    private WeatherApiDataService weatherApiDataService;
    private MockWebServer mockWebServer;

    @BeforeEach
    void setUp() {
        weatherApiDataService = new WeatherApiDataServiceImpl();
        mockWebServer = new MockWebServer();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void testGetAllWeatherApiData_SuccessfulResponse() throws SpecificAPIError {
        // Arrange
        String jsonResponse = "{\"key\":\"value\"}";
        mockWebServer.enqueue(new MockResponse().setBody(jsonResponse));
        String apiUrl = mockWebServer.url("/").toString();

        // Act
        ResponseEntity<String> response = weatherApiDataService.getAllWeatherApiData(apiUrl);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jsonResponse, response.getBody());
    }

    @Test
    void testGetAllWeatherApiData_CityNotFound() {
        // Arrange
        mockWebServer.enqueue(new MockResponse().setResponseCode(404));
        String apiUrl = mockWebServer.url("/").toString();

        // Act and Assert
        SpecificAPIError specificAPIError = assertThrows(SpecificAPIError.class,
                () -> weatherApiDataService.getAllWeatherApiData(apiUrl));
        assertEquals("City not found", specificAPIError.getMessage());
    }

    @Test
    void testGetAllWeatherApiData_InternalServerError() throws SpecificAPIError {
        // Arrange
        mockWebServer.enqueue(new MockResponse().setResponseCode(500));
        String apiUrl = mockWebServer.url("/").toString();

        // Act
        ResponseEntity<String> response = weatherApiDataService.getAllWeatherApiData(apiUrl);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetAllWeatherApiData_IOException() throws IOException, SpecificAPIError {
        // Arrange
        String apiUrl = "http://invalid-url"; // Intentionally using an invalid URL to cause an IOException

        // Act
        ResponseEntity<String> response = weatherApiDataService.getAllWeatherApiData(apiUrl);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}
