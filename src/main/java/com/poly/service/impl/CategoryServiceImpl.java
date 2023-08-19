package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.CategoryDAO;
import com.poly.model.Category;
import com.poly.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	CategoryDAO categoryDAO;
	
	@Override
	public List<Category> findAll() {
		return categoryDAO.findAll();
	}

	@Override
	public Category findById(String id) {
		return categoryDAO.findById(id).get();
	}

	@Override
	public Category create(Category category) {
		return categoryDAO.save(category);
	}

	@Override
	public Category update(Category category) {
		return categoryDAO.save(category);
	}

	@Override
	public void delete(String id) {
		categoryDAO.deleteById(id);
	}

	@Override
	public List<Category> findByTop4Categoy() {
		return categoryDAO.getTop4Category();
	}

}
