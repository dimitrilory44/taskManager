package com.dim.taskmanager.task.exception;

public sealed class TaskException extends RuntimeException 
	permits TaskNotFound {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6676102180019682524L;
	
	public TaskException(String message) {
		super(message);
	}
}
