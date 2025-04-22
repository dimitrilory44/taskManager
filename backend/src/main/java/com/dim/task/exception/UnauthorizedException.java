package com.dim.task.exception;

public class UnauthorizedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7324251530262927893L;

	public UnauthorizedException(String message) {
		super(message);
	}
}
