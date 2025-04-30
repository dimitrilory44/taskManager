package com.dim.taskmanager.exception;

import org.springframework.http.HttpStatus;

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
}
