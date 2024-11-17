package com.ssafy.ssafit.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.ssafit.jwt.JwtUtil;
import com.ssafy.ssafit.model.dto.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	private final JwtUtil jwtUtil;

	public JwtAuthFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String accessToken = request.getHeader("access-token");
		// System.out.println("accessToken : " + accessToken);

		if (accessToken == null) {
			filterChain.doFilter(request, response);
			return;
		}

		try {

			// 토큰 위조 검사 및 인증 완료 처리
			if (accessToken != null) {
				Jws<Claims> claims = jwtUtil.validate(accessToken);// 토큰이 현재 유효한지 아닌지 확인
				// System.out.println("claims: " + claims);

				User userInfo = new User();
				String userNickname = claims.getBody().get("userNickname", String.class); // 클레임 안에 담겨 있는 userNickname 값
																							// // // 얻어오기
				Integer userId = claims.getBody().get("userId", Integer.class); // 클레임 안에 담겨 있는 userId 값 얻어오기
				// System.out.println("userNickname : " + userNickname + ", userId : " + userId);

				userInfo.setUserNickname(userNickname);
				userInfo.setUserId(userId);

				// 권한 정보(인가 정보) 리스트
				List<GrantedAuthority> authorityList = new ArrayList<>();

				// principla : 사용자 이름 또는 사용자 객체 , credential : 비밀번호, 주로 null
				SecurityContextHolder.getContext()
						.setAuthentication(new UsernamePasswordAuthenticationToken(userInfo, null, authorityList));

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Invalid or expired token");
			response.getWriter().flush();
			System.out.println("토큰이 위조 되었습니다.");
			return; // 요청 처리 중단
		}
		// 필터 체인에 내가 만든 필터 실행 명령
		filterChain.doFilter(request, response);
	}

}
