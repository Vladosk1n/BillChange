package com.example.billchange.exceptions;

public class RequestInputValidationException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Bill amount is incorrect.";
    }
}
