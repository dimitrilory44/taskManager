package com.dim.taskmanager.exception.error;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dim.taskmanager.utils.ErrorUtils;

public interface ApiError {

	HttpStatus status();
	String message();
	String error();
	
	default Map<String, String> details() {
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
		Object errorBody = Optional.ofNullable(details())
				.<Object>map(fieldErrors -> ErrorUtils.buildValidationError(fieldErrors, status(), message()))
				.orElseGet(() -> ErrorUtils.buildError(status(), message(), error()));
        return new ResponseEntity<>(errorBody, status());
    }
	
}