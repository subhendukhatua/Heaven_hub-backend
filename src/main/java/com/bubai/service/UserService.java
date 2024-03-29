package com.bubai.service;


import com.bubai.exception.UserException;
import com.bubai.model.User;

public interface UserService {
	
	public User findUserById(Long userId)throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
	
	
	
	

}
