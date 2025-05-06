package com.dim.taskmanager.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dim.taskmanager.auth.JwtAuthenticationFilter;
import com.dim.taskmanager.config.ApiProperties;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@EnableConfigurationProperties
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final ApiProperties apiProperties;
	
	private final JwtAuthenticationFilter jwtAuthFilter;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;
	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(auth -> auth
				.requestMatchers(
						"/" + apiProperties.getPrefix() + "/" + apiProperties.getVersion() + "/auth/**",
						"/h2-console",
						"/v3/api-docs/**", 
						"/swagger-ui/**", 
						"/swagger-ui.html"
						).permitAll()
				.requestMatchers("/" + apiProperties.getPrefix() + "/" + apiProperties.getVersion() + "/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated())
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(exception -> exception
				   .authenticationEntryPoint(customAuthenticationEntryPoint)
				   .accessDeniedHandler(customAccessDeniedHandler)
				);
		return http.build();
	}
		
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}