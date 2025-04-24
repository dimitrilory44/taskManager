package com.dim.task.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.dim.task.config.JWTProperties;
import com.dim.task.service.JWTService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implémentation de {@link JWTService} pour la génération de tokens JWT.
 * 
 * <p>Cette classe utilise les propriétés définies dans {@link JWTProperties} pour configurer
 * la signature et la durée de validité du token. Le token est signé avec l'algorithme HMAC-SHA256.</p>
 */
@Slf4j
@Service
@AllArgsConstructor
public class JWTServiceImpl implements JWTService {

	/**
	 * Propriétés de configuration du token (clé secrète, expiration, etc.)
	 * Injectées automatiquement depuis le fichier application.properties.
	 */
	private final JWTProperties jwtProperties;

	/**
	 * Génère un token JWT à partir de l'email fourni.
	 * 
	 * <p>Le token contient les informations suivantes :
	 * <ul>
	 *   <li><b>Subject :</b> l'email de l'utilisateur (sert d'identifiant principal)</li>
	 *   <li><b>IssuedAt :</b> date de création du token</li>
	 *   <li><b>Expiration :</b> date d’expiration basée sur la durée configurée</li>
	 * </ul>
	 * Le token est signé à l'aide d'une clé générée dynamiquement avec {@code Keys.secretKeyFor()}.
	 * </p>
	 *
	 * @param email l'identifiant de l'utilisateur (souvent utilisé comme "username" pour l’authentification)
	 * @return un token JWT signé, sous forme de chaîne.
	 */
	@Override
	public String generateToken(String email) {		
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", List.of("ROLE_USER"));

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(email)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	/**
	 * Vérifie la validité du token.
	 * 
	 * Cette méthode tente de parser le token avec la clé secrète.
	 * Si le parsing échoue (token invalide, expiré ou mal signé),
	 * une exception est attrapée et false est retourné.
	 *
	 * @param token JWT à valider
	 * @return true si le token est bien formé et non expiré, false sinon
	 */
	@Override
	public boolean isTokenValid(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(jwtProperties.getSecret().getBytes())  // clé sous forme de bytes
			.build()
			.parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			log.warn("Token invalide : {}", e.getMessage());
			return false;  // Si le token est invalide ou expiré, une exception sera levée
		}
	}

	/**
	 * Extrait le nom d'utilisateur (email) à partir du token JWT.
	 * 
	 * @param token JWT
	 * @return subject (souvent : email)
	 */
	@Override
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/** Méthode utilitaire pour extraire le token JWT de l'en-tête Authorization.
	 *
	 * @param request la requête HTTP
	 * @return le token JWT s’il est présent, sinon null
	 */
	@Override
	public String extractToken(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);  // Retirer "Bearer "
		}
		return null;
	}

	/**
	 * Récupère la clé de signature HMAC en utilisant la clé secrète fournie dans la config.
	 * Elle doit être encodée en UTF-8 pour être compatible avec la méthode utilisée.
	 *
	 * @return SecretKey construite pour HMAC
	 */
	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Méthode générique pour extraire n'importe quel claim depuis un token JWT.
	 * 
	 * @param token JWT
	 * @param claimsResolver fonction lambda qui extrait un champ spécifique
	 * @param <T> type du champ extrait
	 * @return valeur du claim demandé
	 */
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * Parse le token pour extraire tous les claims disponibles.
	 *
	 * @param token JWT
	 * @return claims décodés (corps du token)
	 */
	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

}