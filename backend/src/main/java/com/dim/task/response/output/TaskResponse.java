package com.dim.task.response.output;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskResponse<T> {
	private boolean success;
	private String message;
	private T data;
}
