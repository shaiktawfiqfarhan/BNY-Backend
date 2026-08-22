package com.Backend.exception;

public class PointOfContactNotFoundException extends RuntimeException {

    public PointOfContactNotFoundException(String message) {
        super(message);
    }
}