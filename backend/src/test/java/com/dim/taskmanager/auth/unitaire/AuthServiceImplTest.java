package com.dim.taskmanager.auth.unitaire;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dim.taskmanager.auth.exception.EmailAlreadyUsedException;
import com.dim.taskmanager.auth.exception.InvalidCredentialsException;
import com.dim.taskmanager.auth.mapper.AuthMapper;
import com.dim.taskmanager.auth.response.input.LoginRequest;
import com.dim.taskmanager.auth.response.input.RegisterRequest;
import com.dim.taskmanager.auth.response.output.AuthDTO;
import com.dim.taskmanager.auth.service.JWTService;
import com.dim.taskmanager.auth.service.impl.AuthServiceImpl;
import com.dim.taskmanager.user.entity.UserEntity;
import com.dim.taskmanager.user.model.Role;
import com.dim.taskmanager.user.repository.UserRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private AuthMapper authMapper;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JWTService jwtService;

	@InjectMocks
	private AuthServiceImpl authService;

	private Validator validator;

	@BeforeEach
	void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void should_ReturnUserDTO_When_RegisterSuccessful() {
		// Arrange
		RegisterRequest request = new RegisterRequest("dim", "lory", "dimitri", "dim@example.com", "123456");

		UserEntity user = new UserEntity();
		user.setName(request.name());
		user.setFirstName(request.firstName());
		user.setUserName(request.userName());
		user.setEmail(request.email());
		user.setPassword("encoded-password");
		user.setRole(Role.USER);
		user.setEnabled(true);

		AuthDTO userDTO = new AuthDTO(1L, "Alice", "", "", request.email());

		when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());
		when(passwordEncoder.encode(request.password())).thenReturn(user.getPassword());
		when(userRepository.save(user)).thenReturn(user);
		when(authMapper.toDTO(user)).thenReturn(userDTO);

		// Act
		AuthDTO result = authService.register(request);

		// Assert
		assertNotNull(result);
		assertEquals("dim@example.com", result.email());
		verify(userRepository).save(user);
	}

	@Test
	void should_ThrowEmailAlreadyUsedException_When_EmailExists() {
		// Arrange
		RegisterRequest request = new RegisterRequest("dim", "lory", "dimitri", "dim@example.com", "123456");

		when(userRepository.findByEmail("dim@example.com")).thenReturn(Optional.of(new UserEntity()));

		// Act & Assert
		assertThrows(EmailAlreadyUsedException.class, () -> authService.register(request));
		verify(userRepository, never()).save(any());
	}

	@Test
	void should_ReturnToken_When_LoginSuccessful() {
		// Arrange
		String email = "test@example.com";
		String rawPassword = "password";
		String encodedPassword = "encodedPassword";
		Role role = Role.USER;
		UserEntity mockUser = new UserEntity();
		mockUser.setEmail(email);
		mockUser.setPassword(encodedPassword);
		mockUser.setRole(role);

		when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
		when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
		when(jwtService.generateToken(email, role.toString())).thenReturn("fake-jwt-token");

		// Act
		String token = authService.login(email, rawPassword);

		// Assert
		assertEquals("fake-jwt-token", token);
		verify(jwtService).generateToken(email, role.toString());
	}

	@Test
	void should_ThrowUserNotFoundException_When_EmailDoesNotExist() {
		// Arrange
		String nonExistingEmail = "test@example.com";

		when(userRepository.findByEmail(nonExistingEmail)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(InvalidCredentialsException.class, () -> authService.login(nonExistingEmail, "password"));
	}

	@Test
	void should_ThrowInvalidCredentialsException_When_PasswordIsInvalid() {
		// Arrange
		String email = "test@example.com";
		String rawPassword = "wrongPassword";
		String encodedPassword = passwordEncoder.encode("realPassword");

		UserEntity mockUser = new UserEntity();
		mockUser.setEmail(email);
		mockUser.setPassword(encodedPassword);

		when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
		when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

		// Act & Assert
		assertThrows(InvalidCredentialsException.class, () -> authService.login(email, rawPassword));
		verify(jwtService, never()).generateToken(any(), any());
	}

	@Test
	void should_AutoDetectLoginThrowInvalidEmailException_When_EmailIsInvalid() {
		// Arrange
		LoginRequest request = new LoginRequest("invalid-email", "password");

		// Act & Then
		Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);

		assertEquals(1, violations.size());
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
	}

	@Test
	void should_AutoDetectLoginThrowInvalidPasswordException_When_PasswordIsInvalid() {
		// Arrange
		LoginRequest request = new LoginRequest("test@example.com", "");

		// Act & Then
		Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);

		assertEquals(1, violations.size());
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
	}

	@Test
	void should_AutoDetectRegisterThrowInvalidEmailException_When_EmailIsInvalid() {
		// Arrange
		RegisterRequest request = new RegisterRequest("dim", "lory", "dimitri", "invalid-email", "password");

		// Act & Then
		Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

		assertEquals(1, violations.size());
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
	}

	@Test
	void should_AutoDetectRegisterThrowInvalidPasswordException_When_PasswordIsInvalid() {
		// Arrange
		RegisterRequest request = new RegisterRequest("dim", "lory", "dimitri", "example@email.com", "");

		// Act & Then
		Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

		assertEquals(1, violations.size());
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
	}

}
