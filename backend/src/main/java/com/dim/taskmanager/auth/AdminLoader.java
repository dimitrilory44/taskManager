package com.dim.taskmanager.auth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dim.taskmanager.config.ApiProperties;
import com.dim.taskmanager.user.entity.UserEntity;
import com.dim.taskmanager.user.model.Role;
import com.dim.taskmanager.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminLoader implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ApiProperties apiProperties;

	@Override
	public void run(String... args) throws Exception {

		if (!userRepository.findByUserName(apiProperties.getAdmin().getUserName()).isPresent()) {
			UserEntity adminUser = new UserEntity();
			adminUser.setUserName(apiProperties.getAdmin().getUserName());
			adminUser.setEmail(apiProperties.getAdmin().getEmail());
			adminUser.setPassword(passwordEncoder.encode(apiProperties.getAdmin().getPassword()));
			adminUser.setRole(Role.ADMIN);
			adminUser.setEnabled(true);
			userRepository.save(adminUser);
			log.info("Utilisateur admin créé");
		}

	}

}