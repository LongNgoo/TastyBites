package com.poly.service;

import com.poly.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
	List<Product> findAll();
	
	List<Product> findByProductRandom();
	
	List<Product> findByCategoryId(String id);
	
	List<Product> findByProductNew();
	
	List<Product> findByProductTop();
	
	Product create(Product product);
	
	Product update(Product product);
	boolean existsById(Integer id);
	
	public void delete(Integer id);
	
	// Search product name or product id
	List<Product> searchByProductNameOrId(String name, String kw1);
	
	List<Product> findAllProductCustomerLike(String username);

	Product findById(Integer id);
}
