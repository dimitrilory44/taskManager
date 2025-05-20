package com.dim.taskmanager.exception.error;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Interface générique représentant une erreur de l'API.
 * Elle permet de standardiser la structure des réponses d'erreur.
 *
 * @param <T> Le type du corps de la réponse d'erreur (ex: GlobalResponseError, ValidationResponseError).
 */
public interface ApiError<T> {

	/**
     * Statut HTTP associé à l'erreur (ex : 400, 404, 500).
     */
	HttpStatus status();
	
	/**
     * Libellé général de l'erreur (ex : "Bad Request", "Unauthorized").
     */
	String error();
	
	/**
     * Message plus détaillé destiné à l'utilisateur ou au développeur.
     */
	String message();
	
	/**
     * Construit et retourne le corps de la réponse d'erreur.
     * Chaque implémentation est responsable de sa propre construction.
     *
     * @return le corps d'erreur de type T.
     */
	T buildBody();
	
	/**
     * Fournit les détails supplémentaires de l'erreur sous forme de paires champ/message.
     * Utile notamment pour les erreurs de validation de formulaire.
     *
     * @return une map contenant les détails, ou null si non applicable (valeur par défaut).
     */
	default Map<String, String> details() {
		return null;
	}
	
	/**
     * Méthode utilitaire utilisée par tous les gestionnaires d'exceptions pour
     * construire une réponse HTTP structurée avec le corps d'erreur approprié.
     *
     * @return une ResponseEntity contenant le corps d'erreur et le statut HTTP.
     */
	default ResponseEntity<T> buildResponse() {
        return new ResponseEntity<>(buildBody(), status());
    }
	
}