package com.poly.rest.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.model.OrderDetail;
import com.poly.service.OrderDetailService;

@CrossOrigin("*")
@RestController
public class OrderDetailRestController {
	@Autowired
	OrderDetailService orderDetailService;
	
	@GetMapping("/rest/orderdetailall")
	public List<OrderDetail> findAll() {
		return orderDetailService.findAll();
	}
}
