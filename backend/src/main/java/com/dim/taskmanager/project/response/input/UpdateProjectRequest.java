package com.dim.taskmanager.project.response.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateProjectRequest(
	
	@NotNull
	Long id,
		
	String description,
		
	@NotBlank
	String name,
		
	@NotNull
	Long userId
) {}
