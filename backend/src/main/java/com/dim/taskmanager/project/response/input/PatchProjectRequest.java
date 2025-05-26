package com.dim.taskmanager.project.response.input;

import java.util.Optional;

public record PatchProjectRequest(
	Optional<String> name,
	Optional<String> description	
) {}