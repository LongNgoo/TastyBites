package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.model.Favourite;



@Repository
public interface FavouriteDAO extends JpaRepository<Favourite, Integer>{
	
	@Query(value = "select * from Favourites where Username = ?1 and ProductId = ?2", nativeQuery = true)
	Favourite findByUsernameAndProductId(String username, Integer id);

	/*		Chức năng:
	 * 					Thống kê sản phẩm được bao nhiêu lượt like
	 * 		Nghiệp vụ:
	 * 					Bởi vì câu truy vấn trả về một số lượng cột không xác định trước
	 * 					nên là sử dụng kiểu dữ liệu Object[] để chứa kết quả trả về.
	 * 
	 */
	@Query(value = "select p.CategoryId , p.Id, p.Name, p.Image, sum(cast(f.IsLiked as int)) as [Total like] from Favourites f inner join Products p on f.ProductId = p.Id"
			+ " group by p.CategoryId, p.Id, p.Name, p.Image, f.IsLiked order by sum(cast(f.IsLiked as int)) desc", nativeQuery = true)
	List<Object[]> getTotalLikesOfProduct();
	
	// Hiển thị thông tin những người đã like sp theo mã sản phẩm
	@Query(value = "select ac.Fullname, ac.Email from Accounts ac inner join Favourites f on ac.Username = f.Username"
			+ " inner join Products p on f.ProductId = p.Id"
			+ " where p.Id = ?1 and f.IsLiked = 1", nativeQuery = true)
	List<Object[]> getUserInfoWithProductIsLikedByUsers(Integer productId);
}
