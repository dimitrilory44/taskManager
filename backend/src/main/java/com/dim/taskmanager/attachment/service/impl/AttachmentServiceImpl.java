package com.dim.taskmanager.attachment.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dim.taskmanager.attachment.entity.AttachmentEntity;
import com.dim.taskmanager.attachment.exception.AttachmentNotFound;
import com.dim.taskmanager.attachment.mapper.AttachmentMapper;
import com.dim.taskmanager.attachment.repository.AttachmentRepository;
import com.dim.taskmanager.attachment.response.input.AttachmentRequest;
import com.dim.taskmanager.attachment.response.output.AttachmentDTO;
import com.dim.taskmanager.attachment.service.AttachmentService;
import com.dim.taskmanager.task.entity.TaskEntity;
import com.dim.taskmanager.task.exception.TaskNotFound;
import com.dim.taskmanager.task.repository.TaskRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

	private final AttachmentRepository attachmentRepository;
	private final TaskRepository taskRepository;
	
	private final AttachmentMapper attachmentMapper;
		
	@Override
	public AttachmentDTO createAttachment(AttachmentRequest request) {
		return null;
	}

	@Override
	public AttachmentDTO getAttachment(Long id) {
		AttachmentEntity attachment = attachmentRepository.findById(id)
				.orElseThrow(() -> new AttachmentNotFound("Piece non trouvé pour l'ID : " + id));
		return attachmentMapper.toDTO(attachment);
	}
	
	@Override
	public List<AttachmentDTO> getAllAttachments(Long taskId) {
		List<AttachmentEntity> attachments = attachmentRepository.findAllByTaskId(taskId);
		return attachmentMapper.toDTOList(attachments);
	}

	@Override
	public List<AttachmentDTO> updateAttachments(Long taskId, List<AttachmentRequest> request) {
		
		TaskEntity task = taskRepository.findById(taskId)
				.orElseThrow(() -> new TaskNotFound("Tache non trouvé pour l'ID :" + taskId));
		
		// Récupère les pièces jointes déjà associées à la tâche
		Set<Long> existingIds = task.getAttachments().stream()
		        .map(AttachmentEntity::getId)
		        .collect(Collectors.toSet());
		
		// Garde uniquement les nouvelles pièces jointes qui ne sont pas déjà associées
		List<AttachmentDTO> newAttachments = request.stream()
			.map(this::createAttachment)
			.filter(att -> !existingIds.contains(att.id()))
			.toList();
		
		return newAttachments;
		
	}

}