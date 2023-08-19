package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.model.Category;

public interface CategoryDAO extends JpaRepository<Category, String>{
	 @Query(value="SELECT TOP 4 * FROM Categories",nativeQuery = true)
	 List<Category> getTop4Category();
}
