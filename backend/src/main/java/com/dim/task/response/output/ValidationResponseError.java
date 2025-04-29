package com.dim.task.response.output;

import java.time.LocalDateTime;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Réponse d'erreur standard.")
public record ValidationResponseError(
    
    @Schema(description = "Message d'erreur global (ex: Erreur de validation)", 
            example = "Erreur de validation")
    String error,
    
    @Schema(description = "Liste des erreurs de validation par champ", 
            example = "{\"email\": \"L'email est invalide\"}")
    Map<String, String> fieldErrors,
    
    @Schema(description = "Message d'erreur plus détaillé", 
            example = "Des champs requis sont manquants ou invalides.")
    String message,
    
    @Schema(description = "Horodatage de l'erreur")
    LocalDateTime timestamp,
    
    @Schema(description = "Code HTTP associé à l'erreur", example = "400")
    int status
    
) {}