package com.dim.taskmanager.user.exception;

public class UserNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4846305028350434273L;

	public UserNotFoundException(String message) {
		super(message);
	}

	
}