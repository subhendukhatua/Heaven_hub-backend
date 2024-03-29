package com.bubai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bubai.model.OrderItem;
import com.bubai.repo.OrderItemRepo;

@Service
public class OrderItemServiceImpl implements OrderItemService{
	@Autowired
	private OrderItemRepo orderItemRepo;
	

	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		
		return orderItemRepo.save(orderItem);
	}

}
