package com.dim.taskmanager.task.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dim.taskmanager.task.entity.TaskEntity;
import com.dim.taskmanager.task.mapper.TaskMapper;
import com.dim.taskmanager.task.repository.TaskRepository;
import com.dim.taskmanager.task.response.input.TaskRequest;
import com.dim.taskmanager.task.response.output.PatchTaskDTO;
import com.dim.taskmanager.task.response.output.TaskDTO;
import com.dim.taskmanager.task.response.output.UpdateTaskDTO;
import com.dim.taskmanager.task.service.TaskService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
	
	private final TaskRepository taskRepository;
	private final TaskMapper taskMapper;

	@Override
	public List<TaskDTO> getAllTask() {
		List<TaskEntity> tasks = taskRepository.findAll();
		return taskMapper.toDTOList(tasks);
	}

	@Override
	public UpdateTaskDTO updateTask(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteTask() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TaskDTO createTask(TaskRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaskDTO getTask(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaskDTO patchTask(Long id, PatchTaskDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
