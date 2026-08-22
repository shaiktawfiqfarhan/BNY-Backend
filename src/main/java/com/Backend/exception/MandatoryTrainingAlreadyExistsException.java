package com.Backend.exception;

public class MandatoryTrainingAlreadyExistsException extends RuntimeException {

    public MandatoryTrainingAlreadyExistsException( String message) {
        super(message);
    }
}