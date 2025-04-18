package com.dim.task.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * Gestionnaire global des exceptions pour l'ensemble des contrôleurs REST.
 * 
 * Cette classe intercepte les exceptions personnalisées et renvoie une réponse HTTP
 * structurée et cohérente, afin d'améliorer l'expérience client et faciliter le débogage.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	private Map<String, Object> buildErrorBody(HttpStatus status, String error) {
		Map<String, Object> bodyError = new HashMap<>();
		bodyError.put("timestamp", LocalDateTime.now());
		bodyError.put("status", status.value());
		bodyError.put("error", error);
		return bodyError;
	}
	
	/**
     * Gère l'exception {@link EmailAlreadyUsedException} lorsque l'email est déjà enregistré.
     *
     * @param ex l'exception levée contenant le message d'erreur
     * @return une réponse HTTP 400 (Bad Request) contenant les détails de l'erreur
     */
	@ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<Object> handleEmailAlreadyUsed(EmailAlreadyUsedException ex) {
        return new ResponseEntity<>(
        	buildErrorBody(HttpStatus.BAD_REQUEST, "Email déjà utilisé"), 
        	HttpStatus.BAD_REQUEST
        );
    }
	
	@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex) {
        return new ResponseEntity<>(
            buildErrorBody(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"), 
            HttpStatus.NOT_FOUND
        );
    }
	
	@ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentials(InvalidCredentialsException ex) {
        return new ResponseEntity<>(
            buildErrorBody(HttpStatus.UNAUTHORIZED, "Mot de passe invalide"), 
            HttpStatus.UNAUTHORIZED
        );
    }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getFieldErrors().forEach(error -> {
	        errors.put(error.getField(), error.getDefaultMessage());
	    });

	    Map<String, Object> body = new HashMap<>();
	    body.put("timestamp", LocalDateTime.now());
	    body.put("status", HttpStatus.BAD_REQUEST.value());
	    body.put("errors", errors);

	    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
}