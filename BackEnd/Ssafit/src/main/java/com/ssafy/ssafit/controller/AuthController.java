package com.ssafy.ssafit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssafit.model.dto.RefreshToken;
import com.ssafy.ssafit.model.dto.TokenInfo;
import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.service.auth.AuthService;
import com.ssafy.ssafit.service.auth.RefreshTokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth API", description = "로그인 인증/인가 ")
public class AuthController {

	private AuthService authService;
	private RefreshTokenService refreshTokenService;
	

	@Autowired
	public AuthController(AuthService authService, RefreshTokenService refreshTokenService) {
		this.authService = authService;
		this.refreshTokenService = refreshTokenService;
	}

	@PostMapping("/login")
	@Operation(summary = "사용자 로그인 ", description = "로그인을 합니다.")
	public ResponseEntity<?> logIn(@RequestBody User user) {

		try {

			TokenInfo tokenInfo = authService.login(user);

			RefreshToken refreshToken = new RefreshToken();
			refreshToken.setRefreshToken(tokenInfo.getRefreshToken());

			return new ResponseEntity<>(tokenInfo, HttpStatus.OK);

		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}

}
