package com.bridgelabz.usermanagement.message;

import org.springframework.stereotype.Service;

@Service
public class MessageData {
//	private static final Logger LOGGER = Logger.getLogger(CollabratorServiceImp.class);
	
	/****************Status Exception************/
	public String validateUser = "Verified Email";	
	public String Bad_Request = "400";
	public String Success_Request = "200";
	public String Redirect_Request = "300";
	/***************Token Exception*************/
	public String Invalid_Token = "Invalid Token";
	public String Invalid_Password = "Password not match";
	public String Valid_Token = "Valid Token";
	public String Invalid_User = "Invalid user";
	public String userAlready_Present = "User are already present";
}
