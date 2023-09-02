package com.example.billchange.exceptions;

public class NoAvailableCoinsForChangeException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Not enough coins for the change.";
    }
}
