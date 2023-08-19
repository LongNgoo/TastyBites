package com.poly.dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.model.Order;



public interface OrderDAO extends JpaRepository<Order, Long>{
	@Query("SELECT o FROM Order o WHERE o.account.username=?1")
	List<Order> findByUsername(String username);
	
	
	// Admin: hiển thị các hóa đơn theo ngày chỉ định
	@Query(value = "select * from Orders where CreateDate = ?1", nativeQuery = true)
	List<Order> getOrderByDay(String day);
	
	//Admin: Hiển thị ds order đợi xác nhận
	@Query(value = "select * from Orders where Status = N'Đợi xác nhận' ", nativeQuery = true)
	List<Order> findByWaitConfirm();
	
	//Admin: Hiển thị ds order đang giao
	@Query(value = "select * from Orders where Status = N'Đang giao' ", nativeQuery = true)
	List<Order> findByWaitingForShipping();
	
	//Admin: Hiển thị ds order đã giao
	@Query(value = "select * from Orders where Status = N'Đã giao' ", nativeQuery = true)
	List<Order> findByDelivered();
	
	//Admin: Hiển thị ds order đã hủy
	@Query(value = "select * from Orders where Status = N'Đã hủy' ", nativeQuery = true)
	List<Order> findByCancelled();
	
	@Query(value = "SELECT COUNT(*) AS TotalOrders FROM Orders; ", nativeQuery = true)
	long getTotalOrder();
	
	@Query(value = "SELECT COALESCE(SUM(TotalPriceAfterDiscount),0.0) as FinalTotal\r\n"
			+ "FROM (\r\n"
			+ "    SELECT d.Price,\r\n"
			+ "           COALESCE(SUM(od.Price * od.Quantity) - COALESCE(d.Price, 0), 0) + 15000 as TotalPriceAfterDiscount\r\n"
			+ "    FROM Orders o\r\n"
			+ "    INNER JOIN OrderDetails od ON o.Id = od.OrderId \r\n"
			+ "    LEFT JOIN Discount d ON o.DiscountId = d.Id\r\n"
			+ "    WHERE o.Status = N'Đã giao'\r\n"
			+ "    GROUP BY d.Price\r\n"
			+ ") Subquery;", nativeQuery = true)
	float getTotalPriceOrder();
	
	@Query(value = "SELECT views FROM Visitors; ", nativeQuery = true)
	Integer getViewVisitor();
	
	
	// Truy vấn để lấy danh sách các đơn hàng trong một tháng cụ thể
    @Query(value = "SELECT * FROM Orders WHERE YEAR(CreateDate) = :year AND MONTH(CreateDate) = :month", nativeQuery = true)
    List<Order> getOrdersByMonth(@Param("year") int year, @Param("month") int month);

	
	
	
}
