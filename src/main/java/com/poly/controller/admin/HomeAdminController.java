package com.poly.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeAdminController {
	@RequestMapping("admin/index")
	public String index() {
		return "admin/index";
	}
	
}
