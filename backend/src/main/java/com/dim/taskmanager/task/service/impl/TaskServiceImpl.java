package com.dim.taskmanager.task.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dim.taskmanager.attachment.entity.AttachmentEntity;
import com.dim.taskmanager.attachment.mapper.AttachmentMapper;
import com.dim.taskmanager.attachment.repository.AttachmentRepository;
import com.dim.taskmanager.project.exception.ProjectNotFound;
import com.dim.taskmanager.project.mapper.ProjectMapper;
import com.dim.taskmanager.project.repository.ProjectRepository;
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

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
	
	private final TaskRepository taskRepository;
	private final ProjectRepository projectRepository;
	private final AttachmentRepository attachmentRepository;
	
	private final TaskMapper taskMapper;
	private final ProjectMapper projectMapper;
	private final AttachmentMapper attachmentMapper;
	
	private final ProjectService projectService;

	@Override
	public TaskDTO createTask(TaskRequest rawTask) {
		projectRepository.findById(rawTask.projectId())
			.orElseThrow(() -> new ProjectNotFound("Projet non trouvé pour l'ID : " + rawTask.projectId()));
				
		TaskRequest request = new TaskRequest(
			rawTask.title(),
			rawTask.description(),
			rawTask.projectId(),
			rawTask.status(),
			rawTask.priority(),
			rawTask.attachments()
		);
				
		return taskMapper.toTaskDTO(request);
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
	public Page<TaskDTO> getAllTask(Pageable pageable) {
		Page<TaskEntity> tasks = taskRepository.findAll(pageable);
		return taskMapper.toPageDTO(tasks);
	}

	@Override
	public TaskDTO updateTask(Long id, UpdateTaskRequest request) {
		log.info("Tentative de modification d'une tache avec l'ID : {}", id);
		TaskEntity task = taskRepository.findById(id)
				.orElseThrow(() -> new TaskNotFound("Tache non trouvé pour l'ID :" + id));
		task.setTitle(request.title());
		task.setDescription(request.description());
		task.setDueDate(request.dueDate());
		task.setCompleted(request.completed());
		task.setProject(projectMapper.toEntity(projectService.getProject(request.projectId())));
		if(request.status() != null) {
			task.setStatus(request.status());
		}
		if(request.priority() != null) {
			task.setPriority(request.priority());
		}
		
		if (request.attachments() != null && !request.attachments().isEmpty()) {
	        List<AttachmentEntity> attachments = request.attachments().stream()
	            .map(attachmentDTO -> {
	                AttachmentEntity entity = attachmentMapper.toEntity(attachmentDTO);
	                entity.setTask(task); 
	                return entity;
	            })
	            .collect(Collectors.toList());

	        task.setAttachments(attachments);
	    }
		
		TaskEntity updatedTask = taskRepository.save(task);
		return taskMapper.toDTO(updatedTask);
	}

	@Override
	public TaskDTO patchTask(Long id, PatchTaskRequest request) {
		log.info("Tentative de patch d'une tache avec l'ID : {}", id);
		TaskEntity task = taskRepository.findById(id)
				.orElseThrow(() -> new TaskNotFound("Tache non trouvé pour l'ID :" + id));
		request.title().ifPresent(task::setTitle);
		request.description().ifPresent(task::setDescription);
		request.completed().ifPresent(task::setCompleted);
		request.dueDate().ifPresent(task::setDueDate);
		request.projectId().ifPresent(projectId -> {
			task.setProject(projectMapper.toEntity(projectService.getProject(projectId)));	
		});
		request.status().ifPresent(task::setStatus);
		request.priority().ifPresent(task::setPriority);
		request.attachmentIds().ifPresent(attachmentIds -> {
			List<AttachmentEntity> attachmentAdd = attachmentRepository.findAllById(attachmentIds);
			task.getAttachments().addAll(
					attachmentAdd.stream()
						.filter(att -> !task.getAttachments().contains(att))
						.toList()
			);
		});
		return taskMapper.toDTO(taskRepository.save(task));
	}
	
	@Override
	public void deleteTask(Long id) {
		log.info("Tentative de suppression d'une tâche avec l'ID : {}", id);
		if(!taskRepository.existsById(id)) {
			throw new TaskNotFound("Tache non trouvé pour l'ID : " + id);
		}
		taskRepository.deleteById(id);
	}

}