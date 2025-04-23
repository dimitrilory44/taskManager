package com.dim.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dim.task.entities.Tasks;

public interface TaskRepository extends JpaRepository<Tasks, Long> {

}
