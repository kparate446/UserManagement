package com.bridgelabz.usermanagement.services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.usermanagement.configuration.PasswordConfiguration;
import com.bridgelabz.usermanagement.dto.ForgotPasswordDto;
import com.bridgelabz.usermanagement.dto.LoginDto;
import com.bridgelabz.usermanagement.dto.RegistrationDto;
import com.bridgelabz.usermanagement.dto.ResetPasswordDto;
import com.bridgelabz.usermanagement.dto.UpdateUserDto;
import com.bridgelabz.usermanagement.exception.InvalidPassword;
import com.bridgelabz.usermanagement.exception.InvalidUser;
import com.bridgelabz.usermanagement.exception.UserAlreadyPresent;
import com.bridgelabz.usermanagement.message.MessageData;
import com.bridgelabz.usermanagement.message.MessageResponse;
import com.bridgelabz.usermanagement.model.Permissions;
import com.bridgelabz.usermanagement.model.User;
import com.bridgelabz.usermanagement.repository.PermissionsRepository;
//import com.bridgelabz.usermanagement.repository.UserDataRepository;
import com.bridgelabz.usermanagement.repository.UserRepository;
import com.bridgelabz.usermanagement.response.Response;
//import com.bridgelabz.usermanagement.utility.EmailSenderService;
import com.bridgelabz.usermanagement.utility.JwtToken;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.sun.istack.logging.Logger;

@Service
public class UserServiceImp implements IUserService {

	@Autowired
	private ModelMapper mapper;
	@Autowired
	private UserRepository userRepository;
	//	@Autowired
	//	private UserDataRepository userDataRepository;
	@Autowired
	private PermissionsRepository permissionsRepository;
	@Autowired
	private MessageData messageData;
	@Autowired
	private JwtToken jwtToken;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	PasswordConfiguration passConfig;
	@Autowired
	MessageResponse messageResponse;
	private SimpleMailMessage email;
	//	@Autowired
	//	private EmailSenderService emailSenderService;
	User user;
	private static final Logger LOGGER = Logger.getLogger(UserServiceImp.class);

	/** Add User */
	@Override
	public Response addUser(RegistrationDto registrationDto) {
		User checkEmail = userRepository.findByEmail(registrationDto.getEmail());
		if (checkEmail != null) {
			LOGGER.warning("User Already Present");
			throw new UserAlreadyPresent(messageData.userAlready_Present);
		}
		User user = mapper.map(registrationDto, User.class);
		System.out.println(registrationDto.getUserName());
		String token = jwtToken.generateToken(user.getEmail());
		System.out.println(token);
		// email = messageResponse.verifyMail(user.getEmail(), user.getFirstName(),
		// token);
		// emailSenderService.sendEmail(email);;
		// Set Encoded the password
		if (registrationDto.getUserPassword().equals(registrationDto.getConfirmPassword())) {
			user.setRegisterTime(new Date());
			user.setUserPassword(passwordEncoder.encode(registrationDto.getUserPassword()));
			userRepository.save(user);
			LOGGER.info("User Successfully Register");
			return new Response(200, "User Successfully Register", token);
		} else {
			LOGGER.warning("Invalid user");
			throw new InvalidPassword(messageData.Invalid_Password);
		}
	}

	/** Verified user */
	@Override
	public Response verifiedUser(String token) {
		String email = jwtToken.getToken(token);
		User user = userRepository.findByEmail(email);
		if (user == null) {
			System.out.println("Invalid User");
			LOGGER.warning("Invalid User");
			throw new InvalidUser(messageData.Invalid_User);
			// return new Response(400, "Invalid User", false);
		}
		user.setValidate(true);
		userRepository.save(user);
		LOGGER.info("Verified Successfully");
		return new Response(200, "Verified Successfully", token);
	}

	/** Login user */
	@Override
	public Response loginUser(LoginDto loginDto) {
		User user = userRepository.findByUserName(loginDto.getUserName());
		if (user == null) {
			LOGGER.warning("Invalid User");
			throw new InvalidUser(messageData.Invalid_User);
		}
		String token = jwtToken.generateToken(user.getUserName());
		System.out.println(token);
		if (user.isValidate()) {
			if (passConfig.encoder().matches(loginDto.getUserPassword(), user.getUserPassword())) {
				// email = messageResponse.verifyMail(user.getEmail(), user.getFirstName(),
				// token);
				// emailSenderService.sendEmail(email);
				// user.setLatestLoginTime(new Date());
				user.setLatestLoginTime(new Date());
				user.setLogout(false);
				userRepository.save(user);
				LOGGER.info("Login Successfull");
				return new Response(200, "Login Successfull", token);
			}
			LOGGER.warning("Invalid Password");
			return new Response(400, "Invalid Password", token);
		}
		LOGGER.warning("Invalid User");
		throw new InvalidUser(messageData.Invalid_User);
	}

	/** Logout User */
	@Override
	public Response logout(String token) {
		String userName = jwtToken.getToken(token);
		User user = userRepository.findByUserName(userName);
		if (user == null) {
			LOGGER.warning("Invalid User");
			throw new InvalidUser(messageData.Invalid_User);
		}
		user.setLogout(true);
		userRepository.save(user);
		LOGGER.info("Logout Successfully");
		return new Response(200, "Successfully Logout User", true);
	}

	/** Forgot Password */
	@Override
	public Response forgotPassword(ForgotPasswordDto forgotPasswordDto) {
		User user = userRepository.findByEmail(forgotPasswordDto.getEmail());
		if (user == null) {
			LOGGER.warning("Invalid User");
			throw new InvalidUser(messageData.Invalid_User);
		}
		if (user.isValidate()) {
			String token = jwtToken.generateToken(forgotPasswordDto.getEmail());
			// email = messageResponse.verifyMail(user.getEmail(), user.getFirstName(),
			// token);
			// emailSenderService.sendEmail(email);
			userRepository.save(user);
			System.out.println(token);
			LOGGER.info("Send the token in Email");
			return new Response(200, "Send the token in Email", token);
		}
		LOGGER.warning("Invalid User");
		throw new InvalidUser(messageData.Invalid_User);
	}

	/** Reset Password */
	@Override
	public Response resetPassword(String token, ResetPasswordDto resetPasswordDto) {
		String userName = jwtToken.getToken(token);
		System.out.println(email);
		User user = userRepository.findByUserName(userName);
		System.out.println(user);
		if (user == null) {
			LOGGER.warning("Invalid User");
			throw new InvalidUser(messageData.Invalid_User);
		} else {
			System.out.println(user.getEmail());
			if (resetPasswordDto.getUserPassword().equals(resetPasswordDto.getConfirmPassword())) {
				user.setUserPassword(passwordEncoder.encode(resetPasswordDto.getUserPassword()));
				userRepository.save(user);
				LOGGER.info("Password Reset Successfully");
				return new Response(200, "Password Reset Successfully", true);
			}
		}
		LOGGER.warning("Invalid Password");
		return new Response(400, "Invalid Password", token);
	}

	/** Add permission */
	@Override
	public Response addPermission(String token) {
		String name = jwtToken.getToken(token);
		User user = userRepository.findByEmail(name);
		System.out.println(user);
		if (user == null) {
			LOGGER.warning("Invalid User");
			throw new InvalidUser(messageData.Invalid_User);
		}
		Permissions permissions = new Permissions();
		if (user.getPermissions() != null) {
			LOGGER.warning("User have already permission");
			return new Response(400, "Already Permissions", false);
		}
		if (user.getUserRole().equals("Active")) {
			permissions.setAddDashboard(true);
			permissions.setDeleteDashboard(true);
			permissions.setModifyDashboard(true);
			permissions.setReadDashboard(true);
			permissions.setAddSetting(true);
			permissions.setDeleteSetting(true);
			permissions.setModifySetting(true);
			permissions.setReadSetting(true);
			permissions.setAddUserInformation(true);
			permissions.setDeleteUserInformation(true);
			permissions.setModifyUserInformation(true);
			permissions.setReadUserInformation(true);
			permissions.setUser(user);
			permissionsRepository.save(permissions);
			LOGGER.info("Successfully Added Admin Permission");
			return new Response(200, "Sucessfully Added Admin Permisssion ", permissions);
		} else {
			permissions.setAddUserInformation(true);
			permissions.setDeleteUserInformation(true);
			permissions.setModifyUserInformation(true);
			permissions.setReadUserInformation(true);
			permissions.setUser(user);
			permissionsRepository.save(permissions);
			LOGGER.info("Successfully Added User Permission");
			return new Response(200, "Sucessfully Added User Permisssion ", permissions);
		}
	}

	//	@Override
	//	public Response getUserList(String token) {
	//		if(userRepository.findAll() == null) {
	//			throw new InvalidUser(messageData.Invalid_User);
	//		}
	//		List<User> userList = userRepository.findAll();
	//		UserData userDataEntity = new UserData();
	//		for(User userdata : userList) {
	//			userDataEntity.setProfilePic(userdata.getProfilePic());
	//			userDataEntity.setBirthOfDate(userdata.getBirthOfDate());
	//			userDataEntity.setEmail(userdata.getEmail());
	//			userDataEntity.setUserRole(userdata.getUserRole());
	//			userDataRepository.save(userDataEntity);
	//		}	
	//		return new Response(200, "Sucessfully showing the user",userDataRepository.findAll());
	//	}		 

	// List<UserData> userDataList =userDataRepository.findAll();
	// List<UserData> userData = UserData.stream().filter(e -> (e.)))
	// .collect(Collectors.toList());
	// return new Response(200, "Successfully showing the
	// use,userDataRepository.findAll()r List ",userDataRepository.findAll());
	// String userName = jwtToken.getToken(token);
	// User user = userRepository.findByUserName(userName);
	// if (user == null) {
	// throw new InvalidUser(messageData.Invalid_User);
	// }
	// List<Permissions> user1 = permissionsRepository.findAll().stream()
	// .filter(e -> e.getUser().getId() ==
	// user.getId()).collect(Collectors.toList());
	// return new Response(200, "Sucessfully showing the user List ", user1);
	// }
	// List<Notes> list = getNote.stream().filter(note ->
	// (note.getDiscription().contains(description)) ||
	// (note.getTitle().contains(description)) )
	// .collect(Collectors.toList());

	/** Delete Users */
	@Override
	public Response deleteUsers(String token, int id) {
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		System.out.println(id);
		System.out.println("user Id--------->" + user.getId());
		if (user == null) {
			LOGGER.warning("Invalid User");
			throw new InvalidUser(messageData.Invalid_User);
		}
		if (id == user.getId()) {
			userRepository.deleteById(id);
			LOGGER.info("Deleted USer Successfully");
			return new Response(200, "Deleted User Successfully", true);
		}
		LOGGER.warning("Invalid User");
		throw new InvalidUser(messageData.Invalid_User);
	}

	/** Uploaded the Profile Pic */
	@Override
	public Response uploadProfilePic(String token, MultipartFile file) {
		String email = jwtToken.getToken(token);
		// check whether user is present or not
		User user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUser(messageData.Invalid_User);
		}
		// file is not selected to upload
		if (file.isEmpty())
			return new Response(400, "File is Empty", false);
		// throw new FileIsEmpty(messageData.File_Is_Empty);
		File uploadFile = new File(file.getOriginalFilename());
		try {
			// save the file backup
			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
			try {
				outputStream.write(file.getBytes());
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Connection of Closet cloudinary properties
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", "dmlqjysiv", "api_key",
				"242443158528625", "api_secret", "q9p9sxtwVI-kSM5CVt-Yrc4_B0c"));
		Map<?, ?> uploadProfile;
		try {
			// this upload the image on cloudinary ->Query -> Mapped
			uploadProfile = cloudinary.uploader().upload(uploadFile, ObjectUtils.emptyMap());
		} catch (IOException e) {
			LOGGER.warning("File Not Uploaded");
			return new Response(200, "File Not Uploaded", true);
			// throw new FileNotUploadedException(messageData.File_Not_Upload);
		}
		// Set profile picture in Url type in table
		user.setProfilePic(uploadProfile.get("secure_url").toString());
		userRepository.save(user);
		// LOGGER.info("Successfully uploaded the profile picture");
		LOGGER.info("Uploaded profile picture successfully");
		return new Response(200, "Uploaded Profile picture Successfully", true);
	}

	/** Search User */
	@Override
	public Response SearchUser(String token, String firstName) {
		List<User> userList = userRepository.findAll();
		String userName = jwtToken.getToken(token);
		System.out.println(firstName);
		User user = userRepository.findByUserName(userName);
		if (user == null) {
			LOGGER.warning("Invalid User");
			throw new InvalidUser(messageData.Invalid_User);
		} else {
			System.out.println(user.getFirstName());
			System.out.println(userList);
			List<User> list = userList.stream()
					.filter(users -> users.getFirstName().contains(firstName)
							|| (user.getMiddleName().contains(firstName)) || (user.getLastName().contains(firstName))
							|| (user.getEmail().contains(firstName)))
					.collect(Collectors.toList());
			LOGGER.info("Search the User");
			return new Response(200, " Searching the User", list);// list.size()
		}
	}

	/** Show User List */
	@Override
	public Response getUserList(String token) {
		String userName = jwtToken.getToken(token);
		// check whether user is present or not
		User user = userRepository.findByUserName(userName);
		if (user == null) {
			LOGGER.warning("Invalid User");
			throw new InvalidUser(messageData.Invalid_User);
		}
		if (userRepository.findAll() == null) {
			LOGGER.warning("Invalid User");
			throw new InvalidUser(messageData.Invalid_User);
		} else {
			System.out.println(userRepository.findAll());
			System.out.println(userRepository.findAll().size());
			LOGGER.info("Successfully showing the User");
			return new Response(200, "Successfully showing the user", userRepository.findAll());
		}	
	}

	/** Update User */
	@Override
	public Response updateUser(String token, UpdateUserDto updateUserDto) {
		String userName = jwtToken.getToken(token);
		User user = userRepository.findByUserName(userName);
		if (user == null) {
			LOGGER.warning("Invalid User");
			throw new InvalidUser(messageData.Invalid_User);
		}
		user.setFirstName(updateUserDto.getFirstName());
		user.setMiddleName(updateUserDto.getMiddleName());
		user.setLastName(updateUserDto.getLastName());
		user.setAddress(updateUserDto.getAddress());
		user.setBirthOfDate(updateUserDto.getBirthDate());
		user.setCountry(updateUserDto.getCountry());
		user.setPhoneNumber(updateUserDto.getPhoneNumber());
		userRepository.save(user);
		LOGGER.info("Successfully update the User");
		return new Response(200, "Successfully update the user", true);
	}

	/**Number of Total User */
	@Override
	public Response totalUser(String token) {
		String userName = jwtToken.getToken(token);
		// check whether user is present or not
		User user = userRepository.findByUserName(userName);
		if (user == null) {
			LOGGER.warning("Invalid User");
			throw new InvalidUser(messageData.Invalid_User);
		}
		LOGGER.info("Successfully showing the total number of User");
		return new Response(200, "Successfully showing the total number of User", userRepository.findAll().size());
	}
	/** Number of Active Users*/
	public Response activeUsers(String token) {
		String userName = jwtToken.getToken(token);
		user = userRepository.findByUserName(userName);
		if (user == null) {
			LOGGER.warning("Invalid User");
			return new Response(400, "Invalid User", false);
		}	
		List<User> activeUser = userRepository.findAll();
		if (user == null) {
			LOGGER.warning("Invalid User");
			throw new InvalidUser(messageData.Invalid_User);
		}
		List<User> active = activeUser.stream().filter(note1
				->note1.isValidate() == true).collect(Collectors.toList());
		LOGGER.info("Successfully showing the Active User");
		return new Response(200, "Successfully Showing the Active Users",active.size());
	}
	/** Number of InActive Users*/
	public Response inActiveUsers(String token) {
		String userName = jwtToken.getToken(token);
		user = userRepository.findByUserName(userName);
		if (user == null) {
			LOGGER.warning("Invalid User");
			return new Response(400, "Invalid User", false);
		}	
		List<User> inActiveUser = userRepository.findAll();
		if (user == null) {
			LOGGER.warning("Invalid User");
			throw new InvalidUser(messageData.Invalid_User);
		}
		List<User> inActive = inActiveUser.stream().filter(note1
				->note1.isValidate() == false).collect(Collectors.toList());
		LOGGER.info("Successfully showing the Inactive User");
		return new Response(200, "Successfully Showing the InActive Users",inActive.size());
	}

	/** Number of Online Users*/
	public Response onlineUsers(String token) {
		String userName = jwtToken.getToken(token);
		user = userRepository.findByUserName(userName);
		if (user == null) {
			LOGGER.warning("Invalid User");
			return new Response(400, "Invalid User", false);
		}	
		List<User> onlineUser = userRepository.findAll();
		if (user == null) {
			LOGGER.warning("Invalid User");
			throw new InvalidUser(messageData.Invalid_User);
		}
		List<User> online = onlineUser.stream().filter(note1
				->note1.getLatestLoginTime() == user.getLatestLoginTime()).collect(Collectors.toList());
		LOGGER.info("Successfully showing the Online Users");
		return new Response(200, "Successfully Showing the Online Users",online.size());
	}
	/** Sort by Registration Date */
	@Override
	public Response sortByRegistrationDate(String token) {
		String userName = jwtToken.getToken(token);
		User user = userRepository.findByUserName(userName);
		if(user == null) {
			LOGGER.warning("Invalid User");
			return new Response(400, "Invalid User", false);
		}
		List<User> sort = userRepository.findAll();
		List<User> users = sort.stream().sorted((list1,list2) -> list1.getRegisterTime().compareTo(list2.getRegisterTime())).collect(Collectors.toList());
		return new Response(200, "Sort by Registration date in Ascending order",users);
	}

	@Override
	public Response findByGender(String token) {
		String userName = jwtToken.getToken(token);
		User user = userRepository.findByUserName(userName);
		if(user == null) {
			LOGGER.warning("Invalid User");
			return new Response(400, "Invalid User", false);
		}
		User gender = userRepository.findByGender(user.getGender());
//		List<User> user = gender.get(male)
		return null;
	}
	
}
