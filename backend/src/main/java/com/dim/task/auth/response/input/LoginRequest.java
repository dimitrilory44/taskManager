package com.dim.task.auth.response.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Login request payload")
public record LoginRequest (
	
	@NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
	@Schema(description = "User email address", example = "john.doe@example.com")
	String email,
	
	@NotBlank(message = "Le mot de passe est obligatoire")
	@Schema(description = "User password", example = "password123")
	String password
	
) {}