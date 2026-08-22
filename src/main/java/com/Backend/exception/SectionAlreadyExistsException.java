package com.Backend.exception;

public class SectionAlreadyExistsException extends RuntimeException {

    public SectionAlreadyExistsException(
            String message) {

        super(message);
    }
}