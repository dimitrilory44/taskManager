package com.dim.taskmanager.user.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dim.taskmanager.user.entity.UserEntity;
import com.dim.taskmanager.user.exception.UserNotFoundException;
import com.dim.taskmanager.user.mapper.UserMapper;
import com.dim.taskmanager.user.model.Role;
import com.dim.taskmanager.user.repository.UserRepository;
import com.dim.taskmanager.user.response.output.UserDTO;
import com.dim.taskmanager.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	@Override
	public Page<UserDTO> getAllUsers(Pageable pageable) {
		Page<UserEntity> usersPage = userRepository.findAllByRoleNot(Role.ADMIN, pageable);
		
		return userMapper.toPageDTO(usersPage);
		
	}

	@Override
	public UserDTO getUserById(Long id) {
		log.info("Tentative de récupération d'un utilisateur avec l'ID : {}", id);
		UserEntity user = userRepository.findById(id)
				.orElseThrow(() -> 
					new UserNotFoundException("Utilisateur non trouvé pour l'ID : " + id)
				);
		
		return userMapper.toDTO(user);
		
	}

	@Override
	public void deleteUser(Long id) {
		log.info("Tentative de suppression d'un utilisateur avec l'ID : {}", id);
		if (!userRepository.existsById(id)) {
			throw new UserNotFoundException("Utilisateur non trouvé pour l'ID : " + id);
		}
		
		userRepository.deleteById(id);
		
	}

}