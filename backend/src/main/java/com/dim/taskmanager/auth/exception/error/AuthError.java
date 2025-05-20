package com.dim.taskmanager.auth.exception.error;

import org.springframework.http.HttpStatus;

import com.dim.taskmanager.auth.exception.AuthException;
import com.dim.taskmanager.exception.error.ApiError;
import com.dim.taskmanager.response.output.GlobalResponseError;
import com.dim.taskmanager.utils.ErrorUtils;

public record AuthError(
		HttpStatus status,
	    String message,
	    String error
) implements ApiError<GlobalResponseError> {
	
	public AuthError(AuthException ex) {
        this(ex.getHttpStatus(), ex.getMessage(), ex.getErrorMessage());
    }

	@Override
	public GlobalResponseError buildBody() {
		return ErrorUtils.buildError(status, message, error);
	}

}