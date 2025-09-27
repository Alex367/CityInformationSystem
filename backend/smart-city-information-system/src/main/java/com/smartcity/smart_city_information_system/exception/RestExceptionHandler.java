package com.smartcity.smart_city_information_system.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<List<ErrorResponse>> handleException(NotFoundException exc){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                System.currentTimeMillis()
        );

        List<ErrorResponse> errors = List.of(error);

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<List<ErrorResponse>> handleException(AlreadyExistedEntityException exc){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                exc.getMessage(),
                System.currentTimeMillis()
        );

        List<ErrorResponse> errors = List.of(error);

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<List<ErrorResponse>> handleDatabaseOperation(DatabaseOperationException exc) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            exc.getMessage(),
            System.currentTimeMillis()
        );

        List<ErrorResponse> errors = List.of(error);

        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<List<ErrorResponse>> handleException(UnauthorizedException exc){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                exc.getMessage(),
                System.currentTimeMillis()
        );

        List<ErrorResponse> errors = List.of(error);

        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<List<ErrorResponse>> handleValidationExceptions(MethodArgumentNotValidException exc){
        List<ErrorResponse> response = new ArrayList<>();

        for(FieldError error : exc.getBindingResult().getFieldErrors()){
            response.add(new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    error.getDefaultMessage(),
                    System.currentTimeMillis()));
        }

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<List<ErrorResponse>> handleConstraintViolation(ConstraintViolationException exc) {
        List<ErrorResponse> response = new ArrayList<>();
        for (var violation : exc.getConstraintViolations()) {
            response.add(new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    violation.getMessage(),
                    System.currentTimeMillis()
            ));
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<List<ErrorResponse>> handleException(InvalidInputException exc) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                System.currentTimeMillis()
        );

        List<ErrorResponse> errors = List.of(error);

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<List<ErrorResponse>> handleException(Exception exc){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred",
                System.currentTimeMillis()
        );

        List<ErrorResponse> errors = List.of(error);

        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
