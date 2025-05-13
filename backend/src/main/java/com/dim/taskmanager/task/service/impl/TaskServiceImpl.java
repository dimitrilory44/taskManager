package com.dim.taskmanager.task.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dim.taskmanager.attachment.mapper.AttachmentMapper;
import com.dim.taskmanager.attachment.response.output.AttachmentDTO;
import com.dim.taskmanager.attachment.service.AttachmentService;
import com.dim.taskmanager.project.mapper.ProjectMapper;
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
	
	private final TaskMapper taskMapper;
	private final ProjectMapper projectMapper;
	private final AttachmentMapper attachmentMapper;
	
	private final ProjectService projectService;
	private final AttachmentService attachmentService;

	@Override
	public TaskDTO createTask(TaskRequest request) {
		
		TaskEntity task = new TaskEntity();
		task.setTitle(request.title());
		task.setDescription(request.description());
		task.setStatus(request.status());
		task.setPriority(request.priority());
		if(request.project() != null) {
			task.setProject(projectMapper.toEntity(projectService.createProject(request.project())));
		}
		if (request.attachments() != null && !request.attachments().isEmpty()) {
			List<AttachmentDTO> atts = request.attachments().stream()
					.map(attachmentService::createAttachment)
					.toList();
			task.setAttachments(attachmentMapper.toEntityList(atts));
		}
		
		return taskMapper.toDTO(taskRepository.save(task));
		
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
		if(request.project() != null) {
			task.setProject(projectMapper.toEntity(projectService.updateProject(id, request.project())));
		} 
		if(request.status() != null) {
			task.setStatus(request.status());
		}
		if(request.priority() != null) {
			task.setPriority(request.priority());
		}
		if (request.attachments() != null && !request.attachments().isEmpty()) {
	        task.setAttachments(attachmentMapper.toEntityList(attachmentService.updateAttachments(id, request.attachments())));
	    }
		
		TaskEntity updatedTask = taskRepository.save(task);
		log.info("Tâche mise à jour avec succès : ID {}", updatedTask.getId());
		
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
		request.project().ifPresent(project -> {
			task.setProject(projectMapper.toEntity(projectService.updateProject(id, project)));	
		});
		request.status().ifPresent(task::setStatus);
		request.priority().ifPresent(task::setPriority);
		request.attachments().ifPresent(attachments -> {		
			task.setAttachments(attachmentMapper.toEntityList(attachmentService.updateAttachments(id, attachments)));
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