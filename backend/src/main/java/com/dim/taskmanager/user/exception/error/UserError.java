package com.dim.taskmanager.user.exception.error;

import org.springframework.http.HttpStatus;

import com.dim.taskmanager.config.ErrorMessages;
import com.dim.taskmanager.exception.error.ApiError;
import com.dim.taskmanager.user.exception.UserNotFoundException;

public record UserError(
		HttpStatus status,
		String message,
		String error
) implements ApiError {
	
	public UserError(UserNotFoundException ex) {
		this(HttpStatus.NOT_FOUND, ex.getMessage(), ErrorMessages.get("user.not.found"));
	}
}