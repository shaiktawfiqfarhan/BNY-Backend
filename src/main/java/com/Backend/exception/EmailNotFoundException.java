package com.Backend.exception;

public class EmailNotFoundException extends RuntimeException{
	public EmailNotFoundException(String message) {
        super(message);
    }
}
