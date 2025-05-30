package com.dim.taskmanager.user.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dim.taskmanager.response.output.GlobalResponseError;
import com.dim.taskmanager.response.output.PaginatedResponse;
import com.dim.taskmanager.response.output.GlobalResponse;
import com.dim.taskmanager.user.response.output.UserDTO;
import com.dim.taskmanager.user.service.UserService;

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

	@GetMapping("")
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
	public ResponseEntity<PaginatedResponse<UserDTO>> usersList(Pageable pageable) {
		log.info("Tentative de récupération de la liste des utilisateurs");
		Page<UserDTO> userPage = userService.getAllUsers(pageable);
		return ResponseEntity.ok(new PaginatedResponse<>(
			userPage.getContent(), 
			userPage.getNumber(), 
			userPage.getSize(), 
			userPage.getTotalElements(), 
			userPage.getTotalPages()
		));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete User", description = "Supprime un utilisateur en fonction de son id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Utilisateur supprimé avec succès",
					content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
			@ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponseError.class))),
			@ApiResponse(responseCode = "500", description = "Erreur technique",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponseError.class)))
	})
	public ResponseEntity<GlobalResponse<UserDTO>> deleteUser(@PathVariable Long id) {
		UserDTO user = userService.getUser(id);
		log.info("Tentative de suppression d'un utilisateur {}", user);
		userService.deleteUser(id);
		return ResponseEntity.status(HttpStatus.OK).body(new GlobalResponse<>("Utilisateur supprimé avec succès", user));
	}

}