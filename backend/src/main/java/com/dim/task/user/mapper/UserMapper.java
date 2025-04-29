package com.dim.task.user.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.dim.task.auth.response.input.RegisterRequest;
import com.dim.task.entities.Users;
import com.dim.task.user.model.Role;
import com.dim.task.user.response.output.UserDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "role", expression = "java(setDefaultRole())")
	@Mapping(target = "enabled", expression = "java(setDefaultEnabled())")
	Users toUser(RegisterRequest request);
	
	@Mapping(target = "userName", source = "username")
	UserDTO toUserDTO(Users user);
	
	List<UserDTO> toUsersDTO(List<Users> users);

	default Role setDefaultRole() {
		return Role.USER;
	}
	
	default Boolean setDefaultEnabled() {
		return true;
	}
}