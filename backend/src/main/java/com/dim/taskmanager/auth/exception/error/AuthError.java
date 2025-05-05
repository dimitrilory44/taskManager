package com.dim.taskmanager.auth.exception.error;

import org.springframework.http.HttpStatus;

import com.dim.taskmanager.auth.exception.AuthException;
import com.dim.taskmanager.exception.error.ApiError;

public record AuthError(
		HttpStatus status,
	    String message,
	    String error
) implements ApiError {
	
	public AuthError(AuthException ex) {
        this(ex.getHttpStatus(), ex.getMessage(), ex.getErrorMessage());
    }

}