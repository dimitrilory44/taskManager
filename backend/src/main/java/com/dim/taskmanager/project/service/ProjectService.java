package com.dim.taskmanager.project.service;

import com.dim.taskmanager.project.response.input.ProjectRequest;
import com.dim.taskmanager.project.response.output.ProjectDTO;

public interface ProjectService {
	ProjectDTO createProject(ProjectRequest request);
	ProjectDTO getProject(Long id);

}