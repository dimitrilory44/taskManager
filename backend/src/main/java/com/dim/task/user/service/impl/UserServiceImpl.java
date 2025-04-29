package com.dim.task.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dim.task.auth.exception.UserNotFoundException;
import com.dim.task.entities.Users;
import com.dim.task.user.mapper.UserMapper;
import com.dim.task.user.repository.UserRepository;
import com.dim.task.user.response.output.UserDTO;
import com.dim.task.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	@Override
	public List<UserDTO> getAllUsers() {
		List<Users> usersList = userRepository.findAll();
		// Retirer l'utilisateur admin (toujours premier dans la liste)
		usersList.remove(0);	
		return userMapper.toUsersDTO(usersList);
	}

	@Override
	public UserDTO getUserById(Long id) {
		Users user = userRepository.findById(id)
				.orElseThrow(() -> 
					new UserNotFoundException("Utilisateur non trouvé pour l'ID : " + id)
				);
		return userMapper.toUserDTO(user);
	}
	
	@Override
	public void deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
            log.error("Tentative de suppression d'un utilisateur inexistant avec l'ID : {}", id);
            throw new UserNotFoundException("Utilisateur non trouvé pour l'ID : " + id);
        }
		userRepository.deleteById(id);
	}

}