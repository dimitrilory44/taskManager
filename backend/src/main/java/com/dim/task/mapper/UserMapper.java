package com.dim.task.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.dim.task.entities.User;
import com.dim.task.response.input.RegisterRequest;
import com.dim.task.response.output.UserDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
	
	@Mapping(target = "id", ignore = true)
	User toUser(RegisterRequest request);
	
	UserDTO toUserDTO(User user);

}