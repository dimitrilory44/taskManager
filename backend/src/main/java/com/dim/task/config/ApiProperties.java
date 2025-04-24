package com.dim.task.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "api")
public class ApiProperties {

	private String prefix;
	
	private String version;
}
