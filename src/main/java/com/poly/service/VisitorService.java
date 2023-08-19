package com.poly.service;

import org.springframework.stereotype.Service;

import com.poly.model.Order;
import com.poly.model.Visitor;

@Service
public interface VisitorService {
	Visitor findById(Integer id);
	Visitor update(Visitor visitor);
}
