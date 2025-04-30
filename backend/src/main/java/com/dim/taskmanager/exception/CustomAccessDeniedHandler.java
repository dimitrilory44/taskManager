package com.dim.taskmanager.exception;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

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

        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", HttpStatus.FORBIDDEN.value());
        errorBody.put("error", "Accès interdit");
        errorBody.put("message", "Vous n'avez pas les droits suffisants pour accéder à cette ressource");

        // Convertit en JSON manuellement
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
