package com.dim.taskmanager.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.dim.taskmanager.auth.exception.AuthException;
import com.dim.taskmanager.auth.exception.error.AuthError;
import com.dim.taskmanager.exception.error.*;
import com.dim.taskmanager.response.output.GlobalResponseError;
import com.dim.taskmanager.response.output.ValidationResponseError;
import com.dim.taskmanager.user.exception.UserNotFoundException;
import com.dim.taskmanager.user.exception.error.UserError;
import com.fasterxml.jackson.core.JsonParseException;

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
	public ResponseEntity<GlobalResponseError> handleAuthException(AuthException ex) {
		AuthError error = new AuthError(ex);
		return error.buildResponse();
	}
	
	/**
	 * Gère les erreurs liées à la recherche de l'entité User (par exemple, l'utilisateur n'est pas trouvé dans la base de données).
	 *
	 * @param ex l'exception levée
	 * @return une réponse HTTP 400 (Bad Request) contenant les détails de l'erreur
	 */
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<GlobalResponseError> handleUserNotFoundException(UserNotFoundException ex) {
		UserError error = new UserError(ex);
		return error.buildResponse();
	}

	/**
	 * Gère les erreurs liées à une requête invalide (par exemple, un paramètre manquant ou mal formaté).
	 *
	 * @param ex l'exception levée
	 * @return une réponse HTTP 400 (Bad Request) contenant les détails de l'erreur
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<GlobalResponseError> handleBadRequest(IllegalArgumentException ex) {
		GenericError error = new GenericError(ex);
		return error.buildResponse();
	}

	/**
	 * Gère l'exception {@link MethodArgumentTypeMismatchException} lorsqu'un paramètre d'URL ou de type d'entrée est incorrect.
	 *
	 * @param ex l'exception levée
	 * @return une réponse HTTP 400 (Bad Request) contenant les détails de l'erreur
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<GlobalResponseError> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		TypeMismatchError error = new TypeMismatchError(ex);
		return error.buildResponse();
	}

	/**
	 * Gère les erreurs de validation liées aux annotations de type @Valid dans les DTOs.
	 * 
	 * @param ex l'exception levée
	 * @return une réponse HTTP 400 (Bad Request) contenant les détails de l'erreur
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationResponseError> handleValidationExceptions(MethodArgumentNotValidException ex) {    
		ValidationError error = new ValidationError(ex);
		return error.buildResponse();
	}
	
	/**
	 * Gère les erreurs de formatage de champs dans le cadre d'un Enum par exemple (status ou priority)
	 * 
	 * @param ex l'exception levée
	 * @return une réponse HTTP 400 (Bad Request) contenant les détails de l'erreur
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<GlobalResponseError> handleInvalidFormat(HttpMessageNotReadableException ex) {
		InvalidFormatError error = new InvalidFormatError(ex);
		return error.buildResponse();
	}
	
	/**
	 * Gère les erreurs d'écriture du JSON (exemple : title: "test" au lieu de "title": "test")
	 * 
	 * @param ex l'exception levée
	 * @return une réponse HTTP 400 (Bad Request) contenant les détails de l'erreur
	 */
	@ExceptionHandler(JsonParseException.class)
    public ResponseEntity<GlobalResponseError> handleJsonParse(JsonParseException ex) {
		JsonError error = new JsonError(ex);
		return error.buildResponse();
    }

	/**
	 * Gère toutes les exceptions générales non interceptées par des méthodes spécifiques.
	 * Utilisé comme un mécanisme de "catch-all" pour éviter l'exposition d'erreurs inattendues.
	 *
	 * @param ex l'exception levée
	 * @return une réponse HTTP 500 (Internal Server Error) contenant les détails de l'erreur
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<GlobalResponseError> handleGeneric(Exception ex) {
		GenericError error = new GenericError(ex);
		return error.buildResponse();
	}

	/**
	 * Gère l'exception {@link TechnicalException} pour les erreurs techniques.
	 *
	 * @param ex l'exception levée
	 * @return une réponse HTTP 500 (Internal Server Error) contenant les détails de l'erreur
	 */
	@ExceptionHandler(TechnicalException.class)
	public ResponseEntity<GlobalResponseError> handleTechnicalException(TechnicalException ex) {
		TechnicalError error = new TechnicalError(ex);
		return error.buildResponse();
	}

}