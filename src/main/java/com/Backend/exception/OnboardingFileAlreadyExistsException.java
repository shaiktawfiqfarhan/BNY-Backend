package com.Backend.exception;

public class OnboardingFileAlreadyExistsException
        extends RuntimeException {

    public OnboardingFileAlreadyExistsException(
            String message) {

        super(message);
    }
}