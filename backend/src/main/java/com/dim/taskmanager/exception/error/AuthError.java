package com.dim.taskmanager.exception.error;

import java.util.Map;

import org.springframework.http.HttpStatus;

import com.dim.taskmanager.exception.AuthException;

public record AuthError(AuthException ex) implements ApiError {
	
	@Override
	public HttpStatus getStatus() {
		return ex.getHttpStatus();
	}

	@Override
	public String getMessage() {
		return ex.getMessage();
	}

	@Override
	public String getError() {
		return ex.getErrorMessage();
	}

	@Override
	public Map<String, ?> getDetails() {
		return null;
	}

}