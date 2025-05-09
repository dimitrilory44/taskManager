package com.dim.taskmanager.project.service.impl;

import org.springframework.stereotype.Service;

import com.dim.taskmanager.project.entity.ProjectEntity;
import com.dim.taskmanager.project.exception.ProjectNotFound;
import com.dim.taskmanager.project.mapper.ProjectMapper;
import com.dim.taskmanager.project.repository.ProjectRepository;
import com.dim.taskmanager.project.response.input.ProjectRequest;
import com.dim.taskmanager.project.response.output.ProjectDTO;
import com.dim.taskmanager.project.service.ProjectService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository projectRepository;
	private final ProjectMapper projectMapper;
	
	@Override
	public ProjectDTO createProject(ProjectRequest request) {
		
		return null;
	}
	
	@Override
	public ProjectDTO getProject(Long id) {
		ProjectEntity project = projectRepository.findById(id)
				.orElseThrow(() -> new ProjectNotFound("Projet non trouvé pour l'ID : " + id));
		return projectMapper.toDTO(project);
	}
	
}