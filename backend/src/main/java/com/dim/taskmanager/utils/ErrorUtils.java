package com.dim.taskmanager.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.dim.taskmanager.response.output.GlobalResponseError;
import com.dim.taskmanager.response.output.ValidationResponseError;

public class ErrorUtils {

	/**
     * Construit un objet ValidationResponseError à partir des détails de validation fournis.
     *
     * @param details      Map des erreurs de validation, avec le nom du champ en clé et le message en valeur.
     * @param httpStatus   Le statut HTTP à retourner (ex : 400 BAD_REQUEST).
     * @param message      Le message global d'erreur à afficher.
     * @return             Un objet ValidationResponseError complet.
     */
	public static ValidationResponseError buildValidationError(Map<String, String> details, HttpStatus httpStatus, String message) {
		return new ValidationResponseError(details, message, LocalDateTime.now(), httpStatus.value());
	}
	
	/**
     * Construit un objet GlobalResponseError pour des erreurs générales (non liées à la validation).
     *
     * @param httpStatus   Le code de statut HTTP (ex : 404, 500).
     * @param message      Message d'erreur plus détaillé (ex : "Utilisateur non trouvé").
     * @param error        Libellé général de l'erreur (ex : "Not Found").
     * @return             Un objet GlobalResponseError contenant toutes les informations de l'erreur.
     */
	public static GlobalResponseError buildError(HttpStatus httpStatus, String message, String error) {
		return new GlobalResponseError(error, message, LocalDateTime.now(), httpStatus.value());
	}

	/**
     * Convertit un objet GlobalResponseError en une chaîne JSON manuellement.
     * À noter : normalement, Spring s'occupe déjà de cette sérialisation via Jackson.
     *
     * @param error    L'objet GlobalResponseError à convertir.
     * @return         Une chaîne JSON représentant l'objet.
     */
	public static String convertJSONError(GlobalResponseError error) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	    StringBuilder json = new StringBuilder("{");

	    json.append("\"error\": \"").append(error.error()).append("\", ");
	    json.append("\"message\": \"").append(error.message()).append("\", ");
	    json.append("\"timestamp\": \"").append(error.timestamp().format(formatter)).append("\", ");
	    json.append("\"status\": ").append(error.status());

	    json.append("}");

	    return json.toString();
	}

}