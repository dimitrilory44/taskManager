package com.dim.taskmanager.exception.error;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.dim.taskmanager.config.ErrorMessages;

public record ValidationError(
		HttpStatus status,
	    String message,
	    String error,
		Map<String, ?> details
) implements ApiError {

	public ValidationError(MethodArgumentNotValidException ex) {
		this(
			HttpStatus.BAD_REQUEST, 
			ErrorMessages.get("validation.message"), 
			ErrorMessages.get("validation.error"),
			Map.of("fieldErrors", ex.getBindingResult().getFieldErrors().stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (e1, e2) -> e1))
			)
		);
	}

}