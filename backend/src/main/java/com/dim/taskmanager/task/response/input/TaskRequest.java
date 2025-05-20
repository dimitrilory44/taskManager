package com.dim.taskmanager.task.response.input;

import java.util.List;

import com.dim.taskmanager.attachment.response.input.AttachmentRequest;
import com.dim.taskmanager.project.response.input.ProjectRequest;
import com.dim.taskmanager.task.model.Priority;
import com.dim.taskmanager.task.model.Status;

import jakarta.validation.constraints.NotBlank;

public record TaskRequest(
	@NotBlank(message = "Le titre est obligatoire")
	String title,
		
	String description,
		
	ProjectRequest project,
	
	Long projectId,
		
	Status status,
		
	Priority priority,
	
	Long authorId,
	
	Long assignedTo,
	
	List<AttachmentRequest> attachments
) {}