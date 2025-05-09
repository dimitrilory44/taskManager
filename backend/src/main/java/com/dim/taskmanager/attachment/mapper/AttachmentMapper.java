package com.dim.taskmanager.attachment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.dim.taskmanager.attachment.entity.AttachmentEntity;
import com.dim.taskmanager.attachment.response.input.AttachmentRequest;
import com.dim.taskmanager.attachment.response.output.AttachmentDTO;
import com.dim.taskmanager.mapper.TaskManagerMapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AttachmentMapper extends TaskManagerMapper<AttachmentDTO, AttachmentEntity> {

	AttachmentDTO toAttachmentDTO(AttachmentRequest request);
}