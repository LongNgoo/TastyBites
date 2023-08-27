package com.poly.rest.controller.user;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.model.Account;
import com.poly.model.Authority;
import com.poly.model.Role;
import com.poly.service.AccountService;
import com.poly.service.AuthorityService;
import com.poly.service.RoleService;

@CrossOrigin("*")
@RestController
public class AccountRestController {
	@Autowired
	AccountService accountService;
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthorityService authorityService;
	
	@Autowired
	RoleService roleService;
	
	@PostMapping("/rest/account")
    public ResponseEntity<Account> post(@RequestBody Account account) {
		// Mã hóa mật khẩu
	    String encodedPassword = passwordEncoder.encode(account.getPassword());
	    account.setPassword(encodedPassword);

	    Account createdAccount = accountService.create(account);

	    // Tạo một bản ghi Authority mới với roleId là "CUST" và liên kết nó với tài khoản vừa tạo
	    Authority authority = new Authority();
	    authority.setAccount(createdAccount);
	    
	    // Lấy Role có roleId là "CUST" từ cơ sở dữ liệu và gán cho authority
	    Role custRole = roleService.getRoleById("CUST"); // Đảm bảo bạn đã có dịch vụ role để lấy thông tin về Role
	    authority.setRole(custRole);
	    
	    authorityService.create(authority); // Lưu bản ghi Authority
	    
	    return ResponseEntity.ok(createdAccount);
    }
	
	@GetMapping("/rest/accounts")
	public List<Account> getAccounts(@RequestParam("admin") Optional<Boolean> admin){
		if(admin.orElse(false)) {
			return accountService.getAdministrators();
		}
		return accountService.findAll();
	}

	@PutMapping("/rest/account/{username}/change-password")
	public ResponseEntity<Account> changePassword(@PathVariable String username, @RequestBody String newPassword) {
	    Account account = accountService.findById(username);
	    if (account == null) {
	        return ResponseEntity.notFound().build();
	    }
	    
	    account = accountService.changePassword(account, newPassword);
	    return ResponseEntity.ok(account);
	}

}

