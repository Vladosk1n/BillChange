package com.example.billchange.controller;

import com.example.billchange.exceptions.ErrorMessage;
import com.example.billchange.exceptions.NoAvailableCoinsForChangeException;
import com.example.billchange.exceptions.RequestInputValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(RequestInputValidationException.class)
    public ResponseEntity<ErrorMessage> resourceInvalidException(RequestInputValidationException ex) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoAvailableCoinsForChangeException.class)
    public ResponseEntity<ErrorMessage> resourceNotAvailableException(NoAvailableCoinsForChangeException ex) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }
}
