package com.dim.taskmanager.task.response.input;

import java.time.LocalDateTime;

import com.dim.taskmanager.task.model.Priority;
import com.dim.taskmanager.task.model.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskRequest(
	@NotBlank
	String title,
		
	String description,
		
	@NotNull
	LocalDateTime dueDate,
		
	@NotNull
	Boolean completed,
		
	@NotNull
	Long projectId,
		
	Status status,
		
	Priority priority
) {}