package com.bubai.service;

import java.util.ArrayList;
import java.util.List;

import com.bubai.exception.OrderException;
import com.bubai.model.Address;
import com.bubai.model.Order;
import com.bubai.model.User;

public interface OrderService {
	
	public Order createOrder(User user, Address shippingAddress);
	
	public Order findOrderById(Long userId)throws OrderException;
	
	public List<Order>usersOrderHistory(Long userId);
	
	public Order placedOrder(Long orderId) throws OrderException;
	
	public Order confirmedOrder(Long orderId) throws OrderException;
	
	public Order shippedOrder(Long orderId) throws OrderException;
	
	public Order deliveredOrder(Long orderId) throws OrderException;
	
	public Order canceledOrder(Long orderId) throws OrderException;
	
	public List<Order>getAllOrders();
	
	public void deleteOrder(Long orderId)throws OrderException;
	
	
}
