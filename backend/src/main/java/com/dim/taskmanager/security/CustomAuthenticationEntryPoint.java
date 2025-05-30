package com.dim.taskmanager.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.dim.taskmanager.config.ErrorMessages;
import com.dim.taskmanager.response.output.GlobalResponseError;
import com.dim.taskmanager.utils.ErrorUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        
        GlobalResponseError errorBody = ErrorUtils.buildError(
        		HttpStatus.UNAUTHORIZED, 
        		ErrorMessages.get("token.invalid"), 
        		ErrorMessages.get("user.not.identified")
        );

        String json = ErrorUtils.convertJSONError(errorBody);
        
        response.getWriter().write(json);
		
	}

}