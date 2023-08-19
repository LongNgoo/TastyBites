package com.poly.service;

import java.util.List;

import com.poly.model.Authority;



public interface AuthorityService {
	
	List<Authority> findAll();
	
	Authority create(Authority auth);
	
	void delete(Integer id);
	
	List<Authority> findAuthoritiesOfAdministrators();
}
