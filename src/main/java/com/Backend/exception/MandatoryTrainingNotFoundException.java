package com.Backend.exception;

public class MandatoryTrainingNotFoundException extends RuntimeException {

    public MandatoryTrainingNotFoundException(String message) {
        super(message);
    }
}