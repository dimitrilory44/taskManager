package com.dim.task.auth.exception;

public class UnAuthorizedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7324251530262927893L;

	public UnAuthorizedException(String message) {
		super(message);
	}
}
