package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.model.Account;
import com.poly.model.Product;

public interface AccountDAO extends JpaRepository<Account, String>{
	// Lấy tất cả account có vai trò DIRE và Staf
	@Query("select distinct ar.account from Authority ar where ar.role.id in ('DIRE', 'STAF')")
	List<Account> getAdministrators();
	
	@Query(value = "select * from Accounts where Email = ?1", nativeQuery = true)
	Account findByEmail(String email);
	
	@Query(value="SELECT * FROM Accounts A INNER JOIN Authorities Auth ON A.Username = Auth.Username INNER JOIN Roles R ON Auth.RoleId = R.Id WHERE R.Id=N'STAF' OR R.Id=N'DIRE'", nativeQuery = true)
	List<Account> getAccountAuth();
	
	@Query(value="SELECT * FROM Accounts A INNER JOIN Authorities Auth ON A.Username = Auth.Username INNER JOIN Roles R ON Auth.RoleId = R.Id WHERE R.Id=N'CUST'", nativeQuery = true)
	List<Account> getAccountCustomer();
}
