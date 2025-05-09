package com.dim.taskmanager.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.dim.taskmanager.mapper.TaskManagerMapper;
import com.dim.taskmanager.project.entity.ProjectEntity;
import com.dim.taskmanager.project.response.input.ProjectRequest;
import com.dim.taskmanager.project.response.output.ProjectDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMapper extends TaskManagerMapper<ProjectDTO, ProjectEntity> {
		
	ProjectDTO toProjectDTO(ProjectRequest request);
		
}