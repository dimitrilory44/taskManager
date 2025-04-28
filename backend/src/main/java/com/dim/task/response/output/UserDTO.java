package com.dim.task.response.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO représentant un utilisateur")
public class UserDTO {
	@Schema(description = "L'ID de l'utilisateur")
	private Long id;
	
	@Schema(description = "Le nom de l'utilisateur")
	private String name;
	private String firstName;
	private String userName;
	
	@Schema(description = "L'email de l'utilisateur")
	private String email;
}
