package com.dim.taskmanager.task.entity;

import java.time.LocalDate;
import java.util.List;

import com.dim.taskmanager.attachment.entity.AttachmentEntity;
import com.dim.taskmanager.project.entity.ProjectEntity;
import com.dim.taskmanager.task.model.Priority;
import com.dim.taskmanager.task.model.Status;
import com.dim.taskmanager.user.entity.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	private String description;
	
	private Boolean completed;
	
	private LocalDate dueDate;
	
	@ManyToOne
	@JoinColumn(name = "assigned_to_id")
	private UserEntity assignedTo;
	
	@ManyToOne
	@JoinColumn(name = "author_id")
	private UserEntity author;
	
	@ManyToOne
	private ProjectEntity project;
	
	@OneToMany(mappedBy = "task")
	private List<AttachmentEntity> attachments;
	
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Enumerated(EnumType.STRING)
	private Priority priority;
	
	
}
