package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.model.Customer;
import com.example.repo.CustomerRepository;
import com.example.service.CustomerService;

@CrossOrigin(origins = "*")
@Configuration
@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerRepository cr;
	@Autowired
	CustomerService cs;



	@PutMapping("/deposit/{id}/{amount}")
	public int deposit(@PathVariable int id, @PathVariable int amount) {
		cs.deposit(id, amount);
		return checkBalance(id);
	}

	@PutMapping("/withdrawl/{id}/{amount}")
	public ResponseEntity<Object> withdrawl(@PathVariable int id, @PathVariable int amount) {

		try {
			int currentBalance = checkBalance(id);
			if (currentBalance >= amount) {
				cs.withdrawl(id, amount);
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}

	}

	@GetMapping("/checkBalance/{id}")
	public int checkBalance(@PathVariable int id) {
		Customer currentCustomer = cs.readOne(id);
		int currentBalance = 0;
		if (currentCustomer != null) {
			currentBalance = currentCustomer.getBalance();
			return currentBalance;
		} else {
			return 0;
		}

	}

	@PutMapping("/moneyTransfer/{id}/{amount}/{receiverId}")
	public ResponseEntity<Object> moneyTransfer(@PathVariable int id, @PathVariable int amount,
			@PathVariable int receiverId) {
		try {
			int currentBalance = checkBalance(id);
			if (currentBalance >= amount) {
				cs.moneyTransfer(id, amount, receiverId);
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}
	
	@PutMapping("/login/{username}/{pass}")
    public boolean login(@PathVariable String username,@PathVariable String pass) {
       return  cs.login(username, pass);
    }
    
    

}
