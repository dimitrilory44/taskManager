package com.dim.task.user.service;

import java.util.List;

import com.dim.task.user.response.output.UserDTO;

public interface UserService {
	List<UserDTO> getAllUsers();
	UserDTO getUserById(Long id);
	void deleteUser(Long id);
}
