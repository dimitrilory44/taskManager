package com.dim.task.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dim.task.entities.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByEmail(String email);
	Optional<Users> findByUserName(String userName);
}
