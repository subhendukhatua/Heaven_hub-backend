package com.bubai.service;

import java.util.List;

import com.bubai.exception.ProductException;
import com.bubai.model.Rating;
import com.bubai.model.User;
import com.bubai.request.RatingRequest;

public interface RatingService {
	
	public Rating createRating (RatingRequest req, User user) throws ProductException;
	
	public List<Rating>getProductRatings(Long productId);
	

}
