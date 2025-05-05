package com.dim.taskmanager.exception.error;

import org.springframework.http.HttpStatus;

import com.dim.taskmanager.config.ErrorMessages;

public record GenericError(
		HttpStatus status,
		String message,
		String error
) implements ApiError {
	
	public GenericError(Exception ex) {
		this(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ErrorMessages.get("internal.error"));
    }
	
	public GenericError(IllegalArgumentException ex) {
		this(HttpStatus.BAD_REQUEST, ex.getMessage(), ErrorMessages.get("illegal.error"));
	}

}