package com.Backend.exception;

public class SectionNotFoundException extends RuntimeException {
	public SectionNotFoundException(String message){
		super(message);
	}
}
