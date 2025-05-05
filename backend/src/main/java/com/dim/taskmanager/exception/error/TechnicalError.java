package com.dim.taskmanager.exception.error;

import java.util.Map;

import org.springframework.http.HttpStatus;

import com.dim.taskmanager.config.ErrorMessages;
import com.dim.taskmanager.exception.TechnicalException;

public record TechnicalError(TechnicalException ex) implements ApiError {
	
	@Override
	public HttpStatus getStatus() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@Override
	public String getMessage() {
		return ex.getMessage();
	}

	@Override
	public String getError() {
		return ErrorMessages.get("technical.error");
	}

	@Override
	public Map<String, ?> getDetails() {
		return null;
	}

}