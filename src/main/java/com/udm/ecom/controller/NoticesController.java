package com.udm.ecom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticesController {

	@GetMapping("/notices")
	//	@CrossOrigin(origins = "http://localhost:3000")
	public String getNotices() {
		return "Here are the notices details from DB";
	}
}
