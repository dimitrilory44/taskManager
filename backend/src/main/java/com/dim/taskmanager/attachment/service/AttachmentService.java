package com.dim.taskmanager.attachment.service;

import com.dim.taskmanager.attachment.response.input.AttachmentRequest;
import com.dim.taskmanager.attachment.response.output.AttachmentDTO;

public interface AttachmentService {
	AttachmentDTO createAttachment(AttachmentRequest request);
}
