package com.dim.task.service;

import java.util.List;

import com.dim.task.response.output.UserDTO;
import com.dim.task.response.output.UserUpdateDTO;

public interface UserService {
	List<UserDTO> getAllUsers();
	UserDTO getUserById(Long id);
	UserDTO updateUser(Long id, UserUpdateDTO dto);
	void deleteUser(Long id);
	void changeUserRole(Long userId, String role);
}
