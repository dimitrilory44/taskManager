package com.dim.taskmanager.project.service;

import com.dim.taskmanager.project.response.input.ProjectRequest;
import com.dim.taskmanager.project.response.input.UpdateProjectRequest;
import com.dim.taskmanager.project.response.output.ProjectDTO;

public interface ProjectService {
	ProjectDTO createProject(Long userId, ProjectRequest request);
	ProjectDTO getProject(Long id);
	ProjectDTO updateProject(Long taskId, UpdateProjectRequest request);
}