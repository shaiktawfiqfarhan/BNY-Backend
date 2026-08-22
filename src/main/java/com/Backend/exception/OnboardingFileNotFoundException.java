package com.Backend.exception;

public class OnboardingFileNotFoundException
        extends RuntimeException {

    public OnboardingFileNotFoundException(
            String message) {

        super(message);
    }
}