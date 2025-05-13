package com.dim.taskmanager.project.service.impl;

import org.springframework.stereotype.Service;

import com.dim.taskmanager.project.entity.ProjectEntity;
import com.dim.taskmanager.project.exception.ProjectNotFound;
import com.dim.taskmanager.project.mapper.ProjectMapper;
import com.dim.taskmanager.project.repository.ProjectRepository;
import com.dim.taskmanager.project.response.input.ProjectRequest;
import com.dim.taskmanager.project.response.input.UpdateProjectRequest;
import com.dim.taskmanager.project.response.output.ProjectDTO;
import com.dim.taskmanager.project.service.ProjectService;
import com.dim.taskmanager.user.entity.UserEntity;
import com.dim.taskmanager.user.exception.UserNotFoundException;
import com.dim.taskmanager.user.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository projectRepository;
	private final UserRepository userRepository;
	
	private final ProjectMapper projectMapper;
	
	@Override
	public ProjectDTO createProject(ProjectRequest request) {
		UserEntity user = userRepository.findById(request.userId())
				.orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé pour l'ID : " + request.userId()));
		
		ProjectEntity project = new ProjectEntity();
		project.setName(request.name());
		project.setDescription(request.description());
		project.setUser(user);
				
		return projectMapper.toDTO(projectRepository.save(project));
	}
	
	@Override
	public ProjectDTO getProject(Long id) {
		log.info("Tentative de récupération d'un projet avec l'ID : {}", id);
		ProjectEntity project = projectRepository.findById(id)
				.orElseThrow(() -> new ProjectNotFound("Projet non trouvé pour l'ID : " + id));
		return projectMapper.toDTO(project);
	}

	@Override
	public ProjectDTO updateProject(Long taskId, UpdateProjectRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}