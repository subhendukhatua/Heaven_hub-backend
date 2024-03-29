package com.bubai.controller;


import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bubai.config.Jwtprovider;
import com.bubai.exception.UserException;
import com.bubai.model.Cart;
import com.bubai.model.User;
import com.bubai.repo.UserRepo;
import com.bubai.request.LoginRequest;
import com.bubai.response.AuthResponse;
import com.bubai.service.CartService;
import com.bubai.service.CustomUserServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private Jwtprovider jwtprovider;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomUserServiceImpl customUserServiceImpl;
	
	@Autowired
	private CartService cartService;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse>createUserHandler(@RequestBody User user)throws UserException{
		
		String email= user.getEmail();
		String password  = user.getPassword();
		String firstName= user.getFirstName();
		String lastName = user.getLastName();
		
		User isEmailExist = userRepo.findByEmail(email);
		
		if(isEmailExist!=null) {
			throw new UserException("Email Is Already Exists");
		}
		
		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setFirstName(firstName);
		createdUser.setLastName(lastName);
		createdUser.setCreatedAt(LocalDateTime.now());
		
		User savedUser = userRepo.save(createdUser);
		Cart cart = cartService.createCart(savedUser);
		
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
		
		SecurityContextHolder .getContext().setAuthentication(authentication);

		String token = jwtprovider.generateToken(authentication);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("Dear "+ savedUser.getFirstName()+" "+ savedUser.getLastName()+" You Have Successfully Signed Up");
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
		
	}
	
	
	
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse>loginUserhandler(@RequestBody LoginRequest loginRequest){
		
		String username = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		
		Authentication authentication = authenticate(username, password);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtprovider.generateToken(authentication);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("Signin Success with email- "+loginRequest.getEmail() );	
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
		
		
		
	}


	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = customUserServiceImpl.loadUserByUsername(username);
		
		if(userDetails==null) {
			throw new BadCredentialsException("Invalid Username");
		}
		
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password");
		}
		return new UsernamePasswordAuthenticationToken( userDetails, null, userDetails.getAuthorities());
	}
	

}
