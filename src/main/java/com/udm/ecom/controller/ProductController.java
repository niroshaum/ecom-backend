package com.udm.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.udm.ecom.model.Products;
import com.udm.ecom.repository.ProductRepository;

@RestController
public class ProductController {

	@Autowired
	ProductRepository productRepository;

	@GetMapping("/products")
	//	@CrossOrigin(origins = "http://localhost:3000")
	public List<Products> getAllProducts() {
		return productRepository.findAll();

	}

	@PostMapping("/upload")
	public ResponseEntity<String> uploadProducts(@RequestBody Products product){
		productRepository.save(product);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}