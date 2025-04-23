package com.dim.task.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.dim.task.entities.Tasks;
import com.dim.task.response.output.TaskDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {
	
	List<TaskDTO> toTaskDTO(List<Tasks> task);
}
