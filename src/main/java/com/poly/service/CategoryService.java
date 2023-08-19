package com.poly.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.poly.model.Category;

@Service
public interface CategoryService {
	
	List<Category> findAll();
	
	Category findById(String id);
	
	List<Category> findByTop4Categoy();
	
	Category create(Category category);
	
	Category update(Category category);
	
	public void delete(String id);
}
