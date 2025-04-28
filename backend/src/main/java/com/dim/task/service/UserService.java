package com.dim.task.service;

import java.util.List;

import com.dim.task.response.output.UserDTO;

public interface UserService {
	List<UserDTO> getAllUsers();
	UserDTO getUserById(Long id);
	void deleteUser(Long id);
}
