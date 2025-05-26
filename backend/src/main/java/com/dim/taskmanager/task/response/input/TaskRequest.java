package com.dim.taskmanager.task.response.input;

import com.dim.taskmanager.project.response.input.ProjectRequest;
import com.dim.taskmanager.task.model.Priority;
import com.dim.taskmanager.task.model.Status;

import jakarta.validation.constraints.NotBlank;

public record TaskRequest(
		
	@NotBlank(message = "Le titre est obligatoire")
	String title,
		
	String description,
		
	ProjectRequest project,
			
	Status status,
		
	Priority priority,
	
	Long authorId,
	
	Long assignedTo
	
) {}