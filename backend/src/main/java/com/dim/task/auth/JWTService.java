package com.dim.task.auth;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Service responsable de la génération des tokens JWT pour l'authentification.
 * 
 * <p>Cette interface permet de centraliser la logique de création de tokens JWT et de gestion
 * des tokens, assurant ainsi une meilleure séparation des responsabilités. Elle fournit des 
 * méthodes pour la génération de tokens, la validation, l'extraction du nom d'utilisateur, et 
 * l'extraction du token depuis la requête HTTP.</p>
 * 
 * <p>L'implémentation de cette interface permettra de gérer la création, la vérification et 
 * l'extraction d'informations contenues dans un token JWT. Cela peut être utile pour 
 * l'authentification et la gestion des sessions des utilisateurs dans une application.</p>
 */
public interface JWTService {

	/**
     * Génère un token JWT signé à partir du nom d'utilisateur (ou adresse email).
     * 
     * <p>Cette méthode crée un token JWT en utilisant le nom d'utilisateur ou l'email comme sujet 
     * du token. Le token peut aussi inclure des claims supplémentaires (par exemple, les rôles de 
     * l'utilisateur). Le token est ensuite signé avec une clé secrète définie dans la configuration 
     * de l'application pour garantir son intégrité et empêcher toute falsification.</p>
     * 
     * <p>Le token généré aura une durée de validité spécifique, et sa signature permet de vérifier 
     * son authenticité au moment de son utilisation.</p>
     *
     * @param userName le nom d'utilisateur ou email à inclure dans le token. C'est une chaîne de caractères.
     * @return un JWT signé sous forme de chaîne de caractères. Ce token contient des informations sur l'utilisateur
     *         et est sécurisé par une signature.
     */
	String generateToken(String userName);
	
	/**
     * Vérifie si un token JWT est valide.
     * 
     * <p>Cette méthode analyse le token JWT pour vérifier si la signature est valide et si le token n'a pas expiré.
     * Cela permet de s'assurer que le token utilisé pour une requête est authentique et qu'il est toujours valide.</p>
     *
     * @param token le token JWT à vérifier.
     * @return {@code true} si le token est valide (signature et expiration correctes), sinon {@code false}.
     */
	boolean isTokenValid(String token);
	
	/**
     * Extrait le nom d'utilisateur (ou email) à partir d'un token JWT.
     * 
     * <p>Cette méthode permet de récupérer le nom d'utilisateur (ou email) contenu dans le token JWT.
     * Cela est utile pour identifier l'utilisateur qui est authentifié avec ce token.</p>
     *
     * @param token le token JWT contenant les informations d'utilisateur.
     * @return le nom d'utilisateur (ou email) extrait du token. Ce nom est souvent utilisé pour l'authentification.
     */
	String extractUsername(String token);
	
	/**
     * Extrait le token JWT à partir de la requête HTTP.
     * 
     * <p>Cette méthode permet d'extraire le token JWT envoyé dans l'en-tête de la requête HTTP. Elle est généralement
     * utilisée dans des filtres d'authentification pour extraire le token des requêtes entrantes afin de valider l'utilisateur
     * et son accès aux ressources protégées.</p>
     *
     * @param request la requête HTTP contenant l'en-tête "Authorization" avec le token JWT.
     * @return le token JWT extrait de l'en-tête "Authorization", ou {@code null} si aucun token n'est présent.
     */
	String extractToken(HttpServletRequest request);
	
}