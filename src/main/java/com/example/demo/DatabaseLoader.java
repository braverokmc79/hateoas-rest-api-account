package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader {

	private AccountRepository repo;
	
	public DatabaseLoader(AccountRepository repo) {
		this.repo=repo;
	}
	
	@Bean
	public CommandLineRunner initDatabase() {
		return args ->{
			 Account account1=new Account("1234567891", 100);
			 Account account2=new Account("1002003009", 50);
			 Account account3=new Account("1223565893", 1000);
			 
			 //repo.saveAll(List.of(account1, account2, account3));
			 repo.save(account1);
			 repo.save(account2);
			 repo.save(account3);
			 System.out.println("Sample database initialized");
		};
	}
	
	
}


















