package com.dim.taskmanager.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ErrorUtils {

	/**
     * Méthode utilitaire pour construire une erreur sous forme de Map.
     * Utilisée pour une réponse d'erreur simple en JSON.
     *
     * @param httpStatus Le code de statut HTTP (par exemple 404, 500, 403, etc.)
     * @param message    Le message d'erreur, qui sera affiché dans le corps de la réponse.
     * @param error      Le nom ou type de l'erreur, comme "Not Found" ou "Internal Server Error".
     * @return Une Map contenant la structure de l'erreur avec les informations suivantes :
     *         - timestamp (heure actuelle)
     *         - status (code de statut HTTP)
     *         - error (nom/type de l'erreur)
     *         - message (description de l'erreur)
     */
	public static Map<String, Object> buildError(int httpStatus, String message, String error) {
		Map<String, Object> errorBody = new HashMap<>();
		errorBody.put("timestamp", LocalDateTime.now());
		errorBody.put("status", httpStatus);
		errorBody.put("error", error);
		errorBody.put("message", message);
		return errorBody;
	}

	/**
     * Méthode utilitaire pour convertir une Map contenant des informations d'erreur en une chaîne JSON.
     *
     * @param errorBody La Map contenant les informations d'erreur (comme le timestamp, le status, etc.)
     * @return Une chaîne JSON représentant les informations d'erreur.
     */
	public static String convertJSONError(Map<String, Object> errorBody) {
		return errorBody.entrySet().stream()
				.map(e -> {
					// Formater le timestamp si la valeur est un LocalDateTime
					Object value = e.getValue();
					if (value instanceof LocalDateTime) {
						value = ((LocalDateTime) value).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
					}

					// Formater la clé et la valeur comme JSON (en ajoutant des guillemets pour les chaînes)
					return "\"%s\": %s".formatted(e.getKey(), value instanceof String ? "\"" + value + "\"" : value);
				})
				.collect(Collectors.joining(", ", "{", "}"));
	}

}