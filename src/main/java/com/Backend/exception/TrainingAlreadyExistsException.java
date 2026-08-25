package com.Backend.exception;

public class TrainingAlreadyExistsException extends RuntimeException{
	public TrainingAlreadyExistsException(String message) {
		super(message);
	}
}
