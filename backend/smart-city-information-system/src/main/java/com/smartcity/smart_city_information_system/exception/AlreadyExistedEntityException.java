package com.smartcity.smart_city_information_system.exception;

public class AlreadyExitedEntityException extends RuntimeException {
    public AlreadyExitedEntityException(String message) {
        super(message);
    }

    public AlreadyExitedEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExitedEntityException(Throwable cause) {
        super(cause);
    }
}
