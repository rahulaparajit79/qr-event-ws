package com.event.qr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {

	@GetMapping("/")
	public String getEventWelcome() {

		return "Welcome to Event QR Game App API.";

	}

}
