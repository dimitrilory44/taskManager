package com.dim.taskmanager.attachment.service;

import java.util.List;

import com.dim.taskmanager.attachment.response.input.AttachmentRequest;
import com.dim.taskmanager.attachment.response.output.AttachmentDTO;

public interface AttachmentService {
	AttachmentDTO createAttachment(AttachmentRequest request);
	AttachmentDTO getAttachment(Long id);
	List<AttachmentDTO> getAllAttachments(Long taskId);
	List<AttachmentDTO> updateAttachments(Long taskId, List<AttachmentRequest> request);
}
