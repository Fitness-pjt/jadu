package com.ssafy.ssafit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssafit.model.dto.TokenInfo;
import com.ssafy.ssafit.service.auth.AuthService;

@RestController
@RequestMapping(value = "/login/oauth2", produces = "application/json")
public class SocialLoginController {

	AuthService authService;

	public SocialLoginController(AuthService authService) {
		this.authService = authService;
	}

	@GetMapping("/code/{registrationId}")
	public ResponseEntity<?> socialLogin(@RequestParam String code, @PathVariable String registrationId) {
		try {
//			System.out.println("SocialController Code : "+ code);
//			System.out.println( "SocialController registrationId : " + registrationId);
			
			TokenInfo tokenInfo = authService.socialLogin(code, registrationId);
//			System.out.println("SocialController tokenInfo : " + tokenInfo);
			return new ResponseEntity<>(tokenInfo, HttpStatus.OK);

		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}

}
