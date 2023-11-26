FROM amazoncorretto:17.0.7
USER root
WORKDIR /app
COPY target/WeatherPrediction-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java", "-jar", "WeatherPrediction-0.0.1-SNAPSHOT.jar"]