package com.bubai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bubai.exception.UserException;
import com.bubai.model.User;
import com.bubai.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	
	
	@Autowired
	private UserService userService;
	
	
	
	@GetMapping("/profiles")
	public ResponseEntity<User>getUserProfileHandler(@RequestHeader("Authorization")String jwt)throws UserException{
		System.out.println("before funtion call");
		User user = userService.findUserProfileByJwt(jwt);
		System.out.println("After funtion call");
		return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
	}
}
