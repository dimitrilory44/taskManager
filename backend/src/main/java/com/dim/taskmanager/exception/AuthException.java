package com.dim.taskmanager.exception;

import org.springframework.http.HttpStatus;

public sealed abstract class AuthException extends RuntimeException 
	permits EmailAlreadyUsedException, InvalidCredentialsException, UserNameAlreadyUsedException,
			UserNotFoundException, UnAuthorizedException, AccessDeniedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8841261525070877247L;

	public AuthException(String message) {
		super(message);
	}
	
	public abstract HttpStatus getHttpStatus();
}
