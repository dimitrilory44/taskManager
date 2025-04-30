package com.dim.taskmanager.mapper;

import java.util.List;

public interface TaskManagerMapper<D, E> {

	D toDTO(E entity);
	E toEntity(D DTO);
	List<D> toDTOList(List<E> entityList);
	List<E> toEntityList(List<D> DTOList);
	
}