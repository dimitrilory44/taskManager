package com.dim.task.response.output;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Réponse d'une tâche avec un message et des données")
public record TaskResponse<T> (
	
	@Schema(description = "Message de la réponse", example = "Utilisateur supprimé avec succès")
	String message,
	
	@Schema(description = "Données associées à la réponse")
	T data
	
) {}