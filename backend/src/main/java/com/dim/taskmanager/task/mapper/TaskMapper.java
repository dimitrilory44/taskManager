package com.dim.taskmanager.task.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.dim.taskmanager.attachment.AttachmentMapper;
import com.dim.taskmanager.mapper.TaskManagerMapper;
import com.dim.taskmanager.project.ProjectMapper;
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
		
	List<TaskDTO> toTaskListDTO(List<TaskEntity> task);
	
}