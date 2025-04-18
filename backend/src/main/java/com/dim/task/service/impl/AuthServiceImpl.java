package com.dim.task.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dim.task.entities.User;
import com.dim.task.exception.EmailAlreadyUsedException;
import com.dim.task.exception.InvalidCredentialsException;
import com.dim.task.exception.UserNotFoundException;
import com.dim.task.mapper.UserMapper;
import com.dim.task.repository.UserRepository;
import com.dim.task.response.input.RegisterRequest;
import com.dim.task.response.output.UserDTO;
import com.dim.task.service.AuthService;
import com.dim.task.service.JWTService;

import lombok.AllArgsConstructor;

/**
 * Implémentation de l'interface {@link AuthService} qui gère l'inscription et l'authentification des utilisateurs.
 * 
 * <p>Ce service interagit avec le dépôt {@link UserRepository}, l'encodeur de mot de passe,
 * un mapper pour convertir les entités en DTO, et le {@link JWTService} pour la génération de tokens JWT.</p>
 */
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;
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
	public UserDTO register(RegisterRequest register) {
		userRepository.findByEmail(register.getEmail())
			.ifPresent(user -> { throw new EmailAlreadyUsedException("Email déjà utilisé"); });
		
		register.setPassword(passwordEncoder.encode(register.getPassword()));
				
		User userSaved = userRepository.save(userMapper.toUser(register));
		
		return userMapper.toUserDTO(userSaved);
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
		User userPresent = userRepository.findByEmail(email)
			.orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé"));
		
		if(!passwordEncoder.matches(password, userPresent.getPassword())) {
			throw new InvalidCredentialsException("Mot de passe incorrect");
		}
		
		return jwtService.generateToken(email);
	}
	
}