package com.dim.taskmanager.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.dim.taskmanager.auth.exception.EmailAlreadyUsedException;
import com.dim.taskmanager.auth.exception.InvalidCredentialsException;
import com.dim.taskmanager.auth.exception.UserNotFoundException;
import com.dim.taskmanager.auth.response.input.LoginRequest;
import com.dim.taskmanager.auth.response.input.RegisterRequest;
import com.dim.taskmanager.auth.service.AuthService;
import com.dim.taskmanager.user.entity.UserEntity;
import com.dim.taskmanager.user.model.Role;
import com.dim.taskmanager.user.repository.UserRepository;
import com.dim.taskmanager.user.response.output.UserDTO;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AuthServiceIntegrationTest {
	
	@Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private Validator validator;

    @Test
    void shouldRegisterUserAndEncodePassword() {
        // Given
    	RegisterRequest request = new RegisterRequest("username", "dimitri", "lory", "email@example.com", "password");

        // When
        UserDTO userDTO = authService.register(request);

        // Then
        UserEntity user = userRepository.findByEmail("email@example.com").orElseThrow();

        assertEquals(user.getEmail(), userDTO.email());
        assertTrue(passwordEncoder.matches("password", user.getPassword()));
    }
    
    @Test
    void shouldRegisterUserAndEncodePassword_email__already__used() {
        // Given
    	UserEntity existingUser = new UserEntity();
        existingUser.setUserName("johndoe");
        existingUser.setEmail("test@example.com");
        existingUser.setPassword(passwordEncoder.encode("password"));
        userRepository.save(existingUser);
        
        // When
        RegisterRequest request = new RegisterRequest("newuser", "john", "doe", "test@example.com", "newpassword");

        // Then
        assertThrows(EmailAlreadyUsedException.class, () -> { authService.register(request); });
    }
    
    @Test
    void shouldRegisterThrowExceptionWhenEmailInvalid() {
        // Given
    	RegisterRequest invalidEmail = new RegisterRequest("newuser", "john", "doe", "invalid-email", "newpassword");

        // When
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(invalidEmail);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }
    
    @Test
    void shouldRegisterThrowExceptionWhenPasswordInvalid() {
        // Given
    	RegisterRequest invalidPassword = new RegisterRequest("newuser", "john", "doe", "test@example.com", "");

        // When 
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(invalidPassword);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }
    
    @Test
    void shouldLoginAndReturnToken() {
        // Given
        UserEntity user = new UserEntity();
        user.setEmail("test@email.com");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setRole(Role.USER);
        userRepository.save(user);

        // When
        String token = authService.login("test@email.com", "password123");

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }
   
    @Test
    void shouldLoginAndReturnToken_invalid_password() {
        // Given
        UserEntity userPasswordInvalid = new UserEntity();
        userPasswordInvalid.setEmail("test@email.com");
        userPasswordInvalid.setPassword(passwordEncoder.encode("password123"));
        userRepository.save(userPasswordInvalid);

        // When / Then
        assertThrows(InvalidCredentialsException.class, () -> { authService.login("test@email.com", "wrongPassword"); });
    }
    
    @Test
    void shouldLoginAndReturnToken_user_not_found() {
        // Given : Pas la peine d'en enregistrer un

        // When / Then        
        assertThrows(UserNotFoundException.class, () -> { authService.login("non-existant@email.com", "password"); });
    }
    
    @Test
    void shouldLoginThrowExceptionWhenEmailInvalid() {
        // Given
        LoginRequest invalidEmailRequest = new LoginRequest("invalid-email", "password123");

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(invalidEmailRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }
    
    @Test
    void shouldLoginThrowExceptionWhenPasswordInvalid() {
        // Given
        LoginRequest invalidPasswordRequest = new LoginRequest("test@example.fr", "");

        // When 
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(invalidPasswordRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }
    
}