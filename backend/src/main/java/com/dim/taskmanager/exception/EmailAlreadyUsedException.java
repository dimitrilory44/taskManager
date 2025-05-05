package com.dim.taskmanager.exception;

import org.springframework.http.HttpStatus;

import com.dim.taskmanager.config.ErrorMessages;

/**
 * Exception levée lorsque l'email fourni lors de l'enregistrement est déjà utilisé.
 * 
 * Cette classe hérite de RuntimeException afin de permettre une gestion souple via un ControllerAdvice.
 * Le constructeur permet de définir un message d'erreur personnalisé.
 */
public final class EmailAlreadyUsedException extends AuthException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2898995054011525217L;

	/**
     * Construit une nouvelle exception avec un message d'erreur personnalisé.
     *
     * @param message le message d'erreur à afficher
     */
	public EmailAlreadyUsedException(String message) {
		super(message);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.CONFLICT;
	}

	@Override
	public String getErrorMessage() {
		return ErrorMessages.get("email.exist");
	}
	
}
