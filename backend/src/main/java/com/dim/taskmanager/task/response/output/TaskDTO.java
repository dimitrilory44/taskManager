package com.dim.taskmanager.task.response.output;

import java.util.Date;
import java.util.List;

import com.dim.taskmanager.attachment.AttachmentDTO;
import com.dim.taskmanager.task.model.Priority;
import com.dim.taskmanager.task.model.Status;

public record TaskDTO (
		
	String title,
	String description,
	Boolean completed,
	Date dueDate,
	Status status,
	Priority priority,
	List<AttachmentDTO> attachments

){}