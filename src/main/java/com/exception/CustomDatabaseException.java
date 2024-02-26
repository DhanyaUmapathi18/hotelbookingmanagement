package com.exception;

import java.sql.SQLException;

public class CustomDatabaseException extends Exception {
	public CustomDatabaseException (String message, SQLException e) {
		super(message);
	}

	public CustomDatabaseException(String string) {
		super(string);
	}
	
}