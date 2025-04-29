package com.dim.task.auth.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Exception levée lorsque l'email fourni lors de l'enregistrement est déjà utilisé.
 * 
 * Cette classe hérite de RuntimeException afin de permettre une gestion souple via un ControllerAdvice.
 * Le constructeur permet de définir un message d'erreur personnalisé.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailAlreadyUsedException extends RuntimeException {
	
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
}
