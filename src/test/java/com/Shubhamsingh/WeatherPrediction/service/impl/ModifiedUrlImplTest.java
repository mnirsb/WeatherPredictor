package com.Shubhamsingh.WeatherPrediction.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.Shubhamsingh.WeatherPrediction.service.ModifiedUrl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = ModifiedUrlImpl.class)
public class ModifiedUrlImplTest {

    @Autowired
    private ModifiedUrl modifiedUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.cnt:10}")
    private int forecastCount;

    @Test
    void testGetModifiedUrlBasedOnCity() {
        // Given
        String cityName = "London";
        String expectedUrl = String.format("https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&cnt=%d", cityName, apiKey, forecastCount);

        // When
        String actualUrl = modifiedUrl.getModifiedUrlBasedOnCity(cityName);

        // Then
        assertEquals(expectedUrl, actualUrl);
    }
}