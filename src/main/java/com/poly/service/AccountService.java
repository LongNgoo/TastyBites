package com.poly.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.poly.model.Account;
import com.poly.model.Product;

@Service
public interface AccountService {
	Account findById(String username);
	
	Account create(Account account);
	Account create2(Account account);
	
	List<Account> findAll();

	List<Account> getAdministrators();

	Account resetPassword(String email);

	Account updatePassword(Account account, String newPassword);
	
	Account update(Account account);

	void delete(String id);
	
	List<Account> getAccountAuth();
	
	List<Account> getAccountCustomer();
}
