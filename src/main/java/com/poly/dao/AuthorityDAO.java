package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.model.Account;
import com.poly.model.Authority;


public interface AuthorityDAO extends JpaRepository<Authority, Integer>{

	@Query("select distinct a from Authority a where a.account in ?1")
	List<Authority> authoritiesOf(List<Account> accounts);
	
}
