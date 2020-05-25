package com.bridgelabz.usermanagement.services;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.usermanagement.dto.ForgotPasswordDto;
import com.bridgelabz.usermanagement.dto.LoginDto;
import com.bridgelabz.usermanagement.dto.RegistrationDto;
import com.bridgelabz.usermanagement.dto.ResetPasswordDto;
import com.bridgelabz.usermanagement.dto.UpdateUserDto;
import com.bridgelabz.usermanagement.response.Response;

public interface IUserService {
	Response addUser(RegistrationDto registrationDto);
	Response verifiedUser(String token);
	Response loginUser(LoginDto loginDto);
	Response logout(String token);
	Response forgotPassword(ForgotPasswordDto forgotPasswordDto);
	Response resetPassword(String token,ResetPasswordDto resetPasswordDto);
	Response addPermission(String token);
//	Response getUserList();
	Response getUserList(String token);
	Response deleteUsers(String token, int id);
	Response uploadProfilePic(String token, MultipartFile file);
	Response SearchUser(String token,String firstName);
	Response updateUser(String token, UpdateUserDto updateUserDto);
	Response totalUser(String token);
	Response activeUsers(String token);
	Response inActiveUsers(String token);
	Response onlineUsers(String token);
	Response sortByRegistrationDate(String token);
}