package com.bubai.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bubai.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
	
	public User findByEmail(String email);
	
	

}
