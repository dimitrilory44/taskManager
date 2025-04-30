package com.dim.taskmanager.mapper;

import java.util.List;

public interface TaskManagerMapper<D, E> {

	D toDto(E entity);
	E toEntity(D dto);
	List<D> toDtoList(List<E> entityList);
	List<E> toEntityListDTO(List<D> dtoList);
	
}