package com.dim.taskmanager.user.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dim.taskmanager.user.entity.UserEntity;
import com.dim.taskmanager.user.model.Role;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByEmail(String email);
	Optional<UserEntity> findByUserName(String userName);
	Page<UserEntity> findAllByRoleNot(Role role, Pageable page);
}
