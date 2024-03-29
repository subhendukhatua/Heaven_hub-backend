package com.bubai.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bubai.model.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long>{

}
