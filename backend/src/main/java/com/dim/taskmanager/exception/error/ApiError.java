package com.dim.taskmanager.exception.error;

import java.util.Map;

import org.springframework.http.HttpStatus;

public sealed interface ApiError
	permits AuthError, ValidationError, TypeMismatchError, TechnicalError, GenericError {

	HttpStatus getStatus();
	
	String getMessage();
	
	String getError();
	
	Map<String, ?> getDetails();
}
