package com.dim.taskmanager.exception;

import org.springframework.http.HttpStatus;

import com.dim.taskmanager.config.ErrorMessages;

public final class InvalidCredentialsException extends AuthException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5896779140223921178L;

	public InvalidCredentialsException(String message) {
		super(message);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.UNAUTHORIZED;
	}

	@Override
	public String getErrorMessage() {
		return ErrorMessages.get("invalid.credentials");
	}
}
