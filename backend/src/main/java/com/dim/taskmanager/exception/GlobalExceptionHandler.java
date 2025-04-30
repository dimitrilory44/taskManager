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

import jakarta.annotation.Nullable;

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
	    return buildResponse(ex.getHttpStatus(), "Erreur d'authentification", ex.getMessage(), null);
	}

	/**
	 * Gère les erreurs liées à une requête invalide (par exemple, un paramètre manquant ou mal formaté).
	 *
	 * @param ex l'exception levée
	 * @return une réponse HTTP 400 (Bad Request) contenant les détails de l'erreur
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleBadRequest(IllegalArgumentException ex) {
		return buildResponse(HttpStatus.BAD_REQUEST, "Requête invalide", ex.getMessage(), null);
	}

	/**
	 * Gère l'exception {@link MethodArgumentTypeMismatchException} lorsqu'un paramètre d'URL ou de type d'entrée est incorrect.
	 *
	 * @param ex l'exception levée
	 * @return une réponse HTTP 400 (Bad Request) contenant les détails de l'erreur
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		return buildResponse(HttpStatus.BAD_REQUEST, "Type de paramètre incorrect", ex.getMessage(), null);
	}

	/**
     * Gère les erreurs de validation liées aux annotations de type @Valid dans les DTOs.
     * 
     * Regroupe les erreurs de champ sous une clé "fieldErrors" dans la réponse JSON.
     */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {    
		Map<String, Object> fieldErrors = Map.of("fieldErrors", ex.getBindingResult().getFieldErrors().stream()
				.collect(Collectors.toMap(
						FieldError::getField, 
						FieldError::getDefaultMessage,
						(existing, replacement) -> existing  // en cas de doublon, garde le premier
						)));

		return buildResponse(HttpStatus.BAD_REQUEST, "Erreur de validation", "Des champs requis sont manquants ou invalides.", fieldErrors);
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
		return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Un problème interne est survenu. Veuillez réessayer plus tard.", ex.getMessage(), null);
	}

	/**
	 * Gère l'exception {@link TechnicalException} pour les erreurs techniques.
	 *
	 * @param ex l'exception levée
	 * @return une réponse HTTP 500 (Internal Server Error) contenant les détails de l'erreur
	 */
	@ExceptionHandler(TechnicalException.class)
	public ResponseEntity<Object> handleTechnicalException(TechnicalException ex) {
		return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur technique est survenue. Veuillez réessayer plus tard.", ex.getMessage(), null);
	}

	/**
     * Méthode utilitaire utilisée par tous les gestionnaires pour construire une réponse JSON structurée.
     *
     * @param status       Le code HTTP à retourner.
     * @param error        Le résumé de l’erreur (ex: "Erreur de validation").
     * @param message      Le message détaillé (souvent issu de l'exception).
     * @param fieldErrors  Une map contenant les erreurs de validation (peut être null).
     *
     * @return             Une réponse HTTP complète contenant les informations d'erreur.
     */
	private ResponseEntity<Object> buildResponse(HttpStatus status, String error, String message, @Nullable Map<String, ?> fieldErrors) {
		Map<String, Object> bodyError = new HashMap<>();
		bodyError.put("timestamp", LocalDateTime.now());
		bodyError.put("status", status.value());
		bodyError.put("message", message);
		bodyError.put("error", error);
		if (fieldErrors != null) bodyError.putAll(fieldErrors);
		return new ResponseEntity<>(bodyError, status);
	}
}