package com.poly.dao;

import com.poly.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiscountDAO extends JpaRepository<Discount, Integer> {

    @Query("SELECT d FROM Discount d WHERE d.code=:code")
    Discount findByCode(String code);

    @Query("SELECT d FROM Discount d WHERE d.id=:id")
    Discount findById(Long id);

	void deleteById(Long id);
    
    
    
}
