package com.dim.taskmanager.response.output;

import java.util.List;

public record PaginatedResponse<T>(
		List<T> content,
		Integer pageNumber,
		Integer pageSize,
		Long totalElements,
		Integer totalPages
) {}