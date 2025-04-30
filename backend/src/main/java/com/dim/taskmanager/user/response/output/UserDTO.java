package com.dim.taskmanager.user.response.output;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO représentant un utilisateur")
public record UserDTO(
		@Schema(description = "L'ID de l'utilisateur")
		Long id,

		@Schema(description = "Le nom de l'utilisateur")	
		String name,

		String firstName,

		String userName,

		@Schema(description = "L'email de l'utilisateur")
		String email

){}