package com.dim.task.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "jwt")
public class JWTProperties {
    
    private String secret;
    
    private long expiration;
}
