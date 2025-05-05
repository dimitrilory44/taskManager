package com.dim.taskmanager.config;

import java.util.ResourceBundle;

public class ErrorMessages {

	private static final ResourceBundle bundle = ResourceBundle.getBundle("messages");
	
	public static String get(String key) {
		return bundle.getString(key);
	}
}
