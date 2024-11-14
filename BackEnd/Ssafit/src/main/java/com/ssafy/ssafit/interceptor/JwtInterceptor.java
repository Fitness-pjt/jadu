package com.ssafy.ssafit.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.ssafit.jwt.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {
	private final String HEADER_AUTH = "access-token";
	
	private JwtUtil jwtUtil;
	
	public JwtInterceptor(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String token = request.getHeader(HEADER_AUTH);
		if(token != null) {
			jwtUtil.validate(token); // 토큰이 현재 유효한지 아닌지 확인
			return true;
		}
		
		throw new Exception("유효하지 않은 접근입니다.");
	}
}