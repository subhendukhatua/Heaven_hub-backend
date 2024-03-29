package com.bubai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bubai.exception.CartItemException;
import com.bubai.exception.UserException;
import com.bubai.model.CartItem;
import com.bubai.model.User;
import com.bubai.response.ApiResponse;
import com.bubai.service.CartItemService;
import com.bubai.service.UserService;

@RestController
@RequestMapping("/api/cartItem")
public class CartItemController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private CartItemService cartItemService;
	
	@DeleteMapping("/{cartItemId}")
	public ResponseEntity<ApiResponse>deleteCartItem(@PathVariable Long cartItemId,@RequestHeader("Authorization")String jwt)throws UserException,CartItemException{
		User user = userService.findUserProfileByJwt(jwt);
		cartItemService.removeCartItem(user.getId(), cartItemId);
		ApiResponse response = new ApiResponse();
		response.setMessage("Dear "+user.getFirstName()+" "+user.getLastName()+" your cartItem with id "+ cartItemId+" deleted successfully from your cart.");
		response.setStatus(true);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}
	
	@PutMapping("/{cartItemId}")
	public ResponseEntity<CartItem>updateCartItem(@RequestBody CartItem cartItem, 
			@PathVariable Long cartItemId, 
			@RequestHeader("Authorization")String jwt)throws UserException, CartItemException{
		User user = userService.findUserProfileByJwt(jwt);
		CartItem updatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
		return new ResponseEntity<CartItem>(updatedCartItem, HttpStatus.ACCEPTED);
	}
	
	

}
