package com.poly.rest.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.model.Order;
import com.poly.service.OrderService;

@CrossOrigin("*")
@RestController
public class OrderRestController {
	
	@Autowired
	OrderService orderService;
	
	@PostMapping("/rest/orders")
	public Order create(@RequestBody JsonNode orderData) {
		return orderService.create(orderData);
	}
	
	@GetMapping("/rest/ordersall")
	public List<Order> findAll() {
		return orderService.findAll();
	}
}
