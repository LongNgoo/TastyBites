package com.poly.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.TotalProductsUtil;
import com.poly.dao.ReviewDAO;
import com.poly.model.Account;
import com.poly.model.Category;
import com.poly.model.Favourite;
import com.poly.model.Product;
import com.poly.model.Review;
import com.poly.service.CategoryService;
import com.poly.service.FavouriteService;
import com.poly.service.ProductService;

@Controller
public class ProductController {
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	FavouriteService favouriteService;
	
    private final ReviewDAO reviewDAO;
    
    @Autowired
    public ProductController(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }
	
	@RequestMapping("/product/detail/{id}")
	public String detail(Model model, HttpSession session, @PathVariable("id") Integer id) {
		List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        
		Product item = productService.findById(id);
		model.addAttribute("item", item);
		
		//Hiển thị số lượng yêu thích
  		int totalProducts = TotalProductsUtil.getTotalProducts();
  		model.addAttribute("totalProducts", totalProducts);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> authentication = (Map<String, Object>) session.getAttribute("authentication");
		if(authentication != null) {
			Account account = (Account) authentication.get("user");
			Favourite favourite = favouriteService.create(account, item);
			model.addAttribute("flagLikedBtn", favourite.getIsLiked());
		}
		return "user/product/detail";
	}
	
	@RequestMapping("/product/list")
	public String list(Model model, HttpServletRequest request, @RequestParam("cid")Optional<String> cid, @RequestParam(defaultValue = "1") int page) {
		List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories); 
        
        //Load sản phẩm top 5 sao
      	List<Product> productTop;
      	productTop = productService.findByProductTop();
		model.addAttribute("productsTop", productTop);
		
        //Hiển thị số lượng yêu thích
  		int totalProducts = TotalProductsUtil.getTotalProducts();
  		model.addAttribute("totalProducts", totalProducts);
        
		List<Product> list;
		
		list = productService.findByProductNew();
		model.addAttribute("productsNew", list);
		
		if (cid.isPresent()) {
			list= productService.findByCategoryId(cid.get());
		}else {
			list = productService.findAll();
		}
		
		if(list.isEmpty()) {
			int maxPages = 0;
			model.addAttribute("items", list);
			model.addAttribute("currentPage", page);
			model.addAttribute("maxPages", maxPages);
			return "user/product/list";
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
		
		int pageSize = 12; // Kích thước trang
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
 		model.addAttribute("items", productsOnPage);
 		model.addAttribute("currentPage", page);
 		model.addAttribute("maxPages", maxPages);
	 		
		return "user/product/list";
	}
	
	
	// Search by ProductName or productId
		@RequestMapping("/product/list/search")
		public String searchProductByNameOrId(@RequestParam("keyword") String kw,@RequestParam("cid")Optional<String> cid, @RequestParam("keyword") String kw1, Model model) {
			List<Category> categories = categoryService.findAll();
	        model.addAttribute("categories", categories); 
			//Load sản phẩm top 5 sao
	      	List<Product> productTop;
	      	productTop = productService.findByProductTop();
			model.addAttribute("productsTop", productTop);
			
	        //Hiển thị số lượng yêu thích
	  		int totalProducts = TotalProductsUtil.getTotalProducts();
	  		model.addAttribute("totalProducts", totalProducts);
	        
			List<Product> list;
			
			list = productService.findByProductNew();
			model.addAttribute("productsNew", list);
			
			if (cid.isPresent()) {
				list= productService.findByCategoryId(cid.get());
			}else {
				list = productService.findAll();
			}
			List<Product> products = productService.searchByProductNameOrId(kw, kw1);
			for(int i = 0; i < products.size(); ++i) {
				if(products.get(i).getProductQuantity() == 0) {
					products.remove(products.get(i));
				}
			}
			if(kw.equals("")) {
				model.addAttribute("message", "Please input keyword to find product");
				model.addAttribute("items", productService.findAll());
				return "user/product/list";
			}else if(products.isEmpty()) {
				model.addAttribute("message", "The product not found");
				model.addAttribute("items", productService.findAll());
				return "user/product/list";
			}
			model.addAttribute("items", products);
			return "user/product/list";
		}

		// Lọc sản phẩm theo khoảng giá
		@RequestMapping("/product/list/filter")
		public String getProducts(@RequestParam("cid")Optional<String> cid,@RequestParam(value="all", required=false) Boolean all,
								  @RequestParam(value="0-50000", required=false) Boolean between0and50000,
		                          @RequestParam(value="50000-100000", required=false) Boolean between50000and100000,
		                          @RequestParam(value="100000-200000", required=false) Boolean between100000and200000,
		                          @RequestParam(value="200000-300000", required=false) Boolean between200000and300000,
		                          @RequestParam(value="300000-500000", required=false) Boolean between300000and500000,
		                          Model model) {
			List<Category> categories = categoryService.findAll();
	        model.addAttribute("categories", categories); 
			//Load sản phẩm top 5 sao
	      	List<Product> productTop;
	      	productTop = productService.findByProductTop();
			model.addAttribute("productsTop", productTop);
			
	        //Hiển thị số lượng yêu thích
	  		int totalProducts = TotalProductsUtil.getTotalProducts();
	  		model.addAttribute("totalProducts", totalProducts);
	        
			List<Product> list;
			
			list = productService.findByProductNew();
			model.addAttribute("productsNew", list);
			
			if (cid.isPresent()) {
				list= productService.findByCategoryId(cid.get());
			}else {
				list = productService.findAll();
			}
			
		  List<Product> list1 = productService.findAll();
		  if(all != null) {
			  list1 = list1.stream().collect(Collectors.toList());
			  for(int i = 0; i < list1.size(); ++i) {
				  if(list1.get(i).getProductQuantity() == 0) {
					  list1.remove(list1.get(i));
				  }
			  }
		  }
		  if (between0and50000 != null && between0and50000) {
			  list1 = list1.stream().filter(p -> p.getPrice() < 50000).collect(Collectors.toList());
			  for(int i = 0; i < list1.size(); ++i) {
				  if(list1.get(i).getProductQuantity() == 0) {
					  list1.remove(list1.get(i));
				  }
			  }
		  }
		  if (between50000and100000 != null && between50000and100000) {
			  list1 = list1.stream().filter(p -> p.getPrice() >= 50000 && p.getPrice() <= 100000).collect(Collectors.toList());
			  for(int i = 0; i < list1.size(); ++i) {
				  if(list1.get(i).getProductQuantity() == 0) {
					  list1.remove(list1.get(i));
				  }
			  }
		  }
		  if (between100000and200000 != null && between100000and200000) {
			  list1 = list1.stream().filter(p -> p.getPrice() > 100000 && p.getPrice() <= 200000 ).collect(Collectors.toList());
			  for(int i = 0; i < list1.size(); ++i) {
				  if(list1.get(i).getProductQuantity() == 0) {
					  list1.remove(list1.get(i));
				  }
			  }
		  }
		  if (between200000and300000 != null && between200000and300000) {
			  list1 = list1.stream().filter(p -> p.getPrice() > 200000 && p.getPrice() <= 300000).collect(Collectors.toList());
			  for(int i = 0; i < list1.size(); ++i) {
				  if(list1.get(i).getProductQuantity() == 0) {
					  list1.remove(list1.get(i));
				  }
			  }
		  }
		  if (between300000and500000 != null && between300000and500000) {
			  list1 = list1.stream().filter(p -> p.getPrice() > 300000 && p.getPrice() <= 500000).collect(Collectors.toList());
			  for(int i = 0; i < list1.size(); ++i) {
				  if(list1.get(i).getProductQuantity() == 0) {
					  list1.remove(list1.get(i));
				  }
			  }
		  }
		  model.addAttribute("items", list1);
		  return "user/product/list";
		}
		
		@PostMapping("/product/addReview")
		public String addReview(@ModelAttribute Review review) {
		    // Set ngày đánh giá
		    review.setReviewDate(new Date());

		    // Lưu đánh giá vào cơ sở dữ liệu qua DAO
		    reviewDAO.save(review);

		    // Chuyển hướng người dùng trở lại trang chi tiết sản phẩm
		    return "user/product/detail/" + review.getProduct().getId();
		}


}
