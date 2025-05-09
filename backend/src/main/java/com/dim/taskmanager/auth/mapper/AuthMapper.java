package com.dim.taskmanager.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.dim.taskmanager.auth.response.input.RegisterRequest;
import com.dim.taskmanager.auth.response.output.AuthDTO;
import com.dim.taskmanager.mapper.TaskManagerMapper;
import com.dim.taskmanager.user.entity.UserEntity;
import com.dim.taskmanager.user.model.Role;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper extends TaskManagerMapper<AuthDTO, UserEntity> {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "role", expression = "java(setDefaultRole())")
	@Mapping(target = "enabled", expression = "java(setDefaultEnabled())")
	UserEntity toUserEntity(RegisterRequest request);
	
	@Mapping(target = "userName", source = "username")
	AuthDTO toDTO(UserEntity user);
	
	default Role setDefaultRole() {
		return Role.USER;
	}
	
	default Boolean setDefaultEnabled() {
		return true;
	}
}
