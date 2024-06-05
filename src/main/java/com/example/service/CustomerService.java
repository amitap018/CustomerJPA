package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.custom.CustomRepository;
import com.example.model.Customer;
import com.example.repo.CustomerRepository;

import jakarta.transaction.Transactional;

@CrossOrigin(origins = "http://localhost:5500")
@RestController
@RequestMapping("/customer")
public class CustomerService {
	@Autowired
	CustomerRepository tm;

	@Autowired
	CustomRepository custRepo;

	public Customer readOne(@PathVariable int id) {
		return tm.findById(id).orElse(null);
	}

	public void update(@PathVariable int id, @RequestBody Customer newCustomer) {
		Optional<Customer> oldCustomer = tm.findById(id);
		oldCustomer.get().setName(newCustomer.getName());
		oldCustomer.get().setUsername(newCustomer.getUsername());
		oldCustomer.get().setPass(newCustomer.getPass());
		oldCustomer.get().setEmail(newCustomer.getEmail());
		oldCustomer.get().setPhone(newCustomer.getPhone());
		oldCustomer.get().setBalance(newCustomer.getBalance());
		tm.save(oldCustomer.get());

	}

	@Transactional
	public String deposit(@PathVariable int id, @PathVariable int amount) {
		custRepo.depositUpdate(id, amount);
		return "Transaction Successful";

	}

	@Transactional
	public void withdrawl(@PathVariable int id, @PathVariable int amount) {
		custRepo.withdwarlUpdate(id, amount);
	}

	@Transactional
	public void moneyTransfer(@PathVariable int id, @PathVariable int amount, @PathVariable int receiverId) {
		custRepo.moneyTransfer(id, amount, receiverId);
	}
	
	@Transactional
    public boolean login(@PathVariable String username,@PathVariable String pass) {
        Customer customer = tm.findByUsername(username);

        if (customer != null && customer.getPass().equals(pass)) {
            return true; // Password matches, login successful
        } else {
            return false; // Either customer not found or password doesn't match
        }
    }

}
