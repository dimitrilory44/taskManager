package com.dim.task.auth;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dim.task.entities.Users;
import com.dim.task.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Implémentation personnalisée de {@link UserDetailsService}.
 *
 * <p>Ce service est utilisé par Spring Security pour récupérer les informations
 * d'un utilisateur (UserDetails) à partir de son identifiant (ici : l'email).</p>
 *
 * <p>Il est appelé automatiquement lors de l'authentification JWT (ou formulaire)
 * pour vérifier les identifiants et charger les rôles associés.</p>
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	
	/**
     * Méthode appelée automatiquement par Spring Security lorsqu’un utilisateur tente de se connecter.
     *
     * @param username l’email de l’utilisateur (car tu utilises email comme identifiant principal)
     * @return un objet UserDetails utilisé pour l’authentification
     * @throws UsernameNotFoundException si l’utilisateur n’existe pas en base
     */
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));

        String roleName = user.getRole().toString();
        String authority = "ROLE_" + roleName;
        
        return new User(
            user.getEmail(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority(authority)) 
        );
    }
}