package com.poly.rest.controller.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.service.OrderService;
import com.poly.service.PayPalHttpClient;
import com.poly.model.paypal.PayPalResponse;
import com.poly.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class CheckoutRestController {

    private final PayPalHttpClient payPalHttpClient;

    @Autowired
    OrderService orderService;

    @Autowired
    public CheckoutRestController(PayPalHttpClient payPalHttpClient) {
        this.payPalHttpClient = payPalHttpClient;
    }
    @PostMapping("/rest/checkout")
    public ResponseEntity<PayPalResponse> create(@RequestBody JsonNode orderData) {
        try {
            var order = orderService.create(orderData);
            var orderResponse = payPalHttpClient.createOrder(order);
            var payPalResponse = new PayPalResponse();
            payPalResponse.setUrl(orderResponse);
            return ResponseEntity.ok(payPalResponse);
        } catch (Exception e) {
            return null;
        }
    }

}
