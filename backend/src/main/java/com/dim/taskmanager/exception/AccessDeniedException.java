package com.dim.taskmanager.exception;

import org.springframework.http.HttpStatus;

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
}
