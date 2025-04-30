package com.dim.taskmanager.project;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.dim.taskmanager.mapper.TaskManagerMapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMapper extends TaskManagerMapper<ProjectDTO, ProjectEntity> {
		
	
}