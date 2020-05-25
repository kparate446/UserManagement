package com.bridgelabz.usermanagement.dto;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

//import javax.validation.Valid;
//import javax.validation.constraints.NotEmpty;

public class RegistrationDto {
	@Valid
	@NotEmpty(message = "Please provide firstName")
	private String firstName;
	@NotEmpty(message = "Please provide middleName")
	private String middleName;
	@NotEmpty(message = "Please provide lastName")
	private String lastName;
//	private String birthOfDate;
	private LocalDate birthOfDate;
	@NotEmpty
	private String gender;
	@NotEmpty
	private String country;
	@NotEmpty
	private String phoneNumber;
	@NotEmpty
	private String email;
	@NotEmpty
	private String address;
	@NotEmpty
	private String userName;
	@NotEmpty
	private String userPassword;
	@NotEmpty
	private String confirmPassword;
	@NotEmpty
	private String userRole;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthOfDate() {
		return birthOfDate;
	}

	public void setBirthOfDate(LocalDate birthOfDate) {
		this.birthOfDate = birthOfDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
}
