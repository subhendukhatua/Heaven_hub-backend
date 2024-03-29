package com.bubai.service;

import com.bubai.exception.CartItemException;
import com.bubai.exception.UserException;
import com.bubai.model.Cart;
import com.bubai.model.CartItem;
import com.bubai.model.Product;

public interface CartItemService {
	
	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem)throws CartItemException, UserException;
	
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);
	
	
	public void removeCartItem(Long userId, Long cartItemId)throws CartItemException, UserException;
	
	public CartItem findCartItemById(Long cartItemId)throws CartItemException;
	
	
	

}
