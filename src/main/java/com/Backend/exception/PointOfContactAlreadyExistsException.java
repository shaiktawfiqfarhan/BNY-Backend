package com.Backend.exception;

public class PointOfContactAlreadyExistsException extends RuntimeException {

    public PointOfContactAlreadyExistsException(String message) {
        super(message);
    }
}