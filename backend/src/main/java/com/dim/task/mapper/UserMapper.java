package com.dim.task.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.dim.task.entities.Users;
import com.dim.task.response.input.RegisterRequest;
import com.dim.task.response.output.UserDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
	
	@Mapping(target = "id", ignore = true)
	Users toUser(RegisterRequest request);
	
	UserDTO toUserDTO(Users user);

}