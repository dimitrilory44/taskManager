package com.dim.taskmanager.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.dim.taskmanager.mapper.TaskManagerMapper;
import com.dim.taskmanager.user.entity.UserEntity;
import com.dim.taskmanager.user.response.output.UserDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends TaskManagerMapper<UserDTO, UserEntity>{
	
	@Mapping(target = "userName", source = "username")
	UserDTO toDTO(UserEntity user);
	
}