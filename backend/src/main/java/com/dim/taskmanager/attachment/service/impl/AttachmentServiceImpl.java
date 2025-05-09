package com.dim.taskmanager.attachment.service.impl;

import org.springframework.stereotype.Service;

import com.dim.taskmanager.attachment.response.input.AttachmentRequest;
import com.dim.taskmanager.attachment.response.output.AttachmentDTO;
import com.dim.taskmanager.attachment.service.AttachmentService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

	@Override
	public AttachmentDTO createAttachment(AttachmentRequest request) {
		return null;
	}

}