package com.dim.taskmanager.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.dim.taskmanager.user.AdminUser;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "api")
public class ApiProperties {

	private String prefix;
	
	private String version;
	
	private AdminUser admin;
}
