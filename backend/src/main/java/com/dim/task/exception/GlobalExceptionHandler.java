package com.dim.task.exception;

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
	 * Gère l'exception {@link EmailAlreadyUsedException} lorsque l'email est déjà enregistré.
	 *
	 * @param ex l'exception levée contenant le message d'erreur
	 * @return une réponse HTTP 400 (Bad Request) contenant les détails de l'erreur
	 */
	@ExceptionHandler(EmailAlreadyUsedException.class)
	public ResponseEntity<Object> handleEmailAlreadyUsed(EmailAlreadyUsedException ex) {
		return buildResponse(HttpStatus.CONFLICT, "Le compte existe déjà avec l'email fourni", ex.getMessage(), null);
	}

	/**
	 * Gère l'exception {@link UserNameAlreadyUsedException} lorsque le userName est déjà enregistré.
	 *
	 * @param ex l'exception levée contenant le message d'erreur
	 * @return une réponse HTTP 400 (Bad Request) contenant les détails de l'erreur
	 */
	@ExceptionHandler(UserNameAlreadyUsedException.class)
	public ResponseEntity<Object> handleUserNameAlreadyUsed(UserNameAlreadyUsedException ex) {
		return buildResponse(HttpStatus.CONFLICT, "Le compte existe déjà avec le username fourni", ex.getMessage(), null);
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
	 * Gère les exceptions d'authentification (par exemple, un utilisateur non authentifié).
	 *
	 * @param ex l'exception levée
	 * @return une réponse HTTP 401 (Unauthorized) contenant les détails de l'erreur
	 */
	@ExceptionHandler(UnAuthorizedException.class)
	public ResponseEntity<Object> handleUnauthorized(UnAuthorizedException ex) {
		return buildResponse(HttpStatus.UNAUTHORIZED, "Il faut pour cela être identifié pour y avoir accès", ex.getMessage(), null);
	}

	/**
	 * Gère l'exception {@link InvalidCredentialsException} lorsque les informations d'identification sont incorrectes.
	 *
	 * @param ex l'exception levée
	 * @return une réponse HTTP 401 (Unauthorized) contenant les détails de l'erreur
	 */
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<Object> handleInvalidCredentials(InvalidCredentialsException ex) {
		return buildResponse(HttpStatus.UNAUTHORIZED, "Le mot de passe ne correspond pas à celui existant en base", ex.getMessage(), null);
	}

	/**
	 * Gère les erreurs d'accès interdit (l'utilisateur n'a pas les autorisations nécessaires).
	 *
	 * @param ex l'exception levée
	 * @return une réponse HTTP 403 (Forbidden) contenant les détails de l'erreur
	 */
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleForbidden(AccessDeniedException ex) {
		return buildResponse(HttpStatus.FORBIDDEN, "Vous n'avez pas accès à la ressource", ex.getMessage(), null);
	}

	/**
	 * Gère l'exception {@link UserNotFoundException} lorsqu'aucun utilisateur n'est trouvé.
	 *
	 * @param ex l'exception levée contenant le message d'erreur
	 * @return une réponse HTTP 404 (Not Found) contenant les détails de l'erreur
	 */
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex) {
		return buildResponse(HttpStatus.NOT_FOUND, "Aucun compte ne correspond à l'email fourni.", ex.getMessage(), null);
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
	 * Construire une réponse structurée pour l'API avec les détails de l'erreur.
	 *
	 * @param status le code HTTP associé à l'erreur
	 * @param error le message d'erreur global
	 * @param message le message détaillé sur l'erreur
	 * @param fieldErrors des erreurs de validation par champ (peut être null)
	 * @return une réponse HTTP contenant les détails de l'erreur
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