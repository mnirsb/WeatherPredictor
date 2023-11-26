
Weather Prediction App
Overview
Welcome to the Weather Prediction App, a Java-based masterpiece crafted on the Spring Boot framework, tailored to deliver pinpoint accurate weather data for your chosen city. Dive into the core of this project, where an ensemble of classes collaboratively fetches, parses, and crafts weather predictions from data pulled from a third-party API.

Contents
WeatherController
ModifiedUrlImpl
ParseWeatherDataServiceImpl
WeatherApiDataServiceImpl
WeatherConditionServiceImpl
WeatherMessageServiceImpl
WeatherServiceImpl
DateHelper
OfflineModeConfig
SampleWeatherData
TemperatureHelper
TimeHelper
CurrentWeather
WeatherMessagePrediction
WeatherController
The WeatherController - your gateway to handling HTTP requests with swagger. It flaunts two endpoints:

GET /toggleOfflineMode: A toggle switch for the application's offline mode.
GET /weathers/{cityName}: A treasure trove of weather data for the city of your dreams.
ModifiedUrlImpl
Behold the ModifiedUrlImpl class, the maestro orchestrating a dynamic dance of constructing URLs based on city names. It curates the perfect URL for harvesting weather data, incorporating secret ingredients like API keys and forecast counts.

ParseWeatherDataServiceImpl
Meet the conductor, the ParseWeatherDataServiceImpl class, transforming raw JSON data from the API into a symphony of CurrentWeather objects, each narrating the atmospheric tale for specific dates.

WeatherApiDataServiceImpl
The WeatherApiDataServiceImpl – a virtuoso wielding the OkHttp wand, fetching weather data with grace. It plays judge to responses, checks for errors, and casts spells to handle JSON data. A SpecificAPIError emerges if the city is playing hide-and-seek.

WeatherConditionServiceImpl
Enter the maestro, the WeatherConditionServiceImpl class, composing weather conditions for a specific date based on the celestial revelations from the API. Collaborating with helpers, it paints vivid imagery onto the canvas of the CurrentWeather object.

WeatherMessageServiceImpl
The WeatherMessageServiceImpl - a poet in the digital realm. It composes a lyrical notification message based on the weather's mood—whispers of wind, tantrums of storms, soothing rains, or the brilliance of sunshine.

WeatherServiceImpl
The grand conductor, the WeatherServiceImpl, harmonizes the entire symphony. It orchestrates the dance between ModifiedUrl, WeatherApiDataService, and ParseWeatherDataService to serenade you with weather data. In offline mode, it gracefully presents sample data.

DateHelper
Marvel at the DateHelper – a time-travel guide for date-related operations. It effortlessly transforms epoch seconds into formatted date strings.

OfflineModeConfig
Behold the OfflineModeConfig, a configuration wizard managing the elusive offline mode. It bestows upon you methods to check and set the mystical offline mode status.

SampleWeatherData
Meet the SampleWeatherData, generously offering you a taste of its weather message predictions and current weather conditions.

TemperatureHelper
The TemperatureHelper, an expert sculptor carving temperature extremes from the raw material of weather data.

TimeHelper
The TimeHelper, a time-bending magician extracting the essence of time from date-time strings.

CurrentWeather
Gaze upon the CurrentWeather, the sentinel standing guard over the current weather conditions for a specific date. It embodies methods for initiation, reset, and a keen eye for weather checks.

WeatherMessagePrediction
Finally, the WeatherMessagePrediction – a harbinger of poetic weather prophecies. With timestamped precision, it weaves a notification message that echoes through the digital winds.

Flow Chart
Weather Prediction App Flow Chart

Client Request:

The client beckons, toggling offline mode or summoning weather data for a city.
Weather Controller:

The WeatherController takes center stage, directing the request to the rightful method.
Toggle Offline Mode:

The WeatherController adjusts the sails, updating the offline mode configuration.
Fetch Weather Data:

The WeatherController orchestrates with the WeatherServiceImpl to retrieve weather data.
Modified URL and API Data Fetching:

The WeatherServiceImpl wields the ModifiedUrl to construct the API URL and fetches data using the WeatherApiDataService.
JSON Data Parsing:

The ParseWeatherDataService transforms JSON data into a chorus of CurrentWeather objects.
Weather Conditions and Messaging:

The WeatherConditionServiceImpl paints weather conditions, and the WeatherMessageServiceImpl crafts poetic messages.
Response:

The grand finale – weather data or status cascades back as an HTTP response to the client.
Conclusion
The Weather Prediction App, a symphony of modularity and organization, elegantly dances through the clouds of weather-related functionalities. Powered by the Spring Boot framework, it stands as a robust and scalable creation, perfectly suited for the challenges of real-world weather prediction applications.