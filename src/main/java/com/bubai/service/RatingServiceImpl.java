package com.bubai.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bubai.exception.ProductException;
import com.bubai.model.Product;
import com.bubai.model.Rating;
import com.bubai.model.User;
import com.bubai.repo.RatingRepo;
import com.bubai.request.RatingRequest;

@Service
public class RatingServiceImpl implements RatingService{
	
	@Autowired
	private RatingRepo ratingRepo;
	@Autowired
	private ProductService productService;

	@Override
	public Rating createRating(RatingRequest req, User user) throws ProductException {
		Product product = productService.findProductById(req.getProductId());
		
		Rating rating = new Rating();
		
		rating.setProduct(product);
		rating.setRating(req.getRating());
		rating.setUser(user);
		rating.setCreatedAt(LocalDateTime.now());
		
		return ratingRepo.save(rating);
	}

	@Override
	public List<Rating> getProductRatings(Long productId) {
		
		return ratingRepo.getAllRatingsByProductId(productId);
	}

}
