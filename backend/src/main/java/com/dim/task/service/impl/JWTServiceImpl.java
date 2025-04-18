package com.dim.task.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.dim.task.config.JWTProperties;
import com.dim.task.service.JWTService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import lombok.AllArgsConstructor;

/**
 * Implémentation de {@link JWTService} pour la génération de tokens JWT.
 * 
 * <p>Cette classe utilise les propriétés définies dans {@link JWTProperties} pour configurer
 * la signature et la durée de validité du token. Le token est signé avec l'algorithme HMAC-SHA256.</p>
 */
@Service
@AllArgsConstructor
public class JWTServiceImpl implements JWTService {
	
	/**
     * Propriétés de configuration pour le JWT (clé secrète, durée d’expiration, etc.)
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
		SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
	}
}