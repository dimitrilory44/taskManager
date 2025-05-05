package com.dim.taskmanager.exception.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.dim.taskmanager.config.ErrorMessages;

public record TypeMismatchError(
		HttpStatus status,
	    String message,
	    String error
) implements ApiError {
	
	public TypeMismatchError(MethodArgumentTypeMismatchException ex) {
		this(HttpStatus.BAD_REQUEST, ex.getMessage(), ErrorMessages.get("argument.parameter.error"));
	}

}