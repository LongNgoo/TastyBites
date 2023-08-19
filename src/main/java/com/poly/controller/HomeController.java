package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.TotalProductsUtil;
import com.poly.model.Category;
import com.poly.model.Product;
import com.poly.service.CategoryService;
import com.poly.service.ProductService;

@Controller
public class HomeController{
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@RequestMapping(value = {"/","/home/index"})
	public String home(Model model) {
		////Load danh mục
		List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        
        List<Category> top4Category = categoryService.findByTop4Categoy();
        model.addAttribute("top4Category", top4Category);
        
        //Load tất cả sản phẩm 
        List<Product> products = productService.findByProductRandom();
        model.addAttribute("products", products);
        
        //Load sản phẩm mới
        List<Product> list;
		
		list = productService.findByProductNew();
		model.addAttribute("productsNew", list);
		
		//Load sản phẩm top 5 sao
		List<Product> productTop;
		productTop = productService.findByProductTop();
		model.addAttribute("productsTop", productTop);
		
		//Hiển thị số lượng yêu thích
  		int totalProducts = TotalProductsUtil.getTotalProducts();
  		model.addAttribute("totalProducts", totalProducts);
        
		return "/user/home/home";
	}
	
	@RequestMapping(value = {"/admin","/admin/home/index"})
	public String admin() {
		return "redirect:/assets/admin/index.html";
	}
}
