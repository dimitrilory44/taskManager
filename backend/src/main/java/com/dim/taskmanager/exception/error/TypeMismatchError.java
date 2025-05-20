package com.dim.taskmanager.exception.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.dim.taskmanager.config.ErrorMessages;
import com.dim.taskmanager.response.output.GlobalResponseError;
import com.dim.taskmanager.utils.ErrorUtils;

public record TypeMismatchError(
		HttpStatus status,
		String error,
	    String message
) implements ApiError<GlobalResponseError> {
	
	public TypeMismatchError(MethodArgumentTypeMismatchException ex) {
		this(HttpStatus.BAD_REQUEST, ErrorMessages.get("argument.parameter.error"), ex.getMessage());
	}

	@Override
	public GlobalResponseError buildBody() {
		return ErrorUtils.buildError(status, message, error);
	}

}