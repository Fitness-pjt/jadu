package com.ssafy.ssafit.service.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class KakaoAuthService {

	private final String KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";

	@Value("${KAKAO_OAUTH_CLIENT_ID}")
	private String clientId;

	@Value("${KAKAO_OAUTH_REDIRECT_URI}")
	private String redirectUri;

	public String getAccessTokenFromKakao(String code) {
		try {
			// 요청 URL
			String url = KAUTH_TOKEN_URL_HOST + "/oauth/token";

			
			// 필요한 파라미터 설정
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add("code", code);
			params.add("client_id", clientId);
			params.add("redirect_uri", redirectUri); 
			params.add("grant_type", "authorization_code");

			// 요청 헤더 설정
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			// HTTP 요청 엔티티 생성
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

			// RestTemplate으로 POST 요청
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<JsonNode> responseNode = restTemplate.exchange(url, HttpMethod.POST, entity, JsonNode.class);

			// 응답에서 access token 추출
			JsonNode accessTokenNode = responseNode.getBody();
			if (accessTokenNode == null || !accessTokenNode.has("access_token")) {
				throw new IllegalStateException("액세스 토큰을 받아오지 못했습니다.");
			}

			return accessTokenNode.get("access_token").asText();

		} catch (Exception e) {
			throw new IllegalStateException("토큰 요청 중 오류 발생: " + e.getMessage());
		}
	}
}
