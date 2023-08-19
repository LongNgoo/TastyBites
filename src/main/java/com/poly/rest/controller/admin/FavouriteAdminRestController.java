package com.poly.rest.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.poly.service.FavouriteService;



@CrossOrigin("*")
@RestController
public class FavouriteAdminRestController {
	@Autowired
	FavouriteService favouriteService;
	
	@GetMapping("/rest/total-like-of-products")
	public ResponseEntity<?> getTotalLikesOfProduct(){
		// câu truy vấn trả về một danh sách các Object[]
		List<Object[]> result = favouriteService.getTotalLikesOfProduct();
		
		// Nên cần chuyển đổi kết quả trả về thành một danh sách các Map<String, Object> để có thể trả về dưới dạng JSON.
		List<Map<String, Object>> response = new ArrayList<>();
		
		// xử lý chuyển đổi
		for (Object[] row : result) {
			Map<String, Object> item = new HashMap<>();
			item.put("categoryId", row[0]);
            item.put("productId", row[1]);
            item.put("productName", row[2]);
            item.put("productImage", row[3]);
            item.put("totalLikes", row[4]);
            response.add(item);
		}
		return ResponseEntity.ok(response);
	}
	
	// Hiển thị thông tin những người đã like sp theo mã sản phẩm
	@GetMapping("/rest/user-info-by-productId/{id}")
	public ResponseEntity<?> getUserInfoWithProductIsLikedByUsers(@PathVariable("id") Integer id){
		List<Object[]> result = favouriteService.getUserInfoWithProductIsLikedByUsers(id);
		List<Map<String, Object>> response = new ArrayList<>();
		for (Object[] row : result) {
			Map<String, Object> item = new HashMap<>();
			item.put("userFullname", row[0]);
			item.put("userEmail", row[1]);
			response.add(item);
		}
		return ResponseEntity.ok(response);
	}
}
