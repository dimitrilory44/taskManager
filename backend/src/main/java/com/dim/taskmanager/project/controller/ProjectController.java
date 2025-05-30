package com.dim.taskmanager.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dim.taskmanager.auth.CustomUserDetails;
import com.dim.taskmanager.project.response.input.ProjectRequest;
import com.dim.taskmanager.project.response.input.UpdateProjectRequest;
import com.dim.taskmanager.project.response.output.ProjectDTO;
import com.dim.taskmanager.project.service.ProjectService;
import com.dim.taskmanager.response.output.GlobalResponse;
import com.dim.taskmanager.response.output.GlobalResponseError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/${api.prefix}/${api.version}/project")
@RequiredArgsConstructor
@Tag(name = "Gestion des projets", description = "Endpoints for project management")
public class ProjectController {

	private final ProjectService projectService;
	
	@PostMapping("")
	@Operation(summary = "Create Project", description = "Crée un nouveau projet")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Creation du nouveau projet reussie",
				content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
			@ApiResponse(responseCode = "500", description = "Erreur technique",
            content = @Content(schema = @Schema(implementation = GlobalResponseError.class)))
	})
	public ResponseEntity<GlobalResponse<ProjectDTO>> createTask(
			@AuthenticationPrincipal CustomUserDetails userDetails, 
			@Valid @RequestBody ProjectRequest projectRequest) {
		log.info("Tentative de création du projet : {}", projectRequest.toString());
		ProjectDTO newProject = projectService.createProject(userDetails.getId(), projectRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(
			new GlobalResponse<>("Projet crée avec succès", newProject)
		);
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Update Project", description = "Modifie le projet")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Modification du projet avec succès",
				content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
			@ApiResponse(responseCode = "500", description = "Erreur technique",
            	content = @Content(schema = @Schema(implementation = GlobalResponseError.class)))
	})
	public ResponseEntity<GlobalResponse<ProjectDTO>> updateProjet(@PathVariable("id") Long projectId, @Valid @RequestBody UpdateProjectRequest updateProjectDTO) {
		log.info("Tentative de modification du projet ID {}", projectId);
		ProjectDTO projectUpdate = projectService.updateProject(projectId, updateProjectDTO);
		return ResponseEntity.status(HttpStatus.OK).body(
			new GlobalResponse<>("Projet modifé avec succès", projectUpdate)
		);
	}
	
}
