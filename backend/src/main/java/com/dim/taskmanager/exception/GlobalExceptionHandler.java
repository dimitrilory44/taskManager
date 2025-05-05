package com.dim.taskmanager.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.dim.taskmanager.config.ErrorMessages;
import com.dim.taskmanager.exception.error.*;

import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * Gestionnaire global des exceptions pour l'ensemble des contrôleurs REST.
 * 
 * Cette classe intercepte les exceptions personnalisées et renvoie une réponse HTTP
 * structurée et cohérente, afin d'améliorer l'expérience client et faciliter le débogage.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Intercepte toutes les exceptions de type AuthException (scellée avec ses sous-types comme EmailAlreadyUsedException...).
	 * Cela permet de mutualiser le traitement d'exceptions métier liées à l'authentification/autorisation.
	 *
	 * Chaque sous-classe d'AuthException doit fournir un HttpStatus spécifique via getHttpStatus().
	 */
	@ExceptionHandler(AuthException.class)
	public ResponseEntity<Object> handleAuthException(AuthException ex) {
		return buildResponse(new AuthError(ex));
	}

	/**
	 * Gère les erreurs liées à une requête invalide (par exemple, un paramètre manquant ou mal formaté).
	 *
	 * @param ex l'exception levée
	 * @return une réponse HTTP 400 (Bad Request) contenant les détails de l'erreur
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleBadRequest(IllegalArgumentException ex) {
		return buildResponse(new GenericError(HttpStatus.BAD_REQUEST, ex.getMessage(), ErrorMessages.get("illegal.error")));
	}

	/**
	 * Gère l'exception {@link MethodArgumentTypeMismatchException} lorsqu'un paramètre d'URL ou de type d'entrée est incorrect.
	 *
	 * @param ex l'exception levée
	 * @return une réponse HTTP 400 (Bad Request) contenant les détails de l'erreur
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		return buildResponse(new TypeMismatchError(ex));
	}

	/**
	 * Gère les erreurs de validation liées aux annotations de type @Valid dans les DTOs.
	 * 
	 * Regroupe les erreurs de champ sous une clé "fieldErrors" dans la réponse JSON.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {    
		Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (e1, e2) -> e1));
		return buildResponse(new ValidationError(fieldErrors));
	}

	/**
	 * Gère toutes les exceptions générales non interceptées par des méthodes spécifiques.
	 * Utilisé comme un mécanisme de "catch-all" pour éviter l'exposition d'erreurs inattendues.
	 *
	 * @param ex l'exception levée
	 * @return une réponse HTTP 500 (Internal Server Error) contenant les détails de l'erreur
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGeneric(Exception ex) {
		return buildResponse(new GenericError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ErrorMessages.get("internal.error")));
	}

	/**
	 * Gère l'exception {@link TechnicalException} pour les erreurs techniques.
	 *
	 * @param ex l'exception levée
	 * @return une réponse HTTP 500 (Internal Server Error) contenant les détails de l'erreur
	 */
	@ExceptionHandler(TechnicalException.class)
	public ResponseEntity<Object> handleTechnicalException(TechnicalException ex) {
		return buildResponse(new TechnicalError(ex));
	}

	/**
	 * Méthode utilitaire utilisée par tous les gestionnaires pour construire une réponse JSON structurée.
	 *
	 * @param error       La class ApiError avec les spécificité de cette error à paramétrer
	 *
	 * @return             Une réponse HTTP complète contenant les informations d'erreur.
	 */
	private ResponseEntity<Object> buildResponse(ApiError error) {
		Map<String, Object> bodyError = new HashMap<>();
		bodyError.put("timestamp", LocalDateTime.now());
		bodyError.put("status", error.getStatus().value());
		bodyError.put("message", error.getMessage());
		bodyError.put("error", error.getError());
		if (error.getDetails() != null) bodyError.putAll(error.getDetails());
		return new ResponseEntity<>(bodyError, error.getStatus());
	}
}