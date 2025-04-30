package com.dim.taskmanager.auth.service;

import com.dim.taskmanager.auth.response.input.RegisterRequest;
import com.dim.taskmanager.user.response.output.UserDTO;

/**
 * Interface définissant les opérations liées à l'authentification et à l'inscription des utilisateurs.
 * 
 * <p>Elle constitue une couche d'abstraction entre les contrôleurs et la logique métier
 * liée à la sécurité, facilitant les tests et la maintenance du code.</p>
 */
public interface AuthService {
	/**
	 * Crée un nouvel utilisateur à partir des informations d'inscription fournies.
	 * 
	 * <p>Cette méthode effectue les étapes suivantes :
	 * <ul>
	 *   <li>Vérifie si l'utilisateur avec l'adresse email donnée existe déjà en base de données.
	 *       Si c'est le cas, une {@link EmailAlreadyUsedException} est levée.</li>
	 *   <li>Encode le mot de passe en clair avec le {@code passwordEncoder} configuré (généralement Bcrypt).</li>
	 *   <li>Transforme le {@link RegisterRequest} en entité {@link UserEntity} et la persiste dans la base.</li>
	 *   <li>Retourne une représentation {@link UserDTO} de l'utilisateur sauvegardé.</li>
	 * </ul>
	 *
	 * @param register Les données d'inscription de l'utilisateur.
	 * @return L'utilisateur créé, sous forme de {@link UserDTO}.
	 * @throws EmailAlreadyUsedException si l'adresse email est déjà utilisée.
	 */
	UserDTO register(RegisterRequest request);

	/**	
	 * Authentifie un utilisateur à partir de son email et de son mot de passe.
	 * 
	 * <p>Étapes générales :
	 * <ul>
	 *   <li>Recherche de l'utilisateur par email.</li>
	 *   <li>Vérification de la validité du mot de passe saisi.</li>
	 *   <li>Si les informations sont valides, génère un JWT via le {@code JWTService}.</li>
	 * </ul>
	 * </p>
	 *
	 * @param email l'email de l'utilisateur.
	 * @param password le mot de passe (en clair) à vérifier.
	 * @return un token JWT valide en cas d'authentification réussie.
	 * @throws UserNotFoundException si l'email ne correspond à aucun utilisateur existant.
	 * @throws InvalidCredentialsException si le mot de passe est incorrect.
	 */
	String login(String email, String password);

}