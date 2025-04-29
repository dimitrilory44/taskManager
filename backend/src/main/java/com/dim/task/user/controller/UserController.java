package com.dim.task.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dim.task.response.output.GlobalResponseError;
import com.dim.task.response.output.TaskResponse;
import com.dim.task.user.response.output.UserDTO;
import com.dim.task.user.service.UserService;

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
@RequestMapping("/${api.prefix}/${api.version}/admin/users")
@RequiredArgsConstructor
@Tag(name = "Gestion des utilisateurs", description = "Endpoints for users management")
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/")
    @Operation(summary = "User List", description = "Récupère la liste des utilisateurs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Récuperation réussie",
            content = @Content(schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "401", description = "Mot de passe incorrect",
            content = @Content(schema = @Schema(implementation = GlobalResponseError.class))),
        @ApiResponse(responseCode = "401", description = "Authentication nécessaire",
        	content = @Content(schema = @Schema(implementation = GlobalResponseError.class))),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
            content = @Content(schema = @Schema(implementation = GlobalResponseError.class))),
        @ApiResponse(responseCode = "500", description = "Erreur technique",
            content = @Content(schema = @Schema(implementation = GlobalResponseError.class)))
    })
    public ResponseEntity<List<UserDTO>> taskList() {
        log.info("Tentative de récupération de la liste des utilisateurs");
        List<UserDTO> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete User", description = "Supprime un utilisateur en fonction de son id")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Utilisateur supprimé avec succès",
		    content = @Content(schema = @Schema(implementation = TaskResponse.class))),
	    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponseError.class))),
	    @ApiResponse(responseCode = "500", description = "Erreur technique",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponseError.class)))
	})
    public ResponseEntity<TaskResponse<UserDTO>> deleteUser(@PathVariable Long id) {
		UserDTO user = userService.getUserById(id);
        log.info("Tentative de suppression d'un utilisateur {}", user);
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(new TaskResponse<>("Utilisateur supprimé avec succès", user));
    }

}
