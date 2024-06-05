package com.example.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	@Query(value="update customer c set c.balance=c.balance + ?1 where c.id=?2",nativeQuery=true)
	public String deposit(int id, int amount);
	
	Customer findByUsername(String username);
}
