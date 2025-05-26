package com.dim.taskmanager.task.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dim.taskmanager.task.response.input.PatchTaskRequest;
import com.dim.taskmanager.task.response.input.TaskRequest;
import com.dim.taskmanager.task.response.input.UpdateTaskRequest;
import com.dim.taskmanager.task.response.output.TaskDTO;

public interface TaskService {
	TaskDTO createTask(Long projectId, TaskRequest request);
	Page<TaskDTO> getTasks(Pageable pageable); 
	List<TaskDTO> getTasksByProject(Long projectId);
	TaskDTO getTask(Long id); 
	TaskDTO updateTask(Long id, Long projectId, UpdateTaskRequest taskRequest); 
	TaskDTO patchTask(Long id, Long projectId, PatchTaskRequest dto); 
	void deleteTask(Long id);
}