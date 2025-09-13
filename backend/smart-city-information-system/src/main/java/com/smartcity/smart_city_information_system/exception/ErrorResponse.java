package com.smartcity.smart_city_information_system.exception;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ErrorResponse {

    private int status;
    private String message;
    private String timeStamp;

    public ErrorResponse() {
    }

    public ErrorResponse(int status, String message, long timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = formatTimestamp(timeStamp);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String formatTimestamp(long timeStamp){
        Instant instant = Instant.ofEpochMilli(timeStamp);
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss z")
                .withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }
}
