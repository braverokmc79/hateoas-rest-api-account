package com.example.demo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
@RestController
@RequestMapping(value="/api/accounts", produces=MediaTypes.HAL_JSON_VALUE)
//@RequestMapping(value="/api/accounts")
public class AccountApi {

	
	private AccountService service;
	private AccountModelAssember assember;
	
	
	public AccountApi(AccountService service, AccountModelAssember assember) {
		this.service=service;
		this.assember=assember;
	}
	
	
	@GetMapping
	public ResponseEntity<?> listAll(){
		List<Account> listAccounts =service.listAll();	
		if(listAccounts.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		/**	
		List<EntityModel<Account>> accountModel =listAccounts.stream().map(assember::toModel).collect(Collectors.toList());
		CollectionModel<EntityModel<Account>> collectionModel=CollectionModel.of(accountModel); //컬렉션에  링크 추가
		collectionModel.add(linkTo(methodOn(AccountApi.class).listAll()).withSelfRel());
		
		
		assember 에 내장된 toCollectionModel 을 사용 ==>
		*/
		CollectionModel<EntityModel<Account>> collectionModel=assember.toCollectionModel(listAccounts);
		
		return ResponseEntity.status(HttpStatus.OK).body(collectionModel);
	}
	
	//return new ResponseEntity<>(collectionModel, HttpStatus.OK);
	

	/**
	 * ====>출력

{
    "_embedded": {
        "accountList": [
            {
                "id": 1,
                "accountNumber": "1234567891",
                "balance": 100.0,
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/accounts/1"
                    },
                    "collection": {
                        "href": "http://localhost:8080/api/accounts"
                    }
                }
            },
            {
                "id": 2,
                "accountNumber": "1002003009",
                "balance": 50.0,
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/accounts/2"
                    },
                    "collection": {
                        "href": "http://localhost:8080/api/accounts"
                    }
                }
            },
            {
                "id": 3,
                "accountNumber": "1223565893",
                "balance": 1000.0,
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/accounts/3"
                    },
                    "collection": {
                        "href": "http://localhost:8080/api/accounts"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/accounts"
        }
    }
}


	 */
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable("id") Integer id) {
		try {
			 Account account=service.get(id);
			 EntityModel<Account> accountModel=assember.toModel(account);
			 return ResponseEntity.status(HttpStatus.OK).body(accountModel);
		}catch (NoSuchElementException ex) {
			return ResponseEntity.notFound().build();
		}
	}
	
	
/**
 * ===>출력
 * 
 * {
    "id": 1,
    "accountNumber": "1234567891",
    "balance": 100.0,
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/accounts/1"
        },
        "collection": {
            "href": "http://localhost:8080/api/accounts"
        }
    }
}

	
 */
	
	

	
/**
	@PostMapping("/{id}")
    public ResponseEntity<?> createEvent(@PathVariable("id") Integer id){
		Account account =service.get(id);
        URI createdUri = linkTo(AccountApi.class).slash(id).toUri();
      
        System.out.println(" createUri : " + createdUri);
        return ResponseEntity.created(createdUri).body(account);
    }

*/
	
	
	
	
	
	@PostMapping
	public ResponseEntity<?> add(@RequestBody Account account){
		Account savedAccount =service.save(account);
		
		EntityModel<Account> accountModel=assember.toModel(account);
		
		return ResponseEntity.created(linkTo(methodOn(AccountApi.class).getOne(savedAccount.getId())).toUri()).body(accountModel);
	}
	
	
	
	
	/**==> 출력
	 * {
    "id": 8,
    "accountNumber": "987654321",
    "balance": 2950.0,
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/accounts/8"
        },
        "collection": {
            "href": "http://localhost:8080/api/accounts"
        }
    }
	}

	 */
	
	
	
	
	@PutMapping
	public ResponseEntity<?> replace(@RequestBody Account account){
		Account updatedAccount =service.save(account);
		
		EntityModel<Account> accountModel=assember.toModel(updatedAccount);
		
		
		return ResponseEntity.status(HttpStatus.OK).body(accountModel);
	}
	
	
	
	
	/**
	 * ==>
	 * 
	 * {
		    "id":1,
		    "accountNumber":"1234567891",
		    "balance" : 999
		}
		
		
		
		출력=>
		{
		    "id": 1,
		    "accountNumber": "1234567891",
		    "balance": 999.0,
		    "_links": {
		        "self": {
		            "href": "http://localhost:8080/api/accounts/1"
		        },
		        "collection": {
		            "href": "http://localhost:8080/api/accounts"
		        }
		    }
		}

	 */
	
	
	@PatchMapping("/{id}/deposit")
	public ResponseEntity<?> deposit(@PathVariable("id") Integer id, @RequestBody Amount amount){
		Account updatedAccount=service.deposit(amount.getAmout(), id);
		
		EntityModel<Account> accountModel=assember.toModel(updatedAccount);
	
		return ResponseEntity.status(HttpStatus.OK).body(accountModel);
	}
	
	
	@PatchMapping("/{id}/withdraw")
	public ResponseEntity<?> withdraw(@PathVariable("id") Integer id, @RequestBody Amount amount){
		Account updatedAccount=service.withdraw(amount.getAmout(), id);
		
		EntityModel<Account> accountModel=assember.toModel(updatedAccount);
		
		
		return ResponseEntity.status(HttpStatus.OK).body(accountModel);
	}
	
/**
 * 
 {
    "id": 3,
    "accountNumber": "1223565893",
    "balance": 1000.0,
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/accounts/3"
        },
        "deposits": {
            "href": "http://localhost:8080/api/accounts/3/deposit"
        },
        "withdrawals": {
            "href": "http://localhost:8080/api/accounts/3/withdraw"
        },
        "collection": {
            "href": "http://localhost:8080/api/accounts"
        }
    }
}

 * 	
 */
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id){
		try {
			service.delete(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	
}
