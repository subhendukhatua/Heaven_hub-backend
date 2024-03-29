package com.bubai.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bubai.exception.OrderException;
import com.bubai.exception.UserException;
import com.bubai.model.Order;
import com.bubai.repo.OrderRepo;
import com.bubai.response.ApiResponse;
import com.bubai.response.PaymentLinkResponse;
import com.bubai.service.OrderService;
import com.bubai.service.UserService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/api")
public class PaymentController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderRepo orderRepo;
	
	@PostMapping("/payments/{orderId}")
	public ResponseEntity<PaymentLinkResponse>createPaymentLink(@PathVariable Long orderId,
			@RequestHeader("Authorization")String jwt )throws RazorpayException, UserException, OrderException{
		Order order = orderService.findOrderById(orderId);
		
		try {
			RazorpayClient razorpayClient = new RazorpayClient("rzp_test_gacVDRvqeYJ21B", "dcb0tt8CBdXLzqQM2woDhsiF");
			
			JSONObject paymentLinkRequest = new JSONObject();
			paymentLinkRequest.put("amount", order.getTotalPrice()*100);
			paymentLinkRequest.put("currency","INR");
			
			JSONObject customer = new JSONObject();
			customer.put("name", order.getUser().getFirstName()+" "+order.getUser().getLastName());
			customer.put("contact", order.getUser().getMobile());
			customer.put("email", order.getUser().getEmail());
			
			paymentLinkRequest.put("customer", customer);
			
			JSONObject notify = new JSONObject();
			notify.put("sms", true);
			notify.put("email", true);
			
			paymentLinkRequest.put("notify", notify);
			
			paymentLinkRequest.put("callback_url", "http://localhost:4200/payment-success?order_id="+order.getId());
			paymentLinkRequest.put("callback_method", "get");
			
			
			PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);
			
			String paymentLinkId = paymentLink.get("id");
			String paymentLinkUrl = paymentLink.get("short_url");
			
			PaymentLinkResponse response = new PaymentLinkResponse();
			response.setPayment_link_id(paymentLinkId);
			response.setPayment_link_url(paymentLinkUrl);
			
			return new ResponseEntity<PaymentLinkResponse>(response, HttpStatus.CREATED) ;
			
		} catch (Exception e) {
			throw new RazorpayException(e.getMessage());
		}
		
		
	}
	
	@GetMapping("/payments")
	public ResponseEntity<ApiResponse>updatePayment(@RequestParam(name = "payment_id")String paymentId,
			@RequestParam(name = "order_id")Long orderId)throws RazorpayException, OrderException{
		
		RazorpayClient razorpayClient = new RazorpayClient("rzp_test_gacVDRvqeYJ21B", "dcb0tt8CBdXLzqQM2woDhsiF");
		Order order = orderService.findOrderById(orderId);
		
		try {
			Payment payment = razorpayClient.payments.fetch(paymentId);
			if(payment.get("status").equals("captured")) {
				order.setOrderStatus("PLACED");
				order.getPaymentDetails().setPaymentId(paymentId);
				order.getPaymentDetails().setPaymentStatus("COMPLETED");
				orderRepo.save(order);
				}
			ApiResponse response = new ApiResponse();
			response.setMessage("Your Order get Placed");
			response.setStatus(true);
			return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			throw new RazorpayException(e.getMessage());
		}
		
		
	}
	

}
