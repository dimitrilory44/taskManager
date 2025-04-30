package com.dim.taskmanager.task.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dim.taskmanager.response.output.GlobalResponseError;
import com.dim.taskmanager.task.response.output.TaskDTO;
import com.dim.taskmanager.task.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/${api.prefix}/${api.version}/task")
@RequiredArgsConstructor
@Tag(name = "Gestion des taches", description = "Endpoints for task management")
public class TaskController {

	private final TaskService taskService;
	
	@GetMapping("/")
    @Operation(summary = "Task List", description = "Récupère la liste des taches")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Récupération de la liste de tache réussie",
            content = @Content(schema = @Schema(implementation = TaskDTO.class))),
        @ApiResponse(responseCode = "401", description = "Mot de passe incorrect",
            content = @Content(schema = @Schema(implementation = GlobalResponseError.class))),
        @ApiResponse(responseCode = "401", description = "Authentication nécessaire",
        	content = @Content(schema = @Schema(implementation = GlobalResponseError.class))),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
            content = @Content(schema = @Schema(implementation = GlobalResponseError.class))),
        @ApiResponse(responseCode = "500", description = "Erreur technique",
            content = @Content(schema = @Schema(implementation = GlobalResponseError.class)))
    })
    public ResponseEntity<List<TaskDTO>> taskList() {
        log.info("Tentative de récupération de la liste de tache");
        List<TaskDTO> taskList = taskService.getAllTask();
        return ResponseEntity.ok(taskList);
    }
	
}
