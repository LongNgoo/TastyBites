package com.poly.rest.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.poly.model.Account;
import com.poly.model.Authority;
import com.poly.model.Role;
import com.poly.service.AccountService;
import com.poly.service.AuthorityService;
import com.poly.service.RoleService;

@CrossOrigin("*")
@RestController
public class AccountRestControllerAdmin {
	@Autowired
	AccountService accountService;
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
	AuthorityService authorityService;
	
	@Autowired
	RoleService roleService;
	
	@GetMapping("/rest/accountadmin")
	public ResponseEntity<List<Account>> getAll() {
		List<Account> accounts = accountService.getAccountAuth();
		return ResponseEntity.ok(accounts);
	}

	@GetMapping("/rest/accountcustomer")
	public ResponseEntity<List<Account>> getCustomer() {
		List<Account> accounts = accountService.getAccountCustomer();
		return ResponseEntity.ok(accounts);
	}
	
	@GetMapping("/rest/accountadmin/{id}")
	public ResponseEntity<Account> findOne(@PathVariable("id") String id) {
		Account account = accountService.findById(id);
		if (account != null) {
			return ResponseEntity.ok(account);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/rest/accountadmin")
	public ResponseEntity<Account> post(@RequestBody Account account) {
		// Mã hóa mật khẩu
	    String encodedPassword = passwordEncoder.encode(account.getPassword());
	    account.setPassword(encodedPassword);

	    Account createdAccount = accountService.create2(account);

	    // Tạo một bản ghi Authority mới với roleId là "STAFF" và liên kết nó với tài khoản vừa tạo
	    Authority authority = new Authority();
	    authority.setAccount(createdAccount);
	    
	    // Lấy Role có roleId là "CUST" từ cơ sở dữ liệu và gán cho authority
	    Role custRole = roleService.getRoleById("STAF"); // Đảm bảo bạn đã có dịch vụ role để lấy thông tin về Role
	    authority.setRole(custRole);
	    
	    authorityService.create(authority); // Lưu bản ghi Authority
	    
	    return ResponseEntity.ok(createdAccount);
	}

	
	@DeleteMapping("/rest/accountadmin/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
		Account existingAccount = accountService.findById(id);
		if (existingAccount != null) {
			accountService.delete(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/rest/accountadmin/{id}")
	public ResponseEntity<Account> put(@PathVariable("id") String id, @RequestBody Account account) {
		Account existingAccount = accountService.findById(id);
		if (existingAccount != null) {
			// Mã hóa mật khẩu
		    String encodedPassword = passwordEncoder.encode(account.getPassword());
		    account.setPassword(encodedPassword);
		    
			account.setUsername(id);
			Account updatedAccount = accountService.update(account);
			return ResponseEntity.ok(updatedAccount);
		}
		return ResponseEntity.notFound().build();
	}
}
