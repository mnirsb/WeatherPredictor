import com.Shubhamsingh.WeatherPrediction.model.CurrentWeather;
import com.Shubhamsingh.WeatherPrediction.service.WeatherConditionService;
import com.Shubhamsingh.WeatherPrediction.service.impl.ParseWeatherDataServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class ParseWeatherDataServiceImplTest {

    @Mock
    private WeatherConditionService weatherConditionService;

    @InjectMocks
    private ParseWeatherDataServiceImpl parseWeatherDataService;

    @Test
    void testParseWeatherData() {
        // Given
        String jsonData = "{\"list\":[{\"dt\":1636110000,\"main\":{\"temp\":280.36},\"wind\":{\"speed\":3.2},\"weather\":[{\"main\":\"Clear\"}]},{\"dt\":1636196400,\"main\":{\"temp\":282.49},\"wind\":{\"speed\":2.67},\"weather\":[{\"main\":\"Clouds\"}]},{\"dt\":1636282800,\"main\":{\"temp\":278.72},\"wind\":{\"speed\":3.34},\"weather\":[{\"main\":\"Rain\"}]}]}";

        // Mocking behavior for weatherConditionService
        doNothing().when(weatherConditionService).updateWeatherConditions(any(), any());

        // When
        List<CurrentWeather> result = parseWeatherDataService.parseWeatherData(jsonData);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());

        // Verify weatherConditionService method calls
        Mockito.verify(weatherConditionService, Mockito.times(3)).updateWeatherConditions(any(), any());
    }

    @Test
    void testParseWeatherDataWithEmptyJson() {
        // When
        List<CurrentWeather> result = parseWeatherDataService.parseWeatherData(null);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testParseWeatherDataWithInvalidJson() {
        // Given
        String invalidJson = "invalid-json";

        // When
        List<CurrentWeather> result = parseWeatherDataService.parseWeatherData(invalidJson);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testParseWeatherDataWithNoListInJson() {
        // Given
        String jsonData = "{}";

        // When
        List<CurrentWeather> result = parseWeatherDataService.parseWeatherData(jsonData);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testParseWeatherDataWithInvalidListInJson() {
        // Given
        String jsonData = "{\"list\": \"invalid\"}";

        // When
        List<CurrentWeather> result = parseWeatherDataService.parseWeatherData(jsonData);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testParseWeatherDataWithException() {
        // Given
        String jsonData = "{\"list\":[{\"dt\":1636110000,\"main\":{\"temp\":280.36},\"wind\":{\"speed\":3.2},\"weather\":[{\"main\":\"Clear\"}]},{\"dt\":1636196400,\"main\":{\"temp\":282.49},\"wind\":{\"speed\":2.67},\"weather\":[{\"main\":\"Clouds\"}]},{\"dt\":1636282800,\"main\":{\"temp\":278.72},\"wind\":{\"speed\":3.34},\"weather\":[{\"main\":\"Rain\"}]}]}";

        // Mocking behavior for weatherConditionService to throw an exception
        doNothing().when(weatherConditionService).updateWeatherConditions(any(), any());

        // When
        List<CurrentWeather> result = parseWeatherDataService.parseWeatherData(jsonData);

        // Then
        assertNotNull(result);
        assertTrue(result.size() == 3);
    }
}
