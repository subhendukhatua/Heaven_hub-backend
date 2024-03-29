package com.bubai.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bubai.exception.ProductException;
import com.bubai.model.Product;
import com.bubai.model.Review;
import com.bubai.model.User;
import com.bubai.repo.ProductRepo;
import com.bubai.repo.ReviewRepo;
import com.bubai.request.ReviewRequest;

@Service
public class ReviewServiceImpl implements ReviewService{
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ReviewRepo reviewRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	

	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {
		Product product = productService.findProductById(req.getProductId());
		
		Review review = new Review();
		review.setProduct(product);
		review.setUser(user);
		review.setReview(req.getReview());
		review.setCreatedAt(LocalDateTime.now());
		
		return reviewRepo.save(review);
	}

	@Override
	public List<Review> getAllReviewByProductId(Long productid) {
		// TODO Auto-generated method stub
		return reviewRepo.gtAllReviewByProductId(productid);
	}

}
