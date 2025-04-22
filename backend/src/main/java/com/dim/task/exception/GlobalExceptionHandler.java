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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Aucun compte ne correspond à l'email fourni.", ex.getMessage(), null);
    }

    // 400
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleBadRequest(IllegalArgumentException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Requête invalide", ex.getMessage(), null);
    }

    // 401
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorized(UnauthorizedException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Il faut pour cela être identifié pour y avoir accès", ex.getMessage(), null);
    }

    // 403
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleForbidden(AccessDeniedException ex) {
        return buildResponse(HttpStatus.FORBIDDEN, "Vous n'avez pas accès à la ressource", ex.getMessage(), null);
    }

    // Catch-all
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Vous n'avez pas accès à la ressource", ex.getMessage(), null);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentials(InvalidCredentialsException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Le mot de passe ne correspond pas à celui existant en base", ex.getMessage(), null);
    }

    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<Object> handleTechnicalException(TechnicalException ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Le mot de passe ne correspond pas à celui existant en base", ex.getMessage(), null);
    }

    // Mauvais paramètre d'URL ou type d'entrée (400)
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
                (existing, replacement) -> existing
            )));

        return buildResponse(HttpStatus.BAD_REQUEST, "Erreur de validation", "Des champs requis sont manquants ou invalides.", fieldErrors);
    }

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