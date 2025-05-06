package com.dim.taskmanager.auth.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dim.taskmanager.auth.exception.EmailAlreadyUsedException;
import com.dim.taskmanager.auth.exception.InvalidCredentialsException;
import com.dim.taskmanager.auth.exception.UserNameAlreadyUsedException;
import com.dim.taskmanager.auth.exception.UserNotFoundException;
import com.dim.taskmanager.auth.mapper.AuthMapper;
import com.dim.taskmanager.auth.response.input.RegisterRequest;
import com.dim.taskmanager.auth.response.output.AuthDTO;
import com.dim.taskmanager.auth.service.AuthService;
import com.dim.taskmanager.auth.service.JWTService;
import com.dim.taskmanager.user.entity.UserEntity;
import com.dim.taskmanager.user.repository.UserRepository;
import com.dim.taskmanager.user.response.output.UserDTO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implémentation de l'interface {@link AuthService} qui gère l'inscription et l'authentification des utilisateurs.
 * 
 * <p>Ce service interagit avec le dépôt {@link UserRepository}, l'encodeur de mot de passe,
 * un mapper pour convertir les entités en DTO, et le {@link JWTService} pour la génération de tokens JWT.</p>
 */
@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthMapper authMapper;
	private final JWTService jwtService;

	/**
	 * Inscrit un nouvel utilisateur après avoir vérifié que son adresse email n'est pas déjà utilisée.
	 * 
	 * <p>Le mot de passe est encodé avec {@link PasswordEncoder}, puis le nouvel utilisateur est
	 * sauvegardé en base de données. Un {@link UserDTO} est ensuite retourné.</p>
	 *
	 * @param register les informations d'inscription saisies par l'utilisateur.
	 * @return le DTO de l'utilisateur nouvellement créé.
	 * @throws EmailAlreadyUsedException si un utilisateur existe déjà avec cet email.
	 */
	@Override
	public AuthDTO register(RegisterRequest rawRegister) {
		userRepository.findByEmail(rawRegister.email())
			.ifPresent(user -> {
				log.warn("Création échouée - email déjà utilisé : {}", rawRegister.email());
				throw new EmailAlreadyUsedException("Email déjà utilisé");
			});
		
		userRepository.findByUserName(rawRegister.userName())
			.ifPresent(user -> {
				log.warn("Création échouée - userName déjà utilisé : {}", rawRegister.userName());
				throw new UserNameAlreadyUsedException("UserName déjà utilisé"); 
			});
		
		String encodedPassword = passwordEncoder.encode(rawRegister.password());
		
		RegisterRequest register = new RegisterRequest(rawRegister.userName(), rawRegister.name(), rawRegister.firstName(), rawRegister.email(), encodedPassword);
		
		UserEntity userSaved = userRepository.save(authMapper.toEntity(register));
		
		log.info("Tentative de création de l'utilisateur avec l'email : {}", userSaved.getEmail());
		return authMapper.toDTO(userSaved);
	}

	/**
	 * Authentifie un utilisateur en vérifiant l'email et le mot de passe.
	 * 
	 * <p>Si les informations sont valides, un token JWT est généré et retourné.
	 * En cas d'erreur, une exception personnalisée est levée.</p>
	 *
	 * @param email l'adresse email de l'utilisateur.
	 * @param password le mot de passe (en clair) à vérifier.
	 * @return un JWT en cas de succès.
	 * @throws UserNotFoundException si aucun utilisateur ne correspond à cet email.
	 * @throws InvalidCredentialsException si le mot de passe est incorrect.
	 */
	@Override
	public String login(String email, String password) {
		UserEntity userPresent = userRepository.findByEmail(email)
			.orElseThrow(() -> {
				log.warn("Connexion échouée - utilisateur non trouvé : {}", email);
				return new UserNotFoundException("Utilisateur non trouvé");
			});
		
		if(!passwordEncoder.matches(password, userPresent.getPassword())) {
			log.warn("Connexion échouée - mot de passe invalide pour l'utilisateur : {}", email);
			throw new InvalidCredentialsException("Mot de passe incorrect");
		}
		
		log.info("Connexion réussie pour l'utilisateur : {}", email);
		return jwtService.generateToken(email, userPresent.getRole().toString());
	}
		
}