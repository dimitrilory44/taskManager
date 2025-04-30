package com.dim.taskmanager.attachment;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.dim.taskmanager.mapper.TaskManagerMapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AttachmentMapper extends TaskManagerMapper<AttachmentDTO, AttachmentEntity> {

}