package com.dim.task.unitaire;

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

import com.dim.task.auth.JWTService;
import com.dim.task.entities.Users;
import com.dim.task.exception.EmailAlreadyUsedException;
import com.dim.task.exception.InvalidCredentialsException;
import com.dim.task.exception.UserNotFoundException;
import com.dim.task.mapper.UserMapper;
import com.dim.task.repository.UserRepository;
import com.dim.task.response.input.LoginRequest;
import com.dim.task.response.input.RegisterRequest;
import com.dim.task.response.output.UserDTO;
import com.dim.task.service.impl.AuthServiceImpl;

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
	private UserMapper userMapper;

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

		Users user = new Users();
		user.setEmail(request.getEmail());
		user.setPassword("encoded-password");

		UserDTO userDTO = new UserDTO();
		userDTO.setEmail(request.getEmail());

		when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
		when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded-password");
		when(userMapper.toUser(request)).thenReturn(user);
		when(userRepository.save(user)).thenReturn(user);
		when(userMapper.toUserDTO(user)).thenReturn(userDTO);

		// Act
		UserDTO result = authService.register(request);

		// Assert
		assertNotNull(result);
		assertEquals("dim@example.com", result.getEmail());
		verify(userRepository).save(user);
	}

	@Test
	void should_ThrowEmailAlreadyUsedException_When_EmailExists() {
		// Arrange
		RegisterRequest request = new RegisterRequest("dim", "lory", "dimitri", "dim@example.com", "123456");

		when(userRepository.findByEmail("dim@example.com")).thenReturn(Optional.of(new Users()));

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
		Users mockUser = new Users();
		mockUser.setEmail(email);
		mockUser.setPassword(encodedPassword);

		when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
		when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
		when(jwtService.generateToken(email)).thenReturn("fake-jwt-token");

		// Act
		String token = authService.login(email, rawPassword);

		// Assert
		assertEquals("fake-jwt-token", token);
		verify(jwtService).generateToken(email);
	}

	@Test
	void should_ThrowUserNotFoundException_When_EmailDoesNotExist() {
		// Arrange
		String nonExistingEmail = "test@example.com";

		when(userRepository.findByEmail(nonExistingEmail)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(UserNotFoundException.class, () -> authService.login(nonExistingEmail, "password"));
	}

	@Test
	void should_ThrowInvalidCredentialsException_When_PasswordIsInvalid() {
		// Arrange
		String email = "test@example.com";
		String rawPassword = "wrongPassword";
		String encodedPassword = passwordEncoder.encode("realPassword");

		Users mockUser = new Users();
		mockUser.setEmail(email);
		mockUser.setPassword(encodedPassword);

		when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
		when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

		// Act & Assert
		assertThrows(InvalidCredentialsException.class, () -> authService.login(email, rawPassword));
		verify(jwtService, never()).generateToken(any());
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
