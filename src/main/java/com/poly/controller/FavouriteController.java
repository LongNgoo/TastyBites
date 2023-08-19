package com.poly.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.TotalProductsUtil;
import com.poly.model.Category;
import com.poly.model.Product;
import com.poly.service.CategoryService;
import com.poly.service.ProductService;



@Controller
public class FavouriteController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryService categoryService;
	
	@RequestMapping("/favourite/view")
	public String view(Model model, HttpServletRequest request, @RequestParam(defaultValue = "1") int page) {
		
		////Load danh mục
		List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
		
		String username = request.getRemoteUser();
		List<Product> list = productService.findAllProductCustomerLike(username);
		
		if(list.isEmpty()) {
			int maxPages = 0;
			model.addAttribute("favourite", list);
			model.addAttribute("currentPage", page);
			model.addAttribute("maxPages", maxPages);
			return "user/favourite/view";
		}

		for(int i = 0; i < list.size(); ++i) {
			if(list.get(i).getProductQuantity() == 0) {
				list.remove(list.get(i));
			}
		}
		System.out.println("List: " + list.size());
		/*
		 * 		pageSize   : số lượng sản phẩm trên 1 trang
		 * 		maxPages   : Tổng số trang
		 * 		startIndex : chỉ số bắt đầu của sản phẩm cần hiển thị trên trang hiện tại
		 */
		
		int pageSize = 4; // Kích thước trang
		int maxPages = (int) Math.ceil((double) list.size() / pageSize); 

		int startIndex = (page - 1) * pageSize;

		startIndex = Math.min(startIndex, list.size()); // Đảm bảo không vượt quá số lượng sản phẩm
		int endIndex = Math.min(startIndex + pageSize, list.size()); // Chỉ số kết thúc của sản phẩm cần hiển thị trên trang hiện tại
 
		// Kiểm tra và xử lý lỗi
	    if (startIndex == 0) {
	        startIndex = 0;
	    }

	    if (endIndex > list.size()) {
	        endIndex = list.size();
	    }   
	    

		// dssp cần hiển thị
		List<Product> productsOnPage = list.subList(startIndex, endIndex); // Danh sách sản phẩm trên trang hiện tại
		model.addAttribute("favourite", productsOnPage);
		model.addAttribute("currentPage", page);
		model.addAttribute("maxPages", maxPages);
		
		//Hiển thị số lượng yêu thích
		
		int totalProducts = list.size();
		TotalProductsUtil.setTotalProducts(totalProducts);
		model.addAttribute("totalProducts", totalProducts);

		return "user/favourite/view";
	}
}
