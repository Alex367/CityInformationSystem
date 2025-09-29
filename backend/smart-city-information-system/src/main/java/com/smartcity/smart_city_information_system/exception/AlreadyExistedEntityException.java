package com.smartcity.smart_city_information_system.exception;

public class AlreadyExistedEntityException extends RuntimeException {
    public AlreadyExistedEntityException(String message) {
        super(message);
    }

    public AlreadyExistedEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistedEntityException(Throwable cause) {
        super(cause);
    }
}
