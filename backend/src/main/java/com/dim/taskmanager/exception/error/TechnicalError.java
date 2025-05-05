package com.dim.taskmanager.exception.error;

import org.springframework.http.HttpStatus;

import com.dim.taskmanager.config.ErrorMessages;
import com.dim.taskmanager.exception.TechnicalException;

public record TechnicalError(
		HttpStatus status,
	    String message,
	    String error
) implements ApiError {
	
	public TechnicalError(TechnicalException ex) {
		this(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ErrorMessages.get("technical.error"));
	}

}