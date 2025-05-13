package com.dim.taskmanager.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.dim.taskmanager.auth.response.output.AuthDTO;
import com.dim.taskmanager.mapper.TaskManagerMapper;
import com.dim.taskmanager.user.entity.UserEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper extends TaskManagerMapper<AuthDTO, UserEntity> {
	
	@Mapping(target = "userName", source = "username")
	AuthDTO toDTO(UserEntity user);
	
}