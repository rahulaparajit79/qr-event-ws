package com.event.qr.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.event.qr.util.ResponseObject;
import com.event.qr.util.ResponseList;
import com.event.qr.util.ResponseCodes;

import com.event.qr.service.UserService;
import com.event.qr.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userservice;

	@PostMapping("/add")
	public ResponseObject<User> saveUser(@RequestBody User user) {

		return userservice.saveUser(user);
	}

	@GetMapping("/getbyid")
	public ResponseObject<User> getUserById(@RequestParam(value = "id", required = true) int id) {

		return userservice.getUserById(id);

	}

	@GetMapping("/listall")
	public ResponseList<User> getUserListAll() {

		return userservice.getAllUsers();
	}

	@PutMapping("/update")
	public ResponseObject<User> updateUser(@RequestBody User user) {

		return userservice.updateUser(user);
	}

	@DeleteMapping("/delete")
	public ResponseObject<User> deleteUserById(@RequestParam(value = "id", required = true) int id) {

		return userservice.deleteUserById(id);

	}

}