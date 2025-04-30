package com.dim.taskmanager.exception;

import org.springframework.http.HttpStatus;

public final class UserNotFoundException extends AuthException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4846305028350434273L;

	public UserNotFoundException(String message) {
		super(message);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.NOT_FOUND;
	}
}