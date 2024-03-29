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
import com.bubai.model.Review;
import com.bubai.model.User;
import com.bubai.request.ReviewRequest;
import com.bubai.service.ReviewService;
import com.bubai.service.UserService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReviewService reviewService;
	
	@PostMapping("/create")
	public ResponseEntity<Review>createReview(@RequestBody ReviewRequest request, 
			@RequestHeader("Authorization") String jwt)throws UserException, ProductException{
		
		User user = userService.findUserProfileByJwt(jwt);
		Review createReview = reviewService.createReview(request, user);
		return new ResponseEntity<Review>(createReview, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>>getProductReviews(@PathVariable Long productId)throws UserException, ProductException{
		List<Review>reviews = reviewService.getAllReviewByProductId(productId);
		return new ResponseEntity<List<Review>>(reviews, HttpStatus.ACCEPTED);
	}
	
	
}
