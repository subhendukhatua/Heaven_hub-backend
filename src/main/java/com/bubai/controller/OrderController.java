package com.bubai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bubai.exception.OrderException;
import com.bubai.exception.UserException;
import com.bubai.model.Address;
import com.bubai.model.Order;
import com.bubai.model.User;
import com.bubai.service.OrderService;
import com.bubai.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress, 
			@RequestHeader("Authorization")String jwt)throws UserException{
		
		User user = userService.findUserProfileByJwt(jwt);
		Order order = orderService.createOrder(user, shippingAddress);
		System.out.println("Order"+ order);
		
		return new ResponseEntity<Order>(order, HttpStatus.CREATED);
		
	}
	
	
	@GetMapping("/user")
	public ResponseEntity<List<Order>>userOrderHistory(@RequestHeader("Authorization")String jwt)throws UserException{
		User user = userService.findUserProfileByJwt(jwt);
		
		List<Order>orderHistory = orderService.usersOrderHistory(user.getId());
		
		return new ResponseEntity<List<Order>>(orderHistory, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Order>findOrderById(@PathVariable("id")Long orderId, 
			@RequestHeader("Authorization")String jwt)throws UserException,OrderException{
		User user = userService.findUserProfileByJwt(jwt);
		Order order = orderService.findOrderById(orderId);
		return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
	}
	
	
	
	
	
}
