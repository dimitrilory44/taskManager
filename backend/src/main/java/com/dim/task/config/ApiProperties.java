package com.dim.task.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "api")
public class ApiProperties {

	private String prefix;
	
	private String version;
	
	private AdminUser admin;
}
