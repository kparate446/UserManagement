package com.bridgelabz.usermanagement.exception;

public class InvalidUser extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public InvalidUser (String message) {
		super(message);
	}
}