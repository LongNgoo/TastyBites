package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.RoleDAO;
import com.poly.model.Role;
import com.poly.service.RoleService;



@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	RoleDAO dao;
	
	@Override
	public List<Role> findAll() {
		return dao.findAll();
	}

	@Override
	public Role getRoleById(String roleId) {
		return dao.findById(roleId).orElse(null);
	}

}
