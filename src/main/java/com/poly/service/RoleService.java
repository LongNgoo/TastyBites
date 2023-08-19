package com.poly.service;

import java.util.List;

import com.poly.model.Role;

public interface RoleService {
	List<Role> findAll();
	Role getRoleById(String roleId);
}
