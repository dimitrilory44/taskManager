package com.dim.taskmanager.mapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public interface TaskManagerMapper<D, E> {

	D toDTO(E entity);
	E toEntity(D DTO);
	List<D> toDTOList(List<E> entityList);
	List<E> toEntityList(List<D> DTOList);
	
	/**
     * Convertit une page d'entités en une page de DTO.
     *
     * @param entityPage la page d'entités à convertir
     * @return la page de DTO correspondante
     */
    default Page<D> toPageDTO(Page<E> entityPage) {
        List<D> dtoList = toDTOList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}