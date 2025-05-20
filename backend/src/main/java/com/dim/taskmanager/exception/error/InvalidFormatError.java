package com.dim.taskmanager.exception.error;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;

import com.dim.taskmanager.config.ErrorMessages;
import com.dim.taskmanager.response.output.GlobalResponseError;
import com.dim.taskmanager.utils.ErrorUtils;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public record InvalidFormatError(
		HttpStatus status,
		String error,
		String message
) implements ApiError<GlobalResponseError> {
	
	public InvalidFormatError(HttpMessageNotReadableException ex) {
		this(HttpStatus.BAD_REQUEST, ErrorMessages.get("illegal.error"), extractMessage(ex));    
	}
	
	private static String extractMessage(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException ife) {
            String fieldName = ife.getPath().get(ife.getPath().size() - 1).getFieldName();
            String invalidValue = String.valueOf(ife.getValue());
            String accepted = Arrays.toString(ife.getTargetType().getEnumConstants());

            return ErrorMessages.get("invalid.format", invalidValue, fieldName, accepted);
        }

        // Fallback générique si ce n'est pas une InvalidFormatException
        return ErrorMessages.get("json.invalid", ex.getMessage());
    }

	@Override
	public GlobalResponseError buildBody() {
		return ErrorUtils.buildError(status, message, error);
	}
}