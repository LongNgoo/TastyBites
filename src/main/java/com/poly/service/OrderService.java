package com.poly.service;

import java.util.List;


import com.fasterxml.jackson.databind.JsonNode;
import com.poly.model.Order;
import com.poly.model.OrderDetail;
import com.poly.model.Product;

public interface OrderService {

	Order create(JsonNode orderData);
	
	// Khi đặt hàng thành công rồi. Sẽ hiển thị lại hóa đơn cho khách hàng
	Order findById(Long id);
	
	// Cho khách xem lại danh sách đơn hàng của chính khách đặt
	List<Order> findByUsername(String username);
	
	// Admin: hiển thị các hóa đơn theo ngày chỉ định
	List<Order> getOrderByDay(String day);
	
	List<Order> findAll();
	
	//Admin: Hiển thị ds order đợi xác nhận
	List<Order> findByWaitConfirm();
	
	//Admin: Hiển thị ds order đang giao
	List<Order> findByWaitingForShipping();
	
	//Admin: Hiển thị ds order đã giao
	List<Order> findByDelivered();
	
	//Admin: Hiển thị ds order đã hủy
	List<Order> findByCancelled();
	
	Order update(Order order);
	
	long getTotalOrder();
	
	float getTotalPriceOrder();
	
	Integer getViewVisitor();
	
	List<Order> getOrdersByMonth(int year, int month);

}
