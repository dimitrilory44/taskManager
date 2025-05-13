package com.dim.taskmanager.task.response.input;

import java.util.List;

import com.dim.taskmanager.attachment.response.input.AttachmentRequest;
import com.dim.taskmanager.project.response.input.ProjectRequest;
import com.dim.taskmanager.task.model.Priority;
import com.dim.taskmanager.task.model.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskRequest(
	@NotBlank(message = "Le titre est obligatoire")
	String title,
		
	String description,
		
	ProjectRequest project,
		
	Status status,
		
	Priority priority,
	
	List<AttachmentRequest> attachments
) {}