package com.dim.taskmanager.exception;

public abstract class TechnicalException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3558464653568910434L;

	public TechnicalException(String message) {
		super(message);
	}

}
