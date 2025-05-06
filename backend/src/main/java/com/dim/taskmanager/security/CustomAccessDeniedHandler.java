package com.dim.taskmanager.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.dim.taskmanager.config.ErrorMessages;
import com.dim.taskmanager.response.output.GlobalResponseError;
import com.dim.taskmanager.utils.ErrorUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        
        GlobalResponseError errorBody = ErrorUtils.buildError(
        		HttpStatus.FORBIDDEN, 
        		ErrorMessages.get("access.denied"), 
        		ErrorMessages.get("access.denied.message")
        );

        String json = ErrorUtils.convertJSONError(errorBody);

        response.getWriter().write(json);
		
	}
	
}