package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	@Query("UPDATE Account a SET a.balance= a.balance + :amount   WHERE a.id =:id")
	@Modifying
	public void updateBalance(float amount, Integer id);
	
}
