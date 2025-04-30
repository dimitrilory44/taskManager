package com.dim.taskmanager.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JWTProperties {
    
    private String secret;
    
    private long expiration;
}
