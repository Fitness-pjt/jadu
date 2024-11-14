package com.ssafy.ssafit.jwt;

import java.sql.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ssafy.ssafit.model.dto.TokenInfo;
import com.ssafy.ssafit.model.dto.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;


@Component
public class JwtUtil {

	/* 토큰 SECRET KEY */
	@Value("${JWT_SECRET_KEY}")
	private String JWT_SECRET_KEY;
	private SecretKey secretKey; 
	
	
	// 1. 시크릿키를 생성하는 메서드
	// @PostConstruct로 secretKey 초기화
	// Spring Bean의 초기화 후, 딱 한 번만 실행되는 메서드를 지정할 때 사용
	@PostConstruct
	private void initSecretKey( ) {
		byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET_KEY); // deprecated 메서드 새로 작성 방법
 		this.secretKey =  Keys.hmacShaKeyFor(keyBytes); 
	}

	
	// 2. 토큰을 생성하는 메서드
	// access-token 생성
	public String createAccessToken(User user) {
		String nickname = user.getUserNickname();
		int userId = user.getUserId();
		// 토큰 유효기간 설정
		Date exp = new Date(System.currentTimeMillis() + 1000*60*60); // 1시간
		
		return  Jwts.builder()
				.header().add("type", "JWT").and()
				.claim("nickname", nickname).claim("userId", userId).expiration(exp)
				.signWith(secretKey).compact();
	}
	
	
	// refresh-token 생성
	public String createRefreshToken() {
		Date exp = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 14); // 2주일
		
		return Jwts.builder()
				.header().add("type", "JWT").and()
				.expiration(exp)
				.signWith(secretKey).compact();
	}
	
	// 생성한 access-token과 refresh-token을 TokenInfo에 넣기
	public TokenInfo createToken (User user) {
		String accessToken = createAccessToken(user);
		String refreshToken = createRefreshToken();
		
		TokenInfo tokenInfo = new TokenInfo();
		tokenInfo.setGrantType("Bearer");
		tokenInfo.setAccessToken(accessToken);
		tokenInfo.setRefreshToken(refreshToken);
		
		return tokenInfo;
	}
	
	
	
	// 토큰 유효성 검증
	// 주어진 token이 유효한지 확인
	// Jws<Claims> 객체 안에, 토큰에 담근 Claims 정보와 서명 검증 결과가 포함된다.
	public Jws<Claims> validate(String token){
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
	}
	
}