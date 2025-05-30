package com.dim.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dim.taskmanager.auth.JWTProperties;
import com.dim.taskmanager.config.ApiProperties;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TaskManagerApplicationTests {

	@Autowired
	private ApiProperties apiProperties;
	
	@Autowired
	private JWTProperties jwtProperties;

	@Test
	void jwtConfigShouldBeLoaded() {
		assertNotNull(jwtProperties.getSecret());
		assertTrue(jwtProperties.getExpiration() > 0);
	}
	
	@Test
	void apiConfigShouldBeLoaded() {
		assertNotNull(apiProperties.getPrefix());
		assertNotNull(apiProperties.getVersion());
	}

}
