package com.example.demo;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jakarta.persistence.EntityManager;

@DataJpaTest
public class AccountRepositoryTest {

	@Autowired
	private AccountRepository repo;
	
	
	@Autowired
	EntityManager em;
	
	@Test
	public void testAddAccount() {
		Account account=Account.builder().accountNumber("1234567890").balance(100).build();
		Account savedAccount=repo.save(account);	
		
		assertThat(savedAccount).isNotNull();	
		assertThat(savedAccount.getId()).isGreaterThan(0);
	}

	
	@Test
	public void testDepositAmount() {
		Account account=Account.builder().accountNumber("1234567890").balance(100).build();
		Account savedAccount=repo.save(account);	
		
		repo.updateBalance(50,savedAccount.getId());
		em.flush();
		em.clear();
		
		Account updateAccount=repo.findById(savedAccount.getId()).get();
		
		assertThat(updateAccount.getBalance()).isEqualTo(150);
	}
	
	
}
