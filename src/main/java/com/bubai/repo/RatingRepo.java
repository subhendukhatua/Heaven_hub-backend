package com.bubai.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bubai.model.Rating;

public interface RatingRepo extends JpaRepository<Rating, Long> {
	
	
	@Query("SELECT r From Rating r WHERE r.product.id = :productId")
	public List<Rating>getAllRatingsByProductId(@Param("productId")Long productId);
}
