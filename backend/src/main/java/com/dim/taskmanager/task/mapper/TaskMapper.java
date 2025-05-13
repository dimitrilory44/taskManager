package com.dim.taskmanager.task.mapper;

import java.time.LocalDate;

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
		
	@Mapping(target = "completed", expression = "java(setDefaultCompleted())")
	@Mapping(target = "dueDate", expression = "java(setDefaultDueDate())")
	TaskDTO toDTO(TaskEntity entity);
	
	default Boolean setDefaultCompleted() {
		return false;
	}
	
	default LocalDate setDefaultDueDate() {
		return LocalDate.now();
	}
}