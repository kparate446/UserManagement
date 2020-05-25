package com.bridgelabz.usermanagement.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.usermanagement.configuration.PasswordConfiguration;
import com.bridgelabz.usermanagement.exception.InvalidUser;
import com.bridgelabz.usermanagement.message.MessageData;
import com.bridgelabz.usermanagement.message.MessageResponse;
import com.bridgelabz.usermanagement.model.User;
import com.bridgelabz.usermanagement.repository.PermissionsRepository;
import com.bridgelabz.usermanagement.repository.UserRepository;
import com.bridgelabz.usermanagement.response.Response;
import com.bridgelabz.usermanagement.utility.JwtToken;
import com.sun.istack.logging.Logger;

@Service
public class DashboardServicesImp implements IDashboardServices {
	
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
	
	@Override
	public Response getAllTimeAgeGroup(String token) {
		String userName = jwtToken.getToken(token);
		User user = userRepository.findByUserName(userName);
		if (user == null) {
			throw new InvalidUser(messageData.Invalid_User);
		}
//		if (user.getUserRole().equals("Admin")) {
			return new Response(200, "Age Group", userRepository.ageGroup());
//		}
//		LOGGER.warning("Invalid User");
//		throw new InvalidUser(messageData.Invalid_User);
//		return new Response(200, "krunal", false);
	}
	
}
