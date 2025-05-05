package com.dim.taskmanager.exception.error;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.dim.taskmanager.config.ErrorMessages;

public final class TypeMismatchError implements ApiError {

	private final MethodArgumentTypeMismatchException ex;
	
	public TypeMismatchError(MethodArgumentTypeMismatchException ex) {
        this.ex = ex;
    }
	
	@Override
	public HttpStatus getStatus() {
		return HttpStatus.BAD_REQUEST;
	}

	@Override
	public String getMessage() {
		return ex.getMessage();
	}

	@Override
	public String getError() {
		return ErrorMessages.get("argument.parameter.error");	
	}

	@Override
	public Map<String, ?> getDetails() {
		return null;
	}

}
