package com.poly.rest.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.model.Account;
import com.poly.model.Product;
import com.poly.service.AccountService;

@CrossOrigin("*")
@RestController
public class ProfileRestController {
	
	@Autowired
	AccountService accountService;
	
	@GetMapping("/rest/profile/{username}")
	public ResponseEntity<Account> getOne(@PathVariable("username") String username) {
		Account account = accountService.findById(username);
		if (account != null) {
			return ResponseEntity.ok(account);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/rest/profile/{username}")
	public ResponseEntity<Account> put(@PathVariable("username")  String username, @RequestBody Account account) {
		Account existingAccount = accountService.findById(username);
		if (existingAccount != null) {
			account.setUsername(username);
			Account updatedAccount = accountService.update(account);
			return ResponseEntity.ok(updatedAccount);
		}
		return ResponseEntity.notFound().build();
	}
}
