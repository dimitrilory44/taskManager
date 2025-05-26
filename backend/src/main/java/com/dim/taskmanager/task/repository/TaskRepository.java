package com.dim.taskmanager.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dim.taskmanager.task.entity.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
	List<TaskEntity> findByProjectId(Long projetId);
	
}