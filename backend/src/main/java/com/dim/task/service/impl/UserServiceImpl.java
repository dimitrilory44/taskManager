package com.dim.task.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dim.task.entities.Users;
import com.dim.task.mapper.UserMapper;
import com.dim.task.repository.UserRepository;
import com.dim.task.response.output.UserDTO;
import com.dim.task.response.output.UserUpdateDTO;
import com.dim.task.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final UserMapper userMapper;

	@Override
	public List<UserDTO> getAllUsers() {
		List<Users> usersList = userRepository.findAll();
		return userMapper.toUsersDTO(usersList);
	}

	@Override
	public UserDTO getUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO updateUser(Long id, UserUpdateDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeUserRole(Long userId, String role) {
		// TODO Auto-generated method stub
		
	}
		
}