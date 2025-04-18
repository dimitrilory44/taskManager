package com.dim.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dim.task.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
