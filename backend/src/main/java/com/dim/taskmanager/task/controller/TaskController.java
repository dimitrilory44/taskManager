package com.dim.taskmanager.task.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dim.taskmanager.response.output.GlobalResponse;
import com.dim.taskmanager.response.output.GlobalResponseError;
import com.dim.taskmanager.response.output.PaginatedResponse;
import com.dim.taskmanager.task.response.input.PatchTaskRequest;
import com.dim.taskmanager.task.response.input.TaskRequest;
import com.dim.taskmanager.task.response.input.UpdateTaskRequest;
import com.dim.taskmanager.task.response.output.TaskDTO;
import com.dim.taskmanager.task.service.TaskService;

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
@RequestMapping("/${api.prefix}/${api.version}/task")
@RequiredArgsConstructor
@Tag(name = "Gestion des taches", description = "Endpoints for task management")
public class TaskController {

	private final TaskService taskService;
	
	@PostMapping("/{projectId}")
	@Operation(summary = "Task create", description = "Crée une nouvelle tâche")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Creation de la nouvelle tâche reussie",
				content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
			@ApiResponse(responseCode = "500", description = "Erreur technique",
            content = @Content(schema = @Schema(implementation = GlobalResponseError.class)))
	})
	public ResponseEntity<GlobalResponse<TaskDTO>> createTask(
			@PathVariable("projectId") Long idProject,
			@Valid @RequestBody TaskRequest taskRequest
		) {
		log.info("Tentative de création de la tache : {}", taskRequest);
		TaskDTO newTask = taskService.createTask(idProject, taskRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(
			new GlobalResponse<>("Tache crée avec succès", newTask)
		);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Get Task", description = "Affiche la tâche")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Affichage de la tache reussie",
					content = @Content(schema = @Schema(implementation = TaskDTO.class))),
			@ApiResponse(responseCode = "500", description = "Erreur technique",
			content = @Content(schema = @Schema(implementation = GlobalResponseError.class)))
	})
	public ResponseEntity<TaskDTO> getTask(@PathVariable("id") Long taskId) {
		log.info("Recuperation de la tache avec l'ID {}", taskId);
		TaskDTO task = taskService.getTask(taskId);
		return ResponseEntity.ok(task);
	}
	
	@GetMapping("")
    @Operation(summary = "Task List", description = "Récupère la liste des taches")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Récupération de la liste de tache réussie",
            content = @Content(schema = @Schema(implementation = TaskDTO.class))),
        @ApiResponse(responseCode = "500", description = "Erreur technique",
            content = @Content(schema = @Schema(implementation = GlobalResponseError.class)))
    })
    public ResponseEntity<PaginatedResponse<TaskDTO>> taskList(Pageable pageable) {
        log.info("Tentative de récupération de la liste de tache");
        Page<TaskDTO> taskPage = taskService.getTasks(pageable);
        return ResponseEntity.ok(new PaginatedResponse<>(
        	taskPage.getContent(),
        	taskPage.getNumber(),
        	taskPage.getSize(),
        	taskPage.getTotalElements(),
        	taskPage.getTotalPages()
        ));
    }
	
	@GetMapping("/{projectId}")
    @Operation(summary = "Task List By project", description = "Récupère la liste des taches d'un projet existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Récupération de la liste de tache réussie",
            content = @Content(schema = @Schema(implementation = TaskDTO.class))),
        @ApiResponse(responseCode = "500", description = "Erreur technique",
            content = @Content(schema = @Schema(implementation = GlobalResponseError.class)))
    })
    public ResponseEntity<List<TaskDTO>> taskListByProject(@PathVariable("projectId") Long idProject) {
        log.info("Tentative de récupération de la liste de taches");
        List<TaskDTO> taskList = taskService.getTasksByProject(idProject);
        return ResponseEntity.ok(taskList);
    }
	
	@PutMapping("/{taskId}/project/{projectId}")
	@Operation(summary = "Update Task", description = "Modifie la tache")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Modification de la tache avec succès",
				content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
			@ApiResponse(responseCode = "500", description = "Erreur technique",
            	content = @Content(schema = @Schema(implementation = GlobalResponseError.class)))
	})
	public ResponseEntity<GlobalResponse<TaskDTO>> updateTask(
			@PathVariable("taskId") Long idTask, 
			@PathVariable("projectId") Long idProject,
			@Valid @RequestBody UpdateTaskRequest updateTaskDTO) {
		TaskDTO task = taskService.getTask(idTask);
		log.info("Tentative de modification de la tache : {}", task);
		TaskDTO taskUpdate = taskService.updateTask(idTask, idProject, updateTaskDTO);
		return ResponseEntity.status(HttpStatus.OK).body(
			new GlobalResponse<>("Tâche modifée avec succès", taskUpdate)
		);
	}
	
	@PatchMapping("/{taskId}/project/{projectId}")
	@Operation(summary = "Patch Task", description = "Modifie une donnée de la tache")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Modification de la donnée de la tache avec succès",
				content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
			@ApiResponse(responseCode = "500", description = "Erreur technique",
				content = @Content(schema = @Schema(implementation = GlobalResponseError.class)))
	})
	public ResponseEntity<GlobalResponse<TaskDTO>> patchTask(
			@PathVariable("taskId") Long idTask, 
			@PathVariable("projectId") Long idProject, 
			@RequestBody PatchTaskRequest patchTaskDTO) {
		TaskDTO task = taskService.getTask(idTask);
		log.info("Tentative de modification de l'information de la tache {}", task);
		TaskDTO taskPatch = taskService.patchTask(idTask, idProject, patchTaskDTO);
		return ResponseEntity.status(HttpStatus.OK).body(
			new GlobalResponse<>("Tâche patchée avec succès", taskPatch)
		);
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete Task", description = "Supprime la tache")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Suppression de la tâche avec succès",
				content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
			@ApiResponse(responseCode = "500", description = "Erreur technique",
				content = @Content(schema = @Schema(implementation = GlobalResponseError.class)))
	})
	public ResponseEntity<GlobalResponse<TaskDTO>> deleteTask(@PathVariable("id") Long taskId) {
		TaskDTO task = taskService.getTask(taskId);
		log.info("Suppression de la tâche {}", task);
		taskService.deleteTask(taskId);
		return ResponseEntity.status(HttpStatus.OK).body(
			new GlobalResponse<>("Tache supprimée avec succès", task)
		);
	}
}