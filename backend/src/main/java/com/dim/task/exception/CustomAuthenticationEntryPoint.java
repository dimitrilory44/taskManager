package com.dim.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");

        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", HttpStatus.UNAUTHORIZED.value());
        errorBody.put("error", "Non authentifié");
        errorBody.put("message", "Un token valide est requis pour accéder à cette ressource");

        String json = errorBody.entrySet().stream()
        	    .map(e -> {
        	        String key = "\"" + e.getKey() + "\"";
        	        Object value = e.getValue();
        	        String valueStr = (value instanceof Number || value instanceof Boolean)
        	            ? value.toString()
        	            : "\"" + value.toString() + "\"";
        	        return key + ": " + valueStr;
        	    })
        	    .collect(Collectors.joining(", ", "{", "}"));
        
        response.getWriter().write(json);
		
	}

}
