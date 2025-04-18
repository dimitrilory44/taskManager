package com.dim.task.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "jwt")
public class JWTProperties {
	private String secret;
    private long expiration;
}
