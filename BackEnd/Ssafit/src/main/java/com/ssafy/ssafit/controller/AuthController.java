package com.ssafy.ssafit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssafit.model.dto.RefreshToken;
import com.ssafy.ssafit.model.dto.TokenInfo;
import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.service.auth.AuthService;
import com.ssafy.ssafit.service.auth.KakaoAuthService;
import com.ssafy.ssafit.service.auth.RefreshTokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth API", description = "로그인 인증/인가 ")
public class AuthController {

	private AuthService authService;
	private RefreshTokenService refreshTokenService;
	private KakaoAuthService kakaoAuthService;

	@Autowired
	public AuthController(AuthService authService, RefreshTokenService refreshTokenService,
			KakaoAuthService kakaoAuthService) {
		this.authService = authService;
		this.refreshTokenService = refreshTokenService;
		this.kakaoAuthService = kakaoAuthService;
	}

	@PostMapping("/login")
	@Operation(summary = "사용자 로그인 ", description = "로그인을 합니다.")
	public ResponseEntity<?> logIn(@RequestBody User user) {

		try {
//			System.out.println(user);
			TokenInfo tokenInfo = authService.login(user);

			// System.out.println("tokenInfo : " + tokenInfo);
//			String refreshToken = tokenInfo.getRefreshToken();

			RefreshToken refreshToken = new RefreshToken();
			refreshToken.setRefreshToken(tokenInfo.getRefreshToken());

			// System.out.println(refreshToken);
//			refreshTokenService.addRefreshToken(refreshToken);

			return new ResponseEntity<>(tokenInfo, HttpStatus.OK);

		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/kakaoLogin")
	public ResponseEntity<?> kakaoCallback(@RequestParam("code") String code) {
		try {
			// Service에서 Kakao API 호출하여 access_token 받기
			String accessToken = kakaoAuthService.getAccessTokenFromKakao(code);

			// 응답 반환
			return ResponseEntity.ok().body("Access Token: " + accessToken);
		} catch (Exception e) {
			// 에러 처리
			return ResponseEntity.status(500).body("Failed to retrieve access token: " + e.getMessage());
		}
	}

}
