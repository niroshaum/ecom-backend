package com.udm.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.udm.ecom.model.Customer;
import com.udm.ecom.repository.CustomerRepository;

@RestController
public class LoginController {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;


	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody Customer customer){
		Customer savedCustomer = null;
		ResponseEntity response = null;

		try {
			String hashPwd = passwordEncoder.encode(customer.getPwd());
			customer.setPwd(hashPwd);
			savedCustomer = customerRepository.save(customer);
			if (savedCustomer.getId()>0) {
				response=ResponseEntity
						.status(HttpStatus.CREATED)
						.body("Given user details are successfully registered");
			}
		} catch (Exception e) {
			response = ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An excepion occured due to "+e.getMessage());
		}

		return response;
	}

	@PostMapping("/api/login")
	public ResponseEntity<String> login(@RequestBody Customer customer){
		System.out.println("Customer email : "+customer.getEmail());
		customer.getEmail();
		String password = customer.getPwd();
		System.out.println("Password : "+password);

		List<Customer> cust = customerRepository.findByEmail(customer.getEmail());
		System.out.println("db password : "+cust.get(0).getPwd());
		if (cust.size() > 0 ) {
			if(passwordEncoder.matches(password, cust.get(0).getPwd())) {
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}



	}
}
