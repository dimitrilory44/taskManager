package com.dim.taskmanager.project.service;

import com.dim.taskmanager.project.response.input.PatchProjectRequest;
import com.dim.taskmanager.project.response.input.ProjectRequest;
import com.dim.taskmanager.project.response.input.UpdateProjectRequest;
import com.dim.taskmanager.project.response.output.ProjectDTO;

public interface ProjectService {
	ProjectDTO createProject(Long userId, ProjectRequest request);
	ProjectDTO getProject(Long id);
	ProjectDTO updateProject(Long projectId, UpdateProjectRequest request);
	ProjectDTO patchProject(Long projectId, PatchProjectRequest request);
	void deleteProject(Long id);
}