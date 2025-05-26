package com.dim.taskmanager.project.response.input;

import jakarta.validation.constraints.NotBlank;

public record UpdateProjectRequest(
		
	@NotBlank
	String name,
	
	String description
		
) {}