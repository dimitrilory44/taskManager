package com.dim.taskmanager.exception.error;

import org.springframework.http.HttpStatus;

import com.dim.taskmanager.config.ErrorMessages;
import com.dim.taskmanager.exception.TechnicalException;
import com.dim.taskmanager.response.output.GlobalResponseError;
import com.dim.taskmanager.utils.ErrorUtils;

public record TechnicalError(
		HttpStatus status,
	    String error,
	    String message
) implements ApiError<GlobalResponseError> {
	
	public TechnicalError(TechnicalException ex) {
		this(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.get("technical.error"), ex.getMessage());
	}

	@Override
	public GlobalResponseError buildBody() {
		return ErrorUtils.buildError(status, message, error);
	}

}