package com.dim.taskmanager.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dim.taskmanager.project.entity.ProjectEntity;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
}
