package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.model.Review;

@Repository
public interface ReviewDAO extends JpaRepository<Review, Long> {
	
}

