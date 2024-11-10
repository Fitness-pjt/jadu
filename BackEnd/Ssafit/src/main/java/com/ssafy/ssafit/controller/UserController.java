package com.ssafy.ssafit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.service.user.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@Tag(name = "User API", description = "사용자 로그인 및 회원가입")
public class UserController {

	private UserService userService;
	private PasswordEncoder passwordEncoder;

	public UserController(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/signup")
	@Operation(summary = "사용자 회원가입", description = "회원가입을 합니다.")
	public ResponseEntity<String> signup(@RequestBody User user) {

		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
		userService.signup(user);

		return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
	}

	@PutMapping("/{userId}")
	@Operation(summary = "유저 정보 업데이트", description = "userId로 유저 정보를 수정합니다.")
	public ResponseEntity<String> updateUser(@RequestBody User user, @PathVariable(name = "userId") int userId) {

		user.setUserId(userId);
		System.out.println(user.toString());
		userService.update(user);

		return new ResponseEntity<>("Update", HttpStatus.OK);
	}

	@PostMapping("/login")
	@Operation(summary = "사용자 로그인 ", description = "로그인을 합니다.")
	public ResponseEntity<String> logIn(@RequestBody User user, HttpSession session) {
		User loginUser = null;
		if (user.getUserEmail() == null || user.getUserPassword() == null)
			return new ResponseEntity<>("입력 없음", HttpStatus.NOT_FOUND);
		// 입력된 이메일 날림
		User getUser = userService.searchByEmail(user.getUserEmail());
		// 아이디없음
		if (getUser == null) {
			return new ResponseEntity<>("아이디 틀림", HttpStatus.UNAUTHORIZED);
		}

		String encodePw = getUser.getUserPassword();
		if (passwordEncoder.matches(user.getUserPassword(), encodePw)) {

			loginUser = getUser;
			session.setAttribute("loginUser", loginUser);
			return new ResponseEntity<>("로그인 성공", HttpStatus.OK);
		}

		return new ResponseEntity<>("아이디 맞고, 비번 틀림", HttpStatus.UNAUTHORIZED);
	}

}
