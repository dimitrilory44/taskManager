package com.dim.taskmanager.exception.error;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dim.taskmanager.utils.ErrorUtils;

public interface ApiError {

	HttpStatus status();
	String message();
	String error();
	
	default Map<String, ?> details() {
		return null;
	}
	
	/**
	 * Méthode utilitaire utilisée par tous les gestionnaires pour construire une réponse JSON structurée.
	 *
	 * @param error       La class ApiError avec les spécificité de cette error à paramétrer
	 *
	 * @return             Une réponse HTTP complète contenant les informations d'erreur.
	 */
	default ResponseEntity<Object> buildResponse() {
		Map<String, Object> bodyError = ErrorUtils.buildError(status().value(), message(), error());
		if (details() != null) {bodyError.putAll(details());}
        return new ResponseEntity<>(bodyError, status());
    }
	
}