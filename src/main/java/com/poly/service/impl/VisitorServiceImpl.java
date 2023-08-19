package com.poly.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.OrderDAO;
import com.poly.dao.VisitorDAO;
import com.poly.model.Order;
import com.poly.model.Visitor;
import com.poly.service.VisitorService;

@Service
public class VisitorServiceImpl implements VisitorService{

	@Autowired
	VisitorDAO dao;
	
	@Override
	public Visitor update(Visitor visitor) {
		return dao.save(visitor);
	}

	@Override
	public Visitor findById(Integer id) {
		Optional<Visitor> optionalVisitor = dao.findById(id);
		return optionalVisitor.orElse(null);
	}

}
