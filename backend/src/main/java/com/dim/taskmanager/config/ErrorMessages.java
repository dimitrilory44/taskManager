package com.dim.taskmanager.config;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class ErrorMessages {

	private static final ResourceBundle bundle = ResourceBundle.getBundle("messages");
	
	public static String get(String key) {
		return bundle.getString(key);
	}
	
	public static String get(String key, Object... args) {
		String pattern = bundle.getString(key);
		return MessageFormat.format(pattern, args);
	}
	
	
}
