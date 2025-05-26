package com.dim.taskmanager.project.service.impl;

import org.springframework.stereotype.Service;

import com.dim.taskmanager.project.entity.ProjectEntity;
import com.dim.taskmanager.project.exception.ProjectNotFound;
import com.dim.taskmanager.project.mapper.ProjectMapper;
import com.dim.taskmanager.project.repository.ProjectRepository;
import com.dim.taskmanager.project.response.input.PatchProjectRequest;
import com.dim.taskmanager.project.response.input.ProjectRequest;
import com.dim.taskmanager.project.response.input.UpdateProjectRequest;
import com.dim.taskmanager.project.response.output.ProjectDTO;
import com.dim.taskmanager.project.service.ProjectService;
import com.dim.taskmanager.user.mapper.UserMapper;
import com.dim.taskmanager.user.response.output.UserDTO;
import com.dim.taskmanager.user.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository projectRepository;
	
	private final ProjectMapper projectMapper;
	private final UserMapper userMapper;
	
	private final UserService userService;
	
	@Override
	public ProjectDTO createProject(Long userId, ProjectRequest request) {
		ProjectEntity project = new ProjectEntity();
		project.setName(request.name());
		project.setDescription(request.description());
		
		UserDTO userDTO = userService.getUser(userId);
		project.setUser(userMapper.toEntity(userDTO));
				
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
	public ProjectDTO updateProject(Long id, UpdateProjectRequest request) {
		log.info("Tentative de modification d'un projet avec l'ID : {}", id);
		
		ProjectEntity project = projectMapper.toEntity(this.getProject(id));
		project.setName(request.name());
		project.setDescription(request.description());
		
		ProjectEntity updatedProject = projectRepository.save(project);
		
		return projectMapper.toDTO(updatedProject);
		
	}

	@Override
	public ProjectDTO patchProject(Long id, PatchProjectRequest request) {
		log.info("Tentative de patch d'un projet avec l'ID : {}", id);
		
		ProjectEntity project = projectMapper.toEntity(this.getProject(id));
		request.name().ifPresent(project::setName);
		request.description().ifPresent(project::setDescription);
		
		ProjectEntity patchedProject = projectRepository.save(project);
		log.info("Projet patchée avec succès : ID {}", patchedProject.getId());
		
		return projectMapper.toDTO(patchedProject);
		
	}
	
	@Override
	public void deleteProject(Long id) {
		log.info("Tentative de suppression d'un projet avec l'ID : {}", id);
		ProjectEntity project = projectMapper.toEntity(this.getProject(id));
		
		projectRepository.delete(project);
		
	}

}