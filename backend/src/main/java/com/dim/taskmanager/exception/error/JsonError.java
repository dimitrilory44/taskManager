package com.dim.taskmanager.exception.error;

import org.springframework.http.HttpStatus;

import com.dim.taskmanager.config.ErrorMessages;
import com.dim.taskmanager.response.output.GlobalResponseError;
import com.dim.taskmanager.utils.ErrorUtils;
import com.fasterxml.jackson.core.JsonParseException;

public record JsonError(
		HttpStatus status,
		String error,
		String message
) implements ApiError<GlobalResponseError> {
	
	public JsonError(JsonParseException ex) {
		this(HttpStatus.BAD_REQUEST, ErrorMessages.get("illegal.error"), ErrorMessages.get("json.syntax", ex.getOriginalMessage()));
	}
	
	@Override
	public GlobalResponseError buildBody() {
		return ErrorUtils.buildError(status, message, error);
	}
	
}