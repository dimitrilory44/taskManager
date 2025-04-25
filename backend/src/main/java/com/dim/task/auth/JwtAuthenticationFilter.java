package com.dim.task.auth;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dim.task.auth.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Filtre d'authentification JWT exécuté à chaque requête HTTP.
 *
 * <p>Ce filtre intercepte toutes les requêtes entrantes, extrait le token JWT,
 * le valide, et si tout est correct, configure l’utilisateur authentifié dans le contexte de sécurité de Spring.</p>
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JWTService jwtService;
	private final UserDetailsService userDetailsService;

	/**
	 * Méthode appelée automatiquement pour chaque requête HTTP reçue.
	 *
	 * @param request la requête HTTP entrante
	 * @param response la réponse HTTP en cours
	 * @param filterChain la chaîne de filtres de sécurité
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Extraire le token de la requête
		String token = jwtService.extractToken(request);

		if (token != null && jwtService.isTokenValid(token)) {
			// Extraire le nom d'utilisateur du token
			String username = jwtService.extractUsername(token);

			// Charger les détails de l'utilisateur
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			// Créer un objet Authentication pour Spring Security
			Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

			// Placer l'objet Authentication dans le contexte de sécurité
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		// Passer au filtre suivant dans la chaîne de filtres
		filterChain.doFilter(request, response);
	}

}