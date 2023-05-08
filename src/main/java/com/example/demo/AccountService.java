package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {

	private AccountRepository repo;
	
	public AccountService(AccountRepository repo) {
		this.repo=repo;
	}

	public List<Account> listAll(){
		return repo.findAll();
	}
	
	
	public Account get(Integer id) {
		return repo.findById(id).get();
	}
	
	
	public Account save(Account account) {
		return repo.save(account);
	}
	
	
	/** 입금 */
	public Account deposit(float amount, Integer id) {
		repo.updateBalance(-amount, id);
		return repo.findById(id).get();
	}
	
	
	/** 출금 */
	public Account withdraw(float amount, Integer id) {
		repo.updateBalance(-amount, id);
		return repo.findById(id).get();
	}
	
	public void delete(Integer id) throws AccountNotFoundException {
		if(!repo.existsById(id)) {
			throw new AccountNotFoundException();
		}
		repo.deleteById(id);
	}
}









