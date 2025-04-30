package com.dim.taskmanager.task.response.output;

import java.util.Date;
import java.util.Optional;

import com.dim.taskmanager.task.model.Priority;
import com.dim.taskmanager.task.model.Status;

public record PatchTaskDTO(
	
	Optional<String> title,
	Optional<String> description,
	Optional<Boolean> completed,
	Optional<Date> dueDate,
	Optional<Long> projectId,
	Optional<Status> status,
	Optional<Priority> priority
	
) {}