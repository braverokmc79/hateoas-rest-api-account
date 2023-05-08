package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import jakarta.persistence.EntityManager;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Configuration
public class AccountModelAssember implements RepresentationModelAssembler<Account, EntityModel<Account>> {

	@Override
	public EntityModel<Account> toModel(Account account) {
		EntityModel<Account> accountModel=EntityModel.of(account);
		
		accountModel.add(linkTo(methodOn(AccountApi.class).getOne(account.getId())).withSelfRel());
		accountModel.add(linkTo(methodOn(AccountApi.class).deposit(account.getId(), null)).withRel("deposits"));
		accountModel.add(linkTo(methodOn(AccountApi.class).withdraw(account.getId(), null)).withRel("withdrawals"));
		accountModel.add(linkTo(methodOn(AccountApi.class).listAll()).withRel(IanaLinkRelations.COLLECTION));

		return accountModel;
	}

	
	
}
