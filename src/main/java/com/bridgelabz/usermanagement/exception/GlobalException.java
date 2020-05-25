package com.bridgelabz.usermanagement.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.usermanagement.message.MessageData;
import com.bridgelabz.usermanagement.response.Response;

/**
 * @author :- Krunal Parate
 * Purpose :- Global Exception
 */
@ControllerAdvice
public class GlobalException {
	@Autowired
	MessageData message;
	@ExceptionHandler(InvalidPassword.class)
	public ResponseEntity<Response> InvalidPassword(Exception e){
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request)
				,e.getMessage(),"Please Try Again"),HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserAlreadyPresent.class)
	public ResponseEntity<Response> UserAlreadyPresent(Exception e){
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request)
				,e.getMessage(),"Please Try Again"),HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidUser.class)
	public ResponseEntity<Response> InvalidUser(Exception e){
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request)
				,e.getMessage(),"Please Try Again"),HttpStatus.BAD_REQUEST);
	}
}
