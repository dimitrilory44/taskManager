package com.dim.taskmanager.user;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "admin")
public class AdminUser {
	private String userName;

	private String email;

	private String password;

	private String role;
}