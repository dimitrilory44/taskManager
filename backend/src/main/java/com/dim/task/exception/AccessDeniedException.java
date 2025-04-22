package com.dim.task.exception;

public class AccessDeniedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3731578551775774691L;

	public AccessDeniedException(String message) {
		super(message);
	}
}
