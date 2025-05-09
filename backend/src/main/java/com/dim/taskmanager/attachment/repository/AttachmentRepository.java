package com.dim.taskmanager.attachment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dim.taskmanager.attachment.entity.AttachmentEntity;

public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Long> {

}
