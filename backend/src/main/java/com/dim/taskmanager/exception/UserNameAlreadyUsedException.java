package com.dim.taskmanager.exception;

public class UserNameAlreadyUsedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7475073867705222852L;
	
	public UserNameAlreadyUsedException(String message) {
		super(message);
	}

}