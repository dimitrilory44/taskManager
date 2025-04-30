package com.dim.taskmanager.task.service;

import java.util.List;

import com.dim.taskmanager.task.response.input.TaskRequest;
import com.dim.taskmanager.task.response.output.PatchTaskDTO;
import com.dim.taskmanager.task.response.output.TaskDTO;
import com.dim.taskmanager.task.response.output.UpdateTaskDTO;

public interface TaskService {

	TaskDTO createTask(TaskRequest request);
	List<TaskDTO> getAllTask(); // GET List
	TaskDTO getTask(Long id); // GET Task
	UpdateTaskDTO updateTask(Long id); // PUT
	TaskDTO patchTask(Long id, PatchTaskDTO dto); // PATCH
	void deleteTask(); // DELETE
}
