package com.bridgelabz.usermanagement.dto;

import javax.validation.constraints.NotEmpty;

public class ResetPasswordDto {
	@NotEmpty
	private String userPassword;
	@NotEmpty
	private String confirmPassword;
	
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
