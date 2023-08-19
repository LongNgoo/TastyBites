package com.poly.controller;

import java.util.Date;
import java.util.List;

import com.poly.model.Discount;
import com.poly.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.model.Category;
import com.poly.service.CategoryService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShoppingCartController {
	
	@Autowired
	CategoryService categoryService;

	@Autowired
	DiscountService discountService;
	
	@RequestMapping("/cart/view")
	public String viewCart(Model model) {
		List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
		Discount discount = new Discount();
		discount.setPrice(0.0);
		model.addAttribute("discount", discount);
		return "/user/cart/view";
	}

	@RequestMapping("/cart/view/discount")
	public String getDiscount(@RequestParam(defaultValue = "")  String code, Model model) {
		Discount foundDiscount = discountService.findByCode(code);
		if (foundDiscount == null) {
			model.addAttribute("error", "Code không tồn tại");
			foundDiscount = new Discount();
			foundDiscount.setPrice(0.0);
		}

		Date currentDate = new Date();
		if (currentDate.before(foundDiscount.getApplyDay()) || currentDate.after(foundDiscount.getExpiration())) {
			model.addAttribute("error", "Code hết hạn");
			foundDiscount = new Discount();
			foundDiscount.setPrice(0.0);
		}

		model.addAttribute("discount", foundDiscount);
		return "/user/cart/view";
	}
}
