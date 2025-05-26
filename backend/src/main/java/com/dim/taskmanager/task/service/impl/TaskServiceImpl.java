package com.dim.taskmanager.task.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dim.taskmanager.project.mapper.ProjectMapper;
import com.dim.taskmanager.project.response.output.ProjectDTO;
import com.dim.taskmanager.project.service.ProjectService;
import com.dim.taskmanager.task.entity.TaskEntity;
import com.dim.taskmanager.task.exception.TaskNotFound;
import com.dim.taskmanager.task.mapper.TaskMapper;
import com.dim.taskmanager.task.repository.TaskRepository;
import com.dim.taskmanager.task.response.input.PatchTaskRequest;
import com.dim.taskmanager.task.response.input.TaskRequest;
import com.dim.taskmanager.task.response.input.UpdateTaskRequest;
import com.dim.taskmanager.task.response.output.TaskDTO;
import com.dim.taskmanager.task.service.TaskService;
import com.dim.taskmanager.user.mapper.UserMapper;
import com.dim.taskmanager.user.response.output.UserDTO;
import com.dim.taskmanager.user.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
	
	private final TaskRepository taskRepository;
	
	private final TaskMapper taskMapper;
	private final UserMapper userMapper;
	private final ProjectMapper projectMapper;
	
	private final ProjectService projectService;
	private final UserService userService;
	
	@Override
	public TaskDTO createTask(Long projectId, TaskRequest request) {
		TaskEntity task = new TaskEntity();
		
		task.setTitle(request.title());
		task.setDescription(request.description());
		task.setStatus(request.status());
		task.setPriority(request.priority());
		task.setDueDate(LocalDate.now());
		task.setCompleted(false);
		
		UserDTO authorDTO = userService.getUser(request.authorId());
		task.setAuthor(userMapper.toEntity(authorDTO));
		
		if (request.assignedTo() != null) {
			UserDTO assigneeDTO = userService.getUser(request.assignedTo());
			task.setAssignedTo(userMapper.toEntity(assigneeDTO));
		}
		
		// Dans le cas d'un existant ou d'un nouveau projet
		ProjectDTO project = projectService.getProject(projectId);
		task.setProject(projectMapper.toEntity(project));
		
		if(request.project() != null) {
			ProjectDTO newProject = projectService.createProject(request.authorId(), request.project());
			task.setProject(projectMapper.toEntity(newProject));
		} 
		
		TaskEntity savedTask = taskRepository.save(task);
		log.info("Tâche créé avec succès : ID {}", savedTask.getId());
		
		return taskMapper.toDTO(savedTask);
		
	}
	
	@Override
	public TaskDTO getTask(Long id) {
		log.info("Tentative de récupération d'une tache avec l'ID : {}", id);
		
		TaskEntity task = taskRepository.findById(id)
				.orElseThrow(() -> 
					new TaskNotFound("Tache non trouvé pour l'ID :" + id)
				);
		
		return taskMapper.toDTO(task);
		
	}
	
	@Override
	public Page<TaskDTO> getTasks(Pageable pageable) {
		Page<TaskEntity> tasks = taskRepository.findAll(pageable);
		
		return taskMapper.toPageDTO(tasks);
		
	}
	
	@Override
	public List<TaskDTO> getTasksByProject(Long projectId) {
		List<TaskEntity> tasks = taskRepository.findByProjectId(projectId);
		
		return taskMapper.toDTOList(tasks);
	}

	@Override
	public TaskDTO updateTask(Long id, Long projectId, UpdateTaskRequest request) {
		log.info("Tentative de modification d'une tache avec l'ID : {}", id);
		
		TaskEntity task = taskMapper.toEntity(this.getTask(id));
		
		task.setTitle(request.title());
		task.setDescription(request.description());
		task.setDueDate(request.dueDate());
		task.setCompleted(request.completed());
		if(request.project() != null) {
			task.setProject(projectMapper.toEntity(projectService.updateProject(projectId, request.project())));
		} 
		if(request.status() != null) {
			task.setStatus(request.status());
		}
		if(request.priority() != null) {
			task.setPriority(request.priority());
		}
		
		TaskEntity updatedTask = taskRepository.save(task);
		log.info("Tâche mise à jour avec succès : ID {}", updatedTask.getId());
		
		return taskMapper.toDTO(updatedTask);
		
	}

	@Override
	public TaskDTO patchTask(Long id, Long projectId, PatchTaskRequest request) {
		log.info("Tentative de patch d'une tache avec l'ID : {}", id);
		
		TaskEntity task = taskMapper.toEntity(this.getTask(id));
		
		request.title().ifPresent(task::setTitle);
		request.description().ifPresent(task::setDescription);
		request.completed().ifPresent(task::setCompleted);
		request.dueDate().ifPresent(task::setDueDate);
		request.project().ifPresent(project -> {
			task.setProject(projectMapper.toEntity(projectService.updateProject(projectId, project)));	
		});
		request.status().ifPresent(task::setStatus);
		request.priority().ifPresent(task::setPriority);
		
		TaskEntity patchedTask = taskRepository.save(task);
		log.info("Tâche patchée avec succès : ID {}", patchedTask.getId());
		
		return taskMapper.toDTO(patchedTask);
		
	}
	
	@Override
	public void deleteTask(Long id) {
		log.info("Tentative de suppression d'une tâche avec l'ID : {}", id);
		TaskEntity task = taskMapper.toEntity(this.getTask(id));
		
		taskRepository.delete(task);
		
	}

}