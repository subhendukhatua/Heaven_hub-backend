package com.bubai.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bubai.exception.CartItemException;
import com.bubai.exception.UserException;
import com.bubai.model.Cart;
import com.bubai.model.CartItem;
import com.bubai.model.Product;
import com.bubai.model.User;
import com.bubai.repo.CartItemRepo;
import com.bubai.repo.CartRepo;

@Service
public class CartItemServiceImpl implements CartItemService{
	
	@Autowired
	private CartItemRepo cartItemRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartRepo cartRepo;
	
	

	@Override
	public CartItem createCartItem(CartItem cartItem) {
		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()* cartItem.getQuantity());
		
		CartItem createdCartItem = cartItemRepo.save(cartItem);
		
		return createdCartItem;
	}

	@Override
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
		
		CartItem item = findCartItemById(id);
		User user = userService.findUserById(item.getUserId());
		
		if (user.getId().equals(userId)) {
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity()* item.getProduct().getPrice());
			item.setDiscountedPrice(item.getProduct().getDiscountedPrice()* item.getQuantity());
			
		}
		
		return cartItemRepo.save(item);
	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
		CartItem cartItem = cartItemRepo.isCartItemExist(cart, product, size, userId);
		
		return cartItem;
	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
		CartItem cartItem = findCartItemById(cartItemId);
		User user = userService.findUserById(cartItem.getUserId());
		User regUser = userService.findUserById(userId);
		
		if(user.getId().equals(regUser.getId())) {
			cartItemRepo.deleteById(cartItemId);
		}
		else {
			throw new UserException("you cant remove another users item");
		}
		
		
		
	}

	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {
		Optional<CartItem>cartItem = cartItemRepo.findById(cartItemId);
		
		if (cartItem.isPresent()) {
			return cartItem.get();
		}
		else {
			throw new CartItemException("CartItem not found with id: "+cartItemId);
		}
		
	}

}
