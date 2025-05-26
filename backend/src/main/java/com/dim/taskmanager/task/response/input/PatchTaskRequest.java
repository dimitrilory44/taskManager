package com.dim.taskmanager.task.response.input;

import java.time.LocalDate;
import java.util.Optional;

import com.dim.taskmanager.project.response.input.UpdateProjectRequest;
import com.dim.taskmanager.task.model.Priority;
import com.dim.taskmanager.task.model.Status;

public record PatchTaskRequest(
	
	Optional<String> title,
	Optional<String> description,
	Optional<Boolean> completed,
	Optional<LocalDate> dueDate,
	Optional<UpdateProjectRequest> project,
	Optional<Status> status,
	Optional<Priority> priority
	
) {}