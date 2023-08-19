package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.AccountDAO;
import com.poly.dao.AuthorityDAO;
import com.poly.model.Account;
import com.poly.model.Authority;
import com.poly.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService{

	@Autowired
	AuthorityDAO authDao;
	
	@Autowired
	AccountDAO accDao;
	
	@Override
	public List<Authority> findAll() {
		return authDao.findAll();
	}

	@Override
	public Authority create(Authority auth) {
		return authDao.save(auth);
	}

	@Override
	public void delete(Integer id) {
		authDao.deleteById(id);
	}

	@Override
	public List<Authority> findAuthoritiesOfAdministrators() {
		List<Account> accounts = accDao.getAdministrators();
		return authDao.authoritiesOf(accounts);
	}

}
