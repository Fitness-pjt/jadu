package com.ssafy.ssafit.service.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.ssafit.jwt.JwtUtil;
import com.ssafy.ssafit.model.dto.TokenInfo;
import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.service.user.UserService;

@Service
public class AuthService {

	private UserService userService;
	private JwtUtil jwtUtil;
	private PasswordEncoder passwordEncoder;

	public AuthService(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
		this.passwordEncoder = passwordEncoder;
	}

	public TokenInfo login(User user) {
		System.out.println(user);
		User loginUser = null;

		if (user.getUserEmail() == null || user.getUserPassword() == null)
			throw new IllegalArgumentException("로그인 입력 정보 없음");

		// 입력된 이메일 날림 => user 정보 받아오기
		User getUser = userService.searchByEmail(user.getUserEmail());
		System.out.println(getUser);

		// 아이디 또는 비밀번호 없음
		String encodePw = getUser.getUserPassword();
		if (getUser == null || !passwordEncoder.matches(user.getUserPassword(), encodePw)) { // user가 입력한 pw와 db의 암호화된
			throw new IllegalArgumentException("아이디 또는 비밀번호 틀림");
		}

		// 로그인 성공
		loginUser = getUser;
		
		// 회원가입 정보가 맞을 경우, 로그인 요청 시 => JWT 발급
		return jwtUtil.createToken(loginUser);

	}

}
