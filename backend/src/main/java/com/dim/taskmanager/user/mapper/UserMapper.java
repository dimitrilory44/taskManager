package com.dim.taskmanager.user.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.dim.taskmanager.mapper.TaskManagerMapper;
import com.dim.taskmanager.user.entity.UserEntity;
import com.dim.taskmanager.user.response.output.UserDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends TaskManagerMapper<UserDTO, UserEntity>{
	
	default Page<UserDTO> toPageUserDTO(Page<UserEntity> usersPage) {
		List<UserDTO> dtoList = toDTOList(usersPage.getContent());
		return new PageImpl<>(dtoList, usersPage.getPageable(), usersPage.getTotalElements());
	}
	
}