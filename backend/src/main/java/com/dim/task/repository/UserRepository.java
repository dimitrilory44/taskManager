package com.dim.task.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dim.task.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
