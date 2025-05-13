package com.dim.taskmanager.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dim.taskmanager.project.response.input.ProjectRequest;
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
	@Operation(summary = "project create", description = "Crée un nouveau projet")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Creation du nouveau projet reussie",
				content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
			@ApiResponse(responseCode = "500", description = "Erreur technique",
            content = @Content(schema = @Schema(implementation = GlobalResponseError.class)))
	})
	public ResponseEntity<GlobalResponse<ProjectDTO>> createTask(@Valid @RequestBody ProjectRequest projectRequest) {
		log.info("Tentative de création du projet : {}", projectRequest.toString());
		ProjectDTO newProject = projectService.createProject(projectRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(new GlobalResponse<>("Projet crée avec succès", newProject));
	}
	
}
