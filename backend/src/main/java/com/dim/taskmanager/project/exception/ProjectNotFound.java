package com.dim.taskmanager.project.exception;

public class ProjectNotFound extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2653038193683453064L;
	
	public ProjectNotFound(String message) {
		super(message);
	}

}
