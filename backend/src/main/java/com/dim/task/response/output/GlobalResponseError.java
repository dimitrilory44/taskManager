package com.dim.task.response.output;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Réponse d'erreur standard.")
public record GlobalResponseError(
    
    @Schema(description = "Message d'erreur global (ex: Utilisateur non trouvé)", 
            example = "Utilisateur non trouvé")
    String error,
    
    @Schema(description = "Message d'erreur plus détaillé", 
            example = "Aucun compte ne correspond à l'email fourni.")
    String message,
    
    @Schema(description = "Horodatage de l'erreur")
    LocalDateTime timestamp,
    
    @Schema(description = "Code HTTP associé à l'erreur", example = "404")
    int status
) {}
