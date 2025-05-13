package com.dim.taskmanager.task.response.input;

import java.time.LocalDate;
import java.util.List;

import com.dim.taskmanager.attachment.response.input.AttachmentRequest;
import com.dim.taskmanager.project.response.input.UpdateProjectRequest;
import com.dim.taskmanager.task.model.Priority;
import com.dim.taskmanager.task.model.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateTaskRequest(
	
	@NotBlank
	String title,
	
	String description,
	
	@NotNull
	LocalDate dueDate,
	
	@NotNull
	Boolean completed,
	
	UpdateProjectRequest project,
	
	Status status,
	
    Priority priority,
    
    List<AttachmentRequest> attachments
	
) {}
