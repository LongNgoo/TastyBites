package com.poly.rest.controller.admin;

import com.poly.model.Discount;
import com.poly.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.transaction.Transactional;

@CrossOrigin("*")
@RestController
public class DiscountAdminRestController {

    @Autowired
    private DiscountService discountService;

    @GetMapping("/rest/discount")
    public ResponseEntity<List<Discount>> getAllDiscounts() {
        List<Discount> discounts = discountService.getAllDiscounts();
        return ResponseEntity.ok(discounts);
    }

    @GetMapping("/rest/discount/{id}")
    public ResponseEntity<Discount> getDiscountById(@PathVariable("id") Integer id) {
        Discount discount = discountService.findById(id);
        if (discount != null) {
            return ResponseEntity.ok(discount);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/rest/discount")
    public ResponseEntity<Discount> createDiscount(@RequestBody Discount discount) {
        Discount createdDiscount = discountService.createDiscount(discount);
        return ResponseEntity.ok(createdDiscount);
    }

    @PutMapping("/rest/discount/{id}")
    public ResponseEntity<Discount> updateDiscount(@PathVariable("id") Integer id, @RequestBody Discount discount) {
        Discount existingDiscount = discountService.findById(id);
        if (existingDiscount != null) {
            discount.setId(id);
            Discount updatedDiscount = discountService.updateDiscount(discount);
            return ResponseEntity.ok(updatedDiscount);
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    @DeleteMapping("/rest/discount/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable("id") Integer id) {
        Discount existingDiscount = discountService.findById(id);
        if (existingDiscount != null) {
            discountService.deleteDiscount(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
