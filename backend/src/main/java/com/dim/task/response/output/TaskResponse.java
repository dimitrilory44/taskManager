package com.dim.task.response.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Réponse d'une tâche avec un message et des données")
public class TaskResponse<T> {
	
	@Schema(description = "Message de la réponse", example = "Utilisateur supprimé avec succès")
	private String message;
	
	@Schema(description = "Données associées à la réponse")
	private T data;
}
