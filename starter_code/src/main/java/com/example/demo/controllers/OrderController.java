package com.example.demo.controllers;

import com.example.demo.model.persistence.AppUser;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {


	private final UserRepository userRepository;

	private final OrderRepository orderRepository;

	public OrderController(UserRepository userRepository, OrderRepository orderRepository) {
		this.userRepository = userRepository;
		this.orderRepository = orderRepository;
	}

	@PostMapping("/submit/{username}")
	public ResponseEntity<UserOrder> submit(@PathVariable String username) {
		AppUser appUser = userRepository.findByUsername(username);
		if(appUser == null) {
			return ResponseEntity.notFound().build();
		}
		UserOrder order = UserOrder.createFromCart(appUser.getCart());
		orderRepository.save(order);
		return ResponseEntity.ok(order);
	}

	@GetMapping("/history/{username}")
	public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
		AppUser appUser = userRepository.findByUsername(username);
		if(appUser == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(orderRepository.findByAppUser(appUser));
	}
}
