package com.dim.taskmanager.auth.controller;

import org.springframework.web.bind.annotation.*;

import com.dim.taskmanager.auth.response.input.LoginRequest;
import com.dim.taskmanager.auth.response.input.RegisterRequest;
import com.dim.taskmanager.auth.response.output.AuthDTO;
import com.dim.taskmanager.auth.response.output.JwtResponse;
import com.dim.taskmanager.auth.service.AuthService;
import com.dim.taskmanager.response.output.GlobalResponseError;
import com.dim.taskmanager.response.output.GlobalResponse;
import com.dim.taskmanager.response.output.ValidationResponseError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/${api.prefix}/${api.version}/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authentifie l'utilisateur et génère un token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Authentification réussie",
            content = @Content(schema = @Schema(implementation = JwtResponse.class))),
        @ApiResponse(responseCode = "400", description = "Requête invalide",
        	content = @Content(schema = @Schema(implementation = ValidationResponseError.class))),
        @ApiResponse(responseCode = "404", description = "Mot de passe incorrect",
            content = @Content(schema = @Schema(implementation = GlobalResponseError.class))),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
            content = @Content(schema = @Schema(implementation = GlobalResponseError.class))),
        @ApiResponse(responseCode = "500", description = "Erreur technique",
            content = @Content(schema = @Schema(implementation = GlobalResponseError.class)))
    })
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Tentative de connexion pour l'utilisateur : {}", loginRequest.email());
        String token = authService.login(loginRequest.email(), loginRequest.password());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    @Operation(summary = "User registration", description = "Crée un nouvel utilisateur et retourne ses infos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Création réussie",
            content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
        @ApiResponse(responseCode = "400", description = "Requête invalide",
            content = @Content(schema = @Schema(implementation = ValidationResponseError.class))),
        @ApiResponse(responseCode = "409", description = "Champ déjà utilisé",
            content = @Content(schema = @Schema(implementation = GlobalResponseError.class))),
        @ApiResponse(responseCode = "500", description = "Erreur technique",
            content = @Content(schema = @Schema(implementation = GlobalResponseError.class)))
    })
    public ResponseEntity<GlobalResponse<AuthDTO>> register(@Valid @RequestBody RegisterRequest register) {
        log.info("Tentative de création de l'utilisateur avec l'email : {}", register.email());
        AuthDTO user = authService.register(register);
        return ResponseEntity.status(HttpStatus.CREATED).body(new GlobalResponse<>("Utilisateur inscrit avec succès", user));
    }
    
}