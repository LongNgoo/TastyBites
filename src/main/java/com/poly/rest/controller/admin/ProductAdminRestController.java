package com.poly.rest.controller.admin;

import java.util.List;
import java.util.Optional;

import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.model.Product;
import com.poly.service.ProductService;

@CrossOrigin("*")
@RestController
public class ProductAdminRestController {
	@Autowired
	private ProductService productService;

	@GetMapping("/rest/product-quantity/{id}")
	public ResponseEntity<List<Product>> getQuantitiesByProduct(@PathVariable("id") String id) {
		List<Product> products = productService.findByCategoryId(id);
		return ResponseEntity.ok(products);
	}

	@GetMapping("/rest/product")
	public ResponseEntity<List<Product>> getAll() {
		List<Product> products = productService.findAll();
		return ResponseEntity.ok(products);
	}

	@GetMapping("/rest/product/{id}")
	public ResponseEntity<Product> findOne(@PathVariable("id") Integer id) {
		Product product = productService.findById(id);
		if (product != null) {
			return ResponseEntity.ok(product);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/rest/product")
	public ResponseEntity<Product> post(@RequestBody Product product) {
		Product createdProduct = productService.create(product);
		return ResponseEntity.ok(createdProduct);
	}
	@PutMapping("/rest/product/{id}")
	public ResponseEntity<Product> put(@PathVariable("id") Integer id, @RequestBody Product product) {
		Product existingProduct = productService.findById(id);
		if (existingProduct != null) {
			product.setProductId(id);
			Product updatedProduct = productService.update(product);
			return ResponseEntity.ok(updatedProduct);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/rest/product/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		Product existingProduct = productService.findById(id);
		if (existingProduct != null) {
			productService.delete(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}