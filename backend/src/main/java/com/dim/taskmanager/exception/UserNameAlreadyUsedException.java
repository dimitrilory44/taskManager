package com.dim.taskmanager.exception;

import org.springframework.http.HttpStatus;

import com.dim.taskmanager.config.ErrorMessages;

public final class UserNameAlreadyUsedException extends AuthException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7475073867705222852L;
	
	public UserNameAlreadyUsedException(String message) {
		super(message);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.CONFLICT;
	}

	@Override
	public String getErrorMessage() {
		return ErrorMessages.get("username.exist");
	}

}