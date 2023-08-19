package com.poly.service.impl;

import com.poly.dao.DiscountDAO;
import com.poly.model.Discount;
import com.poly.model.Product;
import com.poly.service.DiscountService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountDAO discountDAO;

    @Override
    public Discount findByCode(String code) {
        return discountDAO.findByCode(code);
    }

    @Override
    public Discount findById(Integer id) {
    	Optional<Discount> optionalDiscount = discountDAO.findById(id);
	    return optionalDiscount.orElse(null);
    }

    @Override
    public List<Discount> getAllDiscounts() {
        return discountDAO.findAll();
    }

    @Override
    public Discount createDiscount(Discount discount) {
        return discountDAO.save(discount);
    }

    @Override
    public Discount updateDiscount(Discount discount) {
        return discountDAO.saveAndFlush(discount);
    }

    @Override
    public void deleteDiscount(Integer id) {
        discountDAO.deleteById(id);
    }
}
