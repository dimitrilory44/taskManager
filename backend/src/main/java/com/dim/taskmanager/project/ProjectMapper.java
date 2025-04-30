package com.dim.taskmanager.project;

import org.mapstruct.Mapper;

import com.dim.taskmanager.response.output.ProjectDTO;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
		
	ProjectDTO toProjectDTO(ProjectEntity project);
	
}