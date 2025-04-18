package com.dim.task.response.output;

import java.time.LocalDateTime;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Réponse d'erreur standard. Un seul des deux champs 'error' ou 'errors' est présent.")
public record GlobalResponseError(
	
	@Schema(description = "Message d'erreur global (ex: Utilisateur non trouvé)", example = "Utilisateur non trouvé")
	String error,
	
	@Schema(description = "Liste des erreurs de validation par champ", example = "{\"email\": \"L'email est invalide\"}")
    Map<String, String> errors,
    
    @Schema(description = "Horodatage de l'erreur")
	LocalDateTime timestamp,
	
	@Schema(description = "Code HTTP associé à l'erreur", example = "400")
	int status
) {}