package com.poly.rest.controller.admin;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.dao.OrderDetailDAO;
import com.poly.model.Category;
import com.poly.model.Order;
import com.poly.model.OrderDetail;
import com.poly.model.Product;
import com.poly.service.CategoryService;
import com.poly.service.OrderService;
import com.poly.service.impl.OrderServiceImpl;




@CrossOrigin("*")
@RestController
public class OrderAdminRestController {
	
	@Autowired
	private final OrderService orderService;
	
	@Autowired
	OrderDetailDAO orderDetailDao;
	
	// Admin: hiển thị các hóa đơn theo ngày chỉ định
	@GetMapping("/rest/order-by-day/{day}")
	public ResponseEntity<List<Order>> getQuantitiesByProduct(@PathVariable("day") String day){
		return ResponseEntity.ok(orderService.getOrderByDay(day));
	}
	
	public OrderAdminRestController(OrderService orderService) {
        this.orderService = orderService;
    }
	// Get all order Confirm
    @GetMapping("/rest/orderConfirm")
    public ResponseEntity<List<Order>> getWaitConfirm() {
        List<Order> orders = orderService.findByWaitConfirm();
        return ResponseEntity.ok(orders);
    }

    // Get all waiting For Shipping
    @GetMapping("/rest/waitingForShipping")
    public ResponseEntity<List<Order>> getWaitingForShipping() {
        List<Order> orders = orderService.findByWaitingForShipping();
        return ResponseEntity.ok(orders);
    }
    
    // Get all delivered
    @GetMapping("/rest/delivered")
    public ResponseEntity<List<Order>> getDelivered() {
        List<Order> orders = orderService.findByDelivered();
        return ResponseEntity.ok(orders);
    }
    
    // Get all cancelled
    @GetMapping("/rest/cancelled")
    public ResponseEntity<List<Order>> getCancelled() {
        List<Order> orders = orderService.findByCancelled();
        return ResponseEntity.ok(orders);
    }
    
    // Get all order All
    @GetMapping("/rest/orderAll")
    public ResponseEntity<List<Order>> getOrderAll() {
        List<Order> orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }
    
    //Xác nhận đơn hàng
    @PutMapping("/rest/orderConfirm/{id}")
	public ResponseEntity<Order> put(@PathVariable("id") Long id, @RequestBody Order order) {
		Order existingOrder = orderService.findById(id);
		if (existingOrder != null) {
			order.setId(id);
			Order updatedOrder = orderService.update(order);
			return ResponseEntity.ok(updatedOrder);
		}
		return ResponseEntity.notFound().build();
	}
    
    //Hoàn thành đơn hàng
    @PutMapping("/rest/waitingForShipping/{id}")
	public ResponseEntity<Order> put2(@PathVariable("id") Long id, @RequestBody Order order) {
		Order existingOrder = orderService.findById(id);
		if (existingOrder != null) {
			order.setId(id);
			Order updatedOrder = orderService.update(order);
			return ResponseEntity.ok(updatedOrder);
		}
		return ResponseEntity.notFound().build();
	}
    
    @GetMapping("/rest/orders-by-month/{year}/{month}")
    public ResponseEntity<List<Order>> getOrdersByMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
        List<Order> orders = orderService.getOrdersByMonth(year, month);
        return ResponseEntity.ok(orders);
    }
    
    
    
    //Hóa đơn chi tiết order Confirm
   
    @GetMapping("/rest/order/detail/{id}")
	public Order getDetail(@PathVariable("id")Long id){
		return orderService.findById(id);
	}
    
    @GetMapping("/rest/order/listDetail/{id}")
	public List<OrderDetail> getOrderDetail(@PathVariable("id")Long id){
		return orderDetailDao.findByOrder(id);
	}
    
    @GetMapping("totalOrder")
    public long getTotalOrder() {
    	return orderService.getTotalOrder();
    }
    
    @GetMapping("totalPriceOrder")
    public float getTotaPricelOrder() {
    	return orderService.getTotalPriceOrder();
    }
    
    @GetMapping("viewVistor")
    public float getViewVistor() {
    	return orderService.getViewVisitor();
    }
}
