package com.bubai.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bubai.exception.OrderException;
import com.bubai.model.Address;
import com.bubai.model.Cart;
import com.bubai.model.CartItem;
import com.bubai.model.Order;
import com.bubai.model.OrderItem;
import com.bubai.model.User;
import com.bubai.repo.AddressRepo;
import com.bubai.repo.CartRepo;
import com.bubai.repo.OrderItemRepo;
import com.bubai.repo.OrderRepo;
import com.bubai.repo.UserRepo;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private AddressRepo addressRepo;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private OrderItemRepo orderItemRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	
	
	

	@Override
	public Order createOrder(User user, Address shippingAddress) {
		shippingAddress.setUser(user);
		Address address = addressRepo.save(shippingAddress);
		user.getAddresses().add(address);
		userRepo.save(user);
		
		Cart cart = cartService.findUserCart(user.getId());
		List<OrderItem>orderItems = new ArrayList<OrderItem>();
		
		for(CartItem item:cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			
			orderItem.setPrice(item.getPrice());
			orderItem.setDiscountedPrice(item.getDiscountedPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			
			
			OrderItem createdOrderItem = orderItemRepo.save(orderItem);
			orderItems.add(createdOrderItem);
			
			
		}
		
		Order createdOrder = new Order();
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
		createdOrder.setDiscount(cart.getDiscount());
		createdOrder.setTotalItem(cart.getTotalItem());
		createdOrder.setShippingAddress(shippingAddress);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus("PENDING");
		createdOrder.getPaymentDetails().setPaymentStatus("PENDING");
		createdOrder.setCreatedAt(LocalDateTime.now());
		
		Order savedOrder = orderRepo.save(createdOrder);
		
		for(OrderItem item:orderItems) {
			item.setOrder(savedOrder);
			orderItemRepo.save(item);
		}
		return savedOrder;
	}

	@Override
	public Order findOrderById(Long userId) throws OrderException {
		Optional<Order>opt = orderRepo.findById(userId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new OrderException("Order not exist with id "+userId);
	}

	@Override
	public List<Order> usersOrderHistory(Long userId) {
		List<Order>orders = orderRepo.getUsersOrders(userId);
		return orders;
	}

	@Override
	public Order placedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setPaymentStatus("COMPLETED");
		return order;
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");
		return orderRepo.save(order);
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("SHIPPED");
		return orderRepo.save(order);
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("DELIVERED");
		return orderRepo.save(order);
	}

	@Override
	public Order canceledOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CANCELlED");
		return orderRepo.save(order);
	}

	@Override
	public List<Order> getAllOrders() {
		
		return orderRepo.findAll();
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		orderRepo.deleteById(orderId);
		
	}

}
