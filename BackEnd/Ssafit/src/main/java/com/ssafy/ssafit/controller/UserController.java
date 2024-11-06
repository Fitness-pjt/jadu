package com.ssafy.ssafit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.service.user.UserService;

@RestController
public class UserController {

	UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody User user) {

		System.out.println(user.toString());
		userService.signup(user);

		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

}
