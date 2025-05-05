package com.dim.taskmanager.exception;

import org.springframework.http.HttpStatus;

import com.dim.taskmanager.config.ErrorMessages;

public final class UnAuthorizedException extends AuthException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7324251530262927893L;

	public UnAuthorizedException(String message) {
		super(message);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.UNAUTHORIZED;
	}

	@Override
	public String getErrorMessage() {
		return ErrorMessages.get("authentication.required");
	}
}
