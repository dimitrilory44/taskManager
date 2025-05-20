package com.dim.taskmanager.project.response.input;

import jakarta.validation.constraints.NotBlank;

public record ProjectRequest(
		
	String description,
	
	@NotBlank
	String name
	
) {}