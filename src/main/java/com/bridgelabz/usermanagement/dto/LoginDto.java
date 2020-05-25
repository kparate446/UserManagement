package com.bridgelabz.usermanagement.dto;

import javax.validation.constraints.NotEmpty;

public class LoginDto {
	@NotEmpty
	private String userName;
	@NotEmpty
	private String userPassword;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
}
