package com.ssafy.ssafit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssafit.jwt.JwtUtil;
import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.model.dto.UserInfo;
import com.ssafy.ssafit.service.user.UserInfoService;
import com.ssafy.ssafit.service.user.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user")
@Tag(name = "User API", description = "사용자 회원가입 및 CRUD")
public class UserController {

	private UserService userService;
	private UserInfoService userInfoService;
	private PasswordEncoder passwordEncoder;
	private JwtUtil jwtUtil;

	@Autowired
	public UserController(UserService userService, UserInfoService userInfoService, PasswordEncoder passwordEncoder,
			JwtUtil jwtUtil) {
		this.userService = userService;
		this.userInfoService = userInfoService;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
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
		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
		userService.update(user);

		return new ResponseEntity<>("Update", HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	@Operation(summary = "유저 정보 조회", description = "유저 정보 조회")
	public ResponseEntity<?> selectUserById(@PathVariable("userId") int userId) {
		User user = userService.searchById(userId);
		if (user == null)
			return new ResponseEntity<String>("해당 유저 없음", HttpStatus.NOT_FOUND);
		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	@DeleteMapping("/{userId}")
	@Operation(summary = "회원 탈퇴", description = "회원 정보 삭제 처리")
	public ResponseEntity<?> deleteUser(@PathVariable("userId") int userId) {

		userService.deleteUser(userId);
		return new ResponseEntity<String>("회원 탈퇴 완료", HttpStatus.OK);

	}

	@PostMapping("/info")
	@Operation(summary = "운동정보 등록", description = "운동정보 등록")
	public ResponseEntity<?> createUserInfo(@RequestBody UserInfo userInfo) {
		System.out.println(userInfo);

		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		userInfo.setUserId(loginUser.getUserId());
		userInfoService.insertUserInfo(userInfo);

		return new ResponseEntity<UserInfo>(userInfo, HttpStatus.OK);

	}

//	@PostMapping("/info/keyword")
//	@Operation(summary = "운동정보 키워드 등록", description = "운동정보 키워드 등록하기")
//	public ResponseEntity<?> createUserInfoKeyword(@RequestBody List<Integer> keywordList) {
//		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		int loginUserId = loginUser.getUserId();
//
////		System.out.println(keywordList);
//
//		for (int i = 0; i < keywordList.size(); i++) {
//			userInfoService.insertUserInfoKeyword(loginUserId, keywordList.get(i));
//			return new ResponseEntity<>("키워드 등록 완료", HttpStatus.OK);
//		}
//		return new ResponseEntity<>("키워드 등록 실패", HttpStatus.INTERNAL_SERVER_ERROR);
//	}

	@GetMapping("/info")
	@Operation(summary = "운동정보 조회", description = "운동정보 조회")
	public ResponseEntity<?> getUserInfo() {
		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		UserInfo userInfo = userInfoService.selectUserInfo(loginUser.getUserId());

		if (userInfo == null) {
			return new ResponseEntity<>("등록된 운동 정보가 없습니다.", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(userInfo, HttpStatus.OK);
	}

	@PutMapping("/info")
	@Operation(summary = "운동정보 수정", description = "운동정보 수정")
	public ResponseEntity<?> updateUserInfo(@RequestBody UserInfo userInfo) {
		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(userInfo);


		userInfo.setUserId(loginUser.getUserId());

		userInfoService.updateUserInfo(userInfo);
		return new ResponseEntity<>(userInfo, HttpStatus.OK);

	}

	@GetMapping("/check-email/{email}")
	@Operation(summary = "이메일 중복 확인", description = "회원가입 시 이메일 중복을 확인합니다.")
	public ResponseEntity<Map<String, Boolean>> checkEmailDuplicate(@PathVariable String email) {
		Map<String, Boolean> response = new HashMap<>();
		boolean isAvailable = userService.isEmailAvailable(email);
		response.put("available", isAvailable);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/check-nickname/{nickname}")
	@Operation(summary = "닉네임 중복 확인", description = "회원가입 시 닉네임 중복을 확인합니다.")
	public ResponseEntity<Map<String, Boolean>> checkNicknameDuplicate(@PathVariable String nickname) {
		Map<String, Boolean> response = new HashMap<>();
		boolean isAvailable = userService.isNicknameAvailable(nickname);
		response.put("available", isAvailable);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
