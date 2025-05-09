package com.dim.taskmanager.task.response.input;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.dim.taskmanager.task.model.Priority;
import com.dim.taskmanager.task.model.Status;

public record PatchTaskRequest(
	
	Optional<String> title,
	Optional<String> description,
	Optional<Boolean> completed,
	Optional<LocalDate> dueDate,
	Optional<Long> projectId,
	Optional<Status> status,
	Optional<Priority> priority,
	Optional<List<Long>> attachmentIds
) {}