package com.dim.taskmanager.task.response.output;

import java.time.LocalDate;

import com.dim.taskmanager.project.response.output.ProjectDTO;
import com.dim.taskmanager.task.model.Priority;
import com.dim.taskmanager.task.model.Status;

public record TaskDTO (
		
	String title,
	String description,
	LocalDate dueDate,
	Boolean completed,
	ProjectDTO project,
	Status status,
	Priority priority,
	Long authorId,
	Long assignedToId
){}