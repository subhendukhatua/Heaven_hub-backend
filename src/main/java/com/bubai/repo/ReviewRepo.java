package com.bubai.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bubai.model.Review;

public interface ReviewRepo extends JpaRepository<Review, Long> {

	@Query("SELECT rev from Review rev WHERE rev.product.id = :productId")
	public List<Review>gtAllReviewByProductId(@Param("productId") Long productId);
}
