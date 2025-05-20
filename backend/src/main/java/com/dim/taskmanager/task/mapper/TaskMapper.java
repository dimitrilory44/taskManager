package com.dim.taskmanager.task.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.dim.taskmanager.attachment.mapper.AttachmentMapper;
import com.dim.taskmanager.mapper.TaskManagerMapper;
import com.dim.taskmanager.project.mapper.ProjectMapper;
import com.dim.taskmanager.task.entity.TaskEntity;
import com.dim.taskmanager.task.response.output.TaskDTO;

@Mapper(
	componentModel = "spring",
	uses = {
		AttachmentMapper.class,
		ProjectMapper.class
	},
	unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TaskMapper extends TaskManagerMapper<TaskDTO, TaskEntity> {
	
	@Mapping(target = "authorId", source = "author.id")
	@Mapping(target = "assignedToId", source = "assignedTo.id")
	TaskDTO toDTO(TaskEntity entity);
	
}