package com.dim.task.response.output;

import java.util.Date;

public record TaskDTO (
		
	String title,
	String description,
	Date dueDate

){}