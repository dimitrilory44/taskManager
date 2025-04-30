package com.dim.taskmanager.attachment;

import java.util.List;

import org.mapstruct.Mapper;

import com.dim.taskmanager.response.output.AttachmentDTO;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {
	
	AttachmentDTO toAttachmentDTO(AttachmentEntity attachment);
	
	List<AttachmentDTO> toAttachmentListDTO(List<AttachmentEntity> attachments);

}