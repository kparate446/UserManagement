package com.bridgelabz.usermanagement.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.usermanagement.dto.ForgotPasswordDto;
import com.bridgelabz.usermanagement.dto.LoginDto;
import com.bridgelabz.usermanagement.dto.RegistrationDto;
import com.bridgelabz.usermanagement.dto.ResetPasswordDto;
import com.bridgelabz.usermanagement.dto.UpdateUserDto;
import com.bridgelabz.usermanagement.response.Response;
import com.bridgelabz.usermanagement.services.IDashboardServices;
import com.bridgelabz.usermanagement.services.IUserService;

@RestController
@RequestMapping("/userapi")
public class UserController {
	@Autowired
	private IUserService userService;
	@Autowired
	private IDashboardServices dashboardService;

	/**
	 * Purpose :- Registration user
	 * 
	 * @param registrationDto :- Access the registrationDto data
	 * @return :- Response
	 */
	@PostMapping("/addusers")
	public ResponseEntity<Response> addUser(@Valid @RequestBody RegistrationDto registrationDto) {
		Response response = userService.addUser(registrationDto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/**
	 * Purpose :- Verified user
	 * 
	 * @param token :- Verified the Token
	 * @return :- Response
	 */
	@PostMapping("/verification")
	public ResponseEntity<String> verification(@RequestHeader String token) {
		Response response = userService.verifiedUser(token);
		return new ResponseEntity<String>(response.getMessage(), HttpStatus.OK);
	}

	/**
	 * Purpose :- Login Users
	 * 
	 * @param loginDto :- Access the loginDto data
	 * @return :- Response
	 */
	@PostMapping("/loginusers")
	public ResponseEntity<Response> loginUser(@Valid @RequestBody LoginDto loginDto) {
		Response response = userService.loginUser(loginDto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param updateUserDto
	 * @return
	 */
	@PutMapping("/updateusers")
	public ResponseEntity<Response> updateUser(@RequestHeader String token,
			@Valid @RequestBody UpdateUserDto updateUserDto) {
		Response response = userService.updateUser(token, updateUserDto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param userId
	 * @return
	 */
	@DeleteMapping("/deleteuser/{userId}")
	public ResponseEntity<Response> deleteuser(@RequestHeader String token, @PathVariable int userId) {
		Response response = userService.deleteUsers(token, userId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/**
	 * @param token
	 * @return
	 */
	@GetMapping("/getUsers")
	public ResponseEntity<Response> showUsers(@RequestHeader String token) {
		Response response = userService.getUserList(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/**
	 * Purpose :- Forgot Password
	 * 
	 * @param forgotPasswordDto :- Access the forgotPasswordDto data
	 * @return :- Response
	 */
	@PostMapping("/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@Valid @RequestBody ForgotPasswordDto forgotPasswordDto) {
		Response response = userService.forgotPassword(forgotPasswordDto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/**
	 * Purpose :- Reset Password
	 * @param token            :- Verified the Token
	 * @param resetPasswordDto :- Access the resetPasswordDto
	 * @return :- Response
	 */
	@PostMapping("/resetpassword")
	public ResponseEntity<Response> resetPassword(@RequestHeader String token, ResetPasswordDto resetPasswordDto) {
		Response response = userService.resetPassword(token, resetPasswordDto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/**
	 * @param token
	 * @return
	 */
	@PostMapping("/addpermission")
	public ResponseEntity<Response> permission(@RequestHeader String token) {
		Response response = userService.addPermission(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param file
	 * @return
	 */
	@PostMapping("/uploadedProfile")
	public ResponseEntity<String> uploadedProfile(@RequestHeader String token, @RequestHeader MultipartFile file) {
		Response response = userService.uploadProfilePic(token, file);
		return new ResponseEntity<String>(response.getMessage(), HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param name
	 * @return
	 */
	@GetMapping("/findByFirstName/{name}")
	public ResponseEntity<Response> findByfirstName(@RequestHeader String token, @PathVariable String name) {
		Response response = userService.SearchUser(token, name);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/**
	 * @param token
	 * @return
	 */
	@PostMapping("/logoutUser")
	public ResponseEntity<Response> logout(@RequestHeader String token) {
		Response response = userService.logout(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	/**
	 * @param token
	 * @return
	 */
	@GetMapping("/totalUsers")
	public ResponseEntity<Response> totalUsers(@RequestHeader String token) {
		Response response = userService.totalUser(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	/**
	 * @param token
	 * @return
	 */
	@GetMapping("/activeUsers")
	public ResponseEntity<Response> activeUsers(@RequestHeader String token) {
		Response response = userService.activeUsers(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	/**
	 * @param token
	 * @return
	 */
	@GetMapping("/inActiveUsers")
	public ResponseEntity<Response> inActiveUsers(@RequestHeader String token) {
		Response response = userService.inActiveUsers(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	/**
	 * @param token
	 * @return
	 */
	@GetMapping("/onlineUsers")
	public ResponseEntity<Response> onlineUsers(@RequestHeader String token) {
		Response response = userService.onlineUsers(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	/**
	 * @param token
	 * @return
	 */
	@GetMapping("/sortByDate")
	public ResponseEntity<Response> sortByRegistrationDate(@RequestHeader String token) {
		Response response = userService.sortByRegistrationDate(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	/**************************************************************/
	/**
	 * @param token
	 * @return
	 */
	@GetMapping("/getAllTimeGender")
	public ResponseEntity<Response> getAllTimeGender(@RequestHeader String token) {
		Response response = dashboardService.getAllTimeAgeGroup(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	/**
	 * @param token
	 * @return
	 */
	@GetMapping("/getAllLocation")
	public ResponseEntity<Response> getAllLocation(@RequestHeader String token) {
		Response response = dashboardService.getAllLocation(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	/**
	 * @param token
	 * @return
	 */
	@GetMapping("/getAllGender")
	public ResponseEntity<Response> getAllGender(@RequestHeader String token) {
		Response response = dashboardService.getAllGender(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
