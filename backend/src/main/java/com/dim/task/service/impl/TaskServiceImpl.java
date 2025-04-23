package com.dim.task.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dim.task.entities.Tasks;
import com.dim.task.mapper.TaskMapper;
import com.dim.task.repository.TaskRepository;
import com.dim.task.response.output.TaskDTO;
import com.dim.task.service.TaskService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
	
	private final TaskRepository taskRepository;
	private final TaskMapper taskMapper;

	@Override
	public List<TaskDTO> getAllTask() {
		List<Tasks> tasks = taskRepository.findAll();
		return taskMapper.toTaskDTO(tasks);
	}

}
