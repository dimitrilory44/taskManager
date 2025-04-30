package com.dim.taskmanager.auth.response.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "RegisterRequest", description = "Payload pour l'inscription d'un nouvel utilisateur")
public record RegisterRequest (

		@Schema(description = "Nom d'utilisateur unique", example = "dimitri92")
		String userName,

		@Schema(description = "Nom de famille", example = "Dupont")
		String name,

		@Schema(description = "Prénom", example = "Dimitri")
		String firstName,

		@NotBlank(message = "L'email est obligatoire")
		@Email(message = "L'email doit être valide")
		@Schema(description = "Adresse email de l'utilisateur", example = "dimitri@example.com")
		String email,

		@NotBlank(message = "Le mot de passe est obligatoire")
		@Schema(description = "Mot de passe en clair (sera hashé en backend)", example = "Motdepasse123!")
		String password

) {}