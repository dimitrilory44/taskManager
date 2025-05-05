package com.dim.taskmanager.exception.error;

import java.util.Map;

import org.springframework.http.HttpStatus;

import com.dim.taskmanager.config.ErrorMessages;

public record ValidationError(Map<String, ?> fieldErrors) implements ApiError {

	@Override
	public HttpStatus getStatus() {
		return HttpStatus.BAD_REQUEST;
	}

	@Override
	public String getMessage() {
		return ErrorMessages.get("validation.message");
	}

	@Override
	public String getError() {
		return ErrorMessages.get("validation.error");
	}

	@Override
	public Map<String, ?> getDetails() {
		return Map.of("fieldErrors", fieldErrors);
	}

}
