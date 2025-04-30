package com.dim.taskmanager.user.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.dim.taskmanager.auth.response.input.RegisterRequest;
import com.dim.taskmanager.user.entity.UserEntity;
import com.dim.taskmanager.user.model.Role;
import com.dim.taskmanager.user.response.output.UserDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
		
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "role", expression = "java(setDefaultRole())")
	@Mapping(target = "enabled", expression = "java(setDefaultEnabled())")
	UserEntity toUser(RegisterRequest request);
	
	@Mapping(target = "userName", source = "username")
	UserDTO toUserDTO(UserEntity user);
	
	List<UserDTO> toUsersDTO(List<UserEntity> users);

	default Page<UserDTO> toPageUserDTO(Page<UserEntity> usersPage) {
		List<UserDTO> dtoList = toUsersDTO(usersPage.getContent());
		return new PageImpl<>(dtoList, usersPage.getPageable(), usersPage.getTotalElements());
	}
	
	default Role setDefaultRole() {
		return Role.USER;
	}
	
	default Boolean setDefaultEnabled() {
		return true;
	}
	
}