package com.dim.task.response.output;

import java.util.Date;
import lombok.Data;

@Data
public class TaskDTO {
	private String title;
	private String description;
	private Date dueDate;
}
