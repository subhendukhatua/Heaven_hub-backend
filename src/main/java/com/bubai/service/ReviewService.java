package com.bubai.service;

import java.util.List;

import com.bubai.exception.ProductException;
import com.bubai.model.Review;
import com.bubai.model.User;
import com.bubai.request.ReviewRequest;

public interface ReviewService {
	
	public Review createReview(ReviewRequest req, User user)throws ProductException;
	
	public List<Review> getAllReviewByProductId(Long productid);
}
