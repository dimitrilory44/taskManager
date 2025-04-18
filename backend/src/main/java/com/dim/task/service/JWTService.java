package com.dim.task.service;

/**
 * Service responsable de la génération des tokens JWT pour l'authentification.
 * 
 * <p>Cette interface permet de centraliser la logique de création de tokens,
 * afin de garantir une meilleure séparation des responsabilités et faciliter les tests.</p>
 */
public interface JWTService {
	
	/**
     * Génère un token JWT signé à partir du nom d'utilisateur (ou adresse email).
     *
     * <p>Le token peut contenir des claims supplémentaires (selon l'implémentation) et est
     * signé avec la clé secrète définie dans la configuration de l'application.</p>
     *
     * @param userName le nom d'utilisateur ou email à inclure dans le token.
     * @return un JWT signé sous forme de chaîne de caractères.
     */
	String generateToken(String userName);
}