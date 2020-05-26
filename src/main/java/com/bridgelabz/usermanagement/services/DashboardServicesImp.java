package com.bridgelabz.usermanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.usermanagement.configuration.PasswordConfiguration;
import com.bridgelabz.usermanagement.exception.InvalidUser;
import com.bridgelabz.usermanagement.message.MessageData;
import com.bridgelabz.usermanagement.model.User;
import com.bridgelabz.usermanagement.repository.UserRepository;
import com.bridgelabz.usermanagement.response.Response;
import com.bridgelabz.usermanagement.utility.JwtToken;
import com.sun.istack.logging.Logger;

@Service
public class DashboardServicesImp implements IDashboardServices {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MessageData messageData;
	@Autowired
	private JwtToken jwtToken;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	PasswordConfiguration passConfig;

	private static final Logger LOGGER = Logger.getLogger(UserServiceImp.class);

	@Override
	public Response getAllTimeAgeGroup(String token) {
		String userName = jwtToken.getToken(token);
		User user = userRepository.findByUserName(userName);
		if (user == null) {
			throw new InvalidUser(messageData.Invalid_User);
		}
		// if (user.getUserRole().equals("Admin")) {
		LOGGER.info("Gender has been counted");
		return new Response(200, "Gender has been counted", userRepository.ageGroup());
		// }
		// LOGGER.warning("Invalid User");
		// throw new InvalidUser(messageData.Invalid_User);
	}

	@Override
	public Response getAllLocation(String token) {
		String userName = jwtToken.getToken(token);
		User user = userRepository.findByUserName(userName);
		if (user == null) {
			throw new InvalidUser(messageData.Invalid_User);
		}
		LOGGER.info("successfully shows how many people there are in the country");
		return new Response(200, "successfully shows how many people there are in the country", userRepository.location());
	}

	@Override
	public Response getAllGender(String token) {
		String userName = jwtToken.getToken(token);
		User user = userRepository.findByUserName(userName);
		if (user == null) {
			throw new InvalidUser(messageData.Invalid_User);
		}
		LOGGER.info("Gender has been counted");
		return new Response(200, "Gender has been counted", userRepository.gender());
	}

}
