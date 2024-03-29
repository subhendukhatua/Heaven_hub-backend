package com.bubai.service;

import com.bubai.exception.ProductException;
import com.bubai.model.Cart;
import com.bubai.model.User;
import com.bubai.request.AddItemRequest;

public interface CartService {
	
	public Cart createCart(User user);
	
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException;
	
	public Cart findUserCart(Long userId);
	

}
