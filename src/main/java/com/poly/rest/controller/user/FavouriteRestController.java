package com.poly.rest.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.model.Account;
import com.poly.model.Favourite;
import com.poly.service.AccountService;
import com.poly.service.FavouriteService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/favourite")
public class FavouriteRestController {
	@Autowired
	FavouriteService favouriteService;
	
	@Autowired
	AccountService accountService;
	
	@GetMapping("/{id}")
	public Favourite check(HttpServletRequest request, @PathVariable("id") Integer id) {
		//Kiểm tra Favourite có tồn tại hay không
		String username = request.getRemoteUser();
		Favourite favourite = favouriteService.findByUsernameAndProductId(username, id);
		return favourite;
	}
	
	@PutMapping("/{id}")
	public Favourite update(HttpServletRequest request, @PathVariable("id") Integer id) {
		// có 2 cách
		// HttpServletRequest
		Account account = accountService.findById(request.getRemoteUser());
		Favourite favourite = favouriteService.updateLikeOrUnlike(account, id);
		return favourite;
	}

}
