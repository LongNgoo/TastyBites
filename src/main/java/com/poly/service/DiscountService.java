package com.poly.service;

import com.poly.model.Discount;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface DiscountService {

    Discount findByCode(String code);

    Discount findById(Integer id);

	List<Discount> getAllDiscounts();

	Discount createDiscount(Discount discount);

	Discount updateDiscount(Discount discount);

	void deleteDiscount(Integer id);
}
