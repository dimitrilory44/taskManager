package com.dim.taskmanager.exception.error;

import java.util.Map;

import org.springframework.http.HttpStatus;

public record GenericError(
		HttpStatus status,
		String message,
		String error,
		Map<String, ?> details
) implements ApiError {

	public GenericError(HttpStatus status, String message, String error) {
        this(status, message, error, null);
    }
	
	@Override
	public HttpStatus getStatus() {
		return status;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getError() {
		return error;
	}

	@Override
	public Map<String, ?> getDetails() {
		return details;
	}

}