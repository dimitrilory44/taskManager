package com.dim.taskmanager.task.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dim.taskmanager.task.response.input.PatchTaskRequest;
import com.dim.taskmanager.task.response.input.TaskRequest;
import com.dim.taskmanager.task.response.input.UpdateTaskRequest;
import com.dim.taskmanager.task.response.output.TaskDTO;

public interface TaskService {

	TaskDTO createTask(TaskRequest request);
	Page<TaskDTO> getTasks(Pageable pageable); 
	TaskDTO getTask(Long id); 
	TaskDTO updateTask(Long id, UpdateTaskRequest taskRequest); 
	TaskDTO patchTask(Long id, PatchTaskRequest dto); 
	void deleteTask(Long id);
}
