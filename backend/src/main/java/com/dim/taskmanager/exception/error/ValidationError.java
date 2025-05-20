package com.dim.taskmanager.exception.error;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.dim.taskmanager.config.ErrorMessages;
import com.dim.taskmanager.response.output.ValidationResponseError;
import com.dim.taskmanager.utils.ErrorUtils;

public record ValidationError(
		HttpStatus status,
		String error,
	    String message,
		Map<String, String> details
) implements ApiError<ValidationResponseError> {

	public ValidationError(MethodArgumentNotValidException ex) {
		this(
			HttpStatus.BAD_REQUEST, 
			ErrorMessages.get("validation.error"),
			ErrorMessages.get("validation.message"), 
			ex.getBindingResult().getFieldErrors().stream()
				.collect(Collectors.toMap(
						FieldError::getField, 
						FieldError::getDefaultMessage,
						(existing, replacement) -> existing  // en cas de doublon, garde le premier
				)
			)
		);
	}

	@Override
	public ValidationResponseError buildBody() {
		return ErrorUtils.buildValidationError(details, status, message);
	}

}