package com.bridgelabz.usermanagement.dto;

import javax.validation.constraints.NotEmpty;

public class ForgotPasswordDto {
	@NotEmpty
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
