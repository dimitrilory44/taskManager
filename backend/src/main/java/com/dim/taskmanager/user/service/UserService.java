package com.dim.taskmanager.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dim.taskmanager.user.response.output.UserDTO;

public interface UserService {
	Page<UserDTO> getAllUsers(Pageable pageable);
	UserDTO getUserById(Long id);
	void deleteUser(Long id);
}
