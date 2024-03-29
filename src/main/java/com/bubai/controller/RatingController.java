package com.bubai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bubai.exception.ProductException;
import com.bubai.exception.UserException;
import com.bubai.model.Rating;
import com.bubai.model.User;
import com.bubai.request.RatingRequest;
import com.bubai.service.RatingService;
import com.bubai.service.UserService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
	
	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/create")
	public ResponseEntity<Rating>createRating(@RequestBody RatingRequest ratingRequest, 
			@RequestHeader("Authorization")String jwt)throws UserException,ProductException{
		User user = userService.findUserProfileByJwt(jwt);
		
		Rating rating = ratingService.createRating(ratingRequest, user);
		return new ResponseEntity<Rating>(rating, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Rating>>getProductRatings(@PathVariable Long productId, 
			@RequestHeader("Authorization")String jwt)throws UserException, ProductException{
		User user = userService.findUserProfileByJwt(jwt);
		List<Rating> ratings = ratingService.getProductRatings(productId);
		
		return new ResponseEntity<List<Rating>>(ratings, HttpStatus.OK);
		
	}

}
