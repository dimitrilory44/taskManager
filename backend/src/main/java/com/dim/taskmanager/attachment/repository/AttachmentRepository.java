package com.dim.taskmanager.attachment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dim.taskmanager.attachment.entity.AttachmentEntity;

public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Long> {
	
	@Query("SELECT a FROM AttachmentEntity a WHERE a.task.id = :taskId")
	List<AttachmentEntity> findAllByTaskId(@Param("taskId") Long taskId);

}
