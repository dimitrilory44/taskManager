package com.dim.taskmanager.project.response.input;

import jakarta.validation.constraints.NotBlank;

public record ProjectRequest(
		
	@NotBlank
	String name,
	
	String description
	
) {}