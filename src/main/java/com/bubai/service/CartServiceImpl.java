package com.bubai.service;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bubai.exception.ProductException;
import com.bubai.model.Cart;
import com.bubai.model.CartItem;
import com.bubai.model.Product;
import com.bubai.model.User;
import com.bubai.repo.CartItemRepo;
import com.bubai.repo.CartRepo;
import com.bubai.request.AddItemRequest;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	 private CartRepo cartRepo;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ProductService productService;
	
	
	@Override
	public Cart createCart(User user) {
		
		Cart cart = new Cart();
		cart.setUser(user);
		return cartRepo.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
		Cart cart = cartRepo.findByUserId(userId);
		Product product = productService.findProductById(req.getProductId());
		
		CartItem isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
		
		if (isPresent==null) {
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(req.getQuantity());
			cartItem.setUserId(userId);
			
			int price = req.getQuantity()* product.getQuantity();
			cartItem.setPrice(price);
			cartItem.setSize(req.getSize());
			
			CartItem createdCartItem = cartItemService.createCartItem(cartItem);
			cart.getCartItems().add(createdCartItem);
			
		}
		return "Item added to cart";
	}

	@Override
	public Cart findUserCart(Long userId) {
		Cart cart = cartRepo.findByUserId(userId);
		
		int totalPrice = 0;
		int totalDiscountedPrice = 0;
		int totalItem = 0;
		
		for(CartItem cartItem:cart.getCartItems()) {
			totalPrice = totalPrice + cartItem.getPrice();
			totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
			totalItem = totalItem + cartItem.getQuantity();
		}
		
		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setDiscount(totalPrice - totalDiscountedPrice);
		
		return cartRepo.save(cart);
	}

}
