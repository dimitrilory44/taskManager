package com.dim.task.response.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Login request payload")
public class LoginRequest {
	
	@NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
	@Schema(description = "User email address", example = "john.doe@example.com")
	private String email;
	
	@NotBlank(message = "Le mot de passe est obligatoire")
	@Schema(description = "User password", example = "password123")
	private String password;
}
