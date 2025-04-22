package com.dim.task.controller;

import org.springframework.web.bind.annotation.*;

import com.dim.task.response.input.LoginRequest;
import com.dim.task.response.input.RegisterRequest;
import com.dim.task.response.output.TaskResponse;
import com.dim.task.response.output.GlobalResponseError;
import com.dim.task.response.output.JwtResponse;
import com.dim.task.response.output.UserDTO;
import com.dim.task.service.AuthService;
import com.dim.task.service.impl.AuthServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@Slf4j
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	@Operation(summary = "User login", description = "Authentifie l'utilisateur et génère un token")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Authentification réussie",
					content = @Content(schema = @Schema(implementation = JwtResponse.class))),
			@ApiResponse(responseCode = "400", description = "Mot de passe incorrect",
			content = @Content(schema = @Schema(implementation = GlobalResponseError.class))),
			@ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
			content = @Content(schema = @Schema(implementation = GlobalResponseError.class))),
			@ApiResponse(responseCode = "500", description = "Erreur technique",
			content = @Content(schema = @Schema(implementation = GlobalResponseError.class)))
	})
	public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
		log.info("Tentative de connexion pour l'utilisateur : {}", loginRequest.getEmail());
		String token = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "User registration", description = "crée un nouvel utilisateur et retourne ses infos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Création réussie",
					content = @Content(schema = @Schema(implementation = TaskResponse.class))),
			@ApiResponse(responseCode = "400", description = "Requête invalide",
			content = @Content(schema = @Schema(implementation = GlobalResponseError.class))),
			@ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
			content = @Content(schema = @Schema(implementation = GlobalResponseError.class))),
			@ApiResponse(responseCode = "409", description = "Email déjà utilisé",
			content = @Content(schema = @Schema(implementation = GlobalResponseError.class))),
			@ApiResponse(responseCode = "500", description = "Erreur technique",
			content = @Content(schema = @Schema(implementation = GlobalResponseError.class)))
	})
	public ResponseEntity<TaskResponse<UserDTO>> register(@Valid @RequestBody RegisterRequest registerRequest) {
		log.info("Tentative de création de l'utilisateur avec l'email : {}", registerRequest.getEmail());
		UserDTO user = authService.register(registerRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(new TaskResponse<>(true, "Utilisateur inscrit avec succès", user));
	}

}