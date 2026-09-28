package com.Backend.exception;

public class InvalidTimeSheetException extends RuntimeException {

    public InvalidTimeSheetException(
            String message) {

        super(message);
    }
}