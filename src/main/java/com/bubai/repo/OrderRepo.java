package com.bubai.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bubai.model.Order;

public interface OrderRepo extends JpaRepository<Order, Long>{
	
	@Query("SELECT o from Order o WHERE o.user.id = :userId AND (o.OrderStatus = 'PLACED' OR o.OrderStatus = 'CONFIRMED' OR o.OrderStatus = 'SHIPPED' OR o.OrderStatus = 'DELIVERED')")
	public List<Order>getUsersOrders(@Param("userId")Long userId);

}
