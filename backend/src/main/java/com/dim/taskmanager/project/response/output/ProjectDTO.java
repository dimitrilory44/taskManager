package com.dim.taskmanager.project.response.output;

public record ProjectDTO(
	Long id,
	String name,
	String description
) {}