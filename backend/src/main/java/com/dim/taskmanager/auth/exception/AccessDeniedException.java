package com.dim.taskmanager.auth.exception;

import org.springframework.http.HttpStatus;

import com.dim.taskmanager.config.ErrorMessages;

public final class AccessDeniedException extends AuthException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3731578551775774691L;

	public AccessDeniedException(String message) {
		super(message);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.FORBIDDEN;
	}

	@Override
	public String getErrorMessage() {
		return ErrorMessages.get("access.denied");
	}
}
