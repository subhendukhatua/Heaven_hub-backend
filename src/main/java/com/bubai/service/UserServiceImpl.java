package com.bubai.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bubai.config.Jwtprovider;
import com.bubai.exception.UserException;
import com.bubai.model.User;
import com.bubai.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private Jwtprovider jwtprovider;

	@Override
	public User findUserById(Long userId) throws UserException {
		Optional<User>user = userRepo.findById(userId);
		if(user.isPresent()) {
			return user.get();
		}
		throw new UserException("User not found with id : "+userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		System.out.println("inside user service impl");
		String email = jwtprovider.getEmailFromToken(jwt);
		User user = userRepo.findByEmail(email);
		
		if(user==null) {
			throw new UserException("User Not Found With this Email : "+email);
		}
		return user;
	}

}
