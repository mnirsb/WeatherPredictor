package com.Shubhamsingh.WeatherPrediction.model;

/**
 * Represents a weather prediction message with a timestamp and notification message.
 */
public class WeatherMessagePrediction {

    private String predictionTimestamp;
    private String notificationMessage;

    /**
     * Default constructor initializes prediction timestamp and notification message.
     */
    public WeatherMessagePrediction() {
        this.predictionTimestamp = "00:00";
        this.notificationMessage = "Day looks great, Have a great day!";
    }

    /**
     * Gets the prediction timestamp.
     *
     * @return The prediction timestamp.
     */
    public String getPredictionTimestamp() {
        return predictionTimestamp;
    }

    /**
     * Sets the prediction timestamp.
     *
     * @param predictionTimestamp The prediction timestamp to set.
     */
    public void setPredictionTimestamp(String predictionTimestamp) {
        this.predictionTimestamp = predictionTimestamp;
    }

    /**
     * Gets the notification message.
     *
     * @return The notification message.
     */
    public String getNotificationMessage() {
        return notificationMessage;
    }

    /**
     * Sets the notification message.
     *
     * @param notificationMessage The notification message to set.
     */
    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }
}

