package com.ssafy.ssafit.service.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.ssafy.ssafit.jwt.JwtUtil;
import com.ssafy.ssafit.model.dto.GoogleUser;
import com.ssafy.ssafit.model.dto.KakaoUser;
import com.ssafy.ssafit.model.dto.TokenInfo;
import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.service.user.UserService;

@Service
public class AuthService {
	private final Environment env;

	private UserService userService;
	private JwtUtil jwtUtil;
	private PasswordEncoder passwordEncoder;
	private final RestTemplate restTemplate = new RestTemplate();

	private final String KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";

	@Value("${KAKAO_OAUTH_CLIENT_ID}")
	private String clientId;

	@Value("${KAKAO_OAUTH_REDIRECT_URI}")
	private String redirectUri;

	public AuthService(Environment env, UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
		this.passwordEncoder = passwordEncoder;
		this.env = env;
	}

	public TokenInfo socialLogin(String code, String registrationId) {

		String accessToken = getAccessToken(code, registrationId);
//		System.out.println("accessToken = " + accessToken);

		if (registrationId.equals("google")) {

			GoogleUser googleUser = getUserInfo(accessToken);

			// 3. 회원가입/로그인 처리
			User user = userService.searchByEmail(googleUser.getEmail());

			if (user == null) {
				// 새로운 회원일 경우 회원가입 처리
				User newUser = new User(googleUser.getEmail(), googleUser.getName(), googleUser.getName());

				userService.signup(newUser);
				user = userService.searchByEmail(googleUser.getEmail());
			}

			// 4. JWT 토큰 발급
			return jwtUtil.createTokenInfo(user);

		} else {
			KakaoUser kakaoUser = getKakaoUserInfo(accessToken);

//			System.out.println("AuthService kakaoUser : " + kakaoUser);
			
			// 회원가입
			User user = userService.searchByEmail(kakaoUser.getEmail());
			
			if(user == null) {
				// 새로운 회원일 경우, 회원가입 시키기
				User newUser = new User(kakaoUser.getEmail(), kakaoUser.getName(), kakaoUser.getName(), kakaoUser.getProfileImgPath());
				
				userService.signup(newUser);
				user = userService.searchByEmail(kakaoUser.getEmail());
			}
			

			// 4. JWT 토큰 발급
			return jwtUtil.createTokenInfo(user);

		}

	}

	// Social-Login AccessToken 발급
	private String getAccessToken(String authorizationCode, String registrationId) {
		try {

			String tokenUri = "";

			// 필요한 파라미터 설정
			MultiValueMap<String, String> params;

			if (registrationId.equals("google")) {
				// Google OAuth2 토큰 엔드포인트 URL
				tokenUri = "https://oauth2.googleapis.com/token";

				// new Map 생성
				params = new LinkedMultiValueMap<>();
				params.add("code", authorizationCode);
				params.add("client_id", env.getProperty("spring.security.oauth2.client.registration.google.client-id"));
				params.add("client_secret",
						env.getProperty("spring.security.oauth2.client.registration.google.client-secret"));
				params.add("redirect_uri", "http://localhost:5173/login/oauth2/code/google");
				params.add("grant_type", "authorization_code");

			} else {
				// Kakao OAuth2 토큰 엔드포인트 URL
				tokenUri = KAUTH_TOKEN_URL_HOST + "/oauth/token";

				// new Map 생성
				params = new LinkedMultiValueMap<>();
				params.add("code", authorizationCode);
				params.add("client_id", clientId);
				params.add("redirect_uri", redirectUri);
				params.add("grant_type", "authorization_code");
			}

			// 헤더 설정
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			// HTTP 요청 엔티티 생성
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

			// RestTemplate으로 POST 요청
			ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity,
					JsonNode.class);

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

	// Google Login 유저 정보 받아오기
	private GoogleUser getUserInfo(String accessToken) {
		try {
			// Google의 사용자 정보를 가져오는 엔드포인트
			String userInfoUri = "https://www.googleapis.com/oauth2/v2/userinfo";

			// 헤더 설정 (Bearer 토큰 포함)
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth(accessToken);

			// HTTP 엔티티 생성
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

			// RestTemplate으로 GET 요청
			ResponseEntity<JsonNode> userInfoResponse = restTemplate.exchange(userInfoUri, HttpMethod.GET, entity,
					JsonNode.class);

			// 응답 처리
			JsonNode userInfo = userInfoResponse.getBody();
			if (userInfo == null) {
				throw new IllegalStateException("사용자 정보를 가져오지 못했습니다.");
			}

			String email = userInfo.get("email").asText();
			String name = userInfo.has("name") ? userInfo.get("name").asText() : "";

			return new GoogleUser(email, name, "google");

		} catch (Exception e) {
			throw new IllegalStateException("사용자 정보 요청 중 오류 발생: " + e.getMessage());
		}
	}

	private KakaoUser getKakaoUserInfo(String accessToken) {
		try {
			// Kakao의 사용자 정보를 가져오는 엔드포인트
			String userInfoUri = "https://kapi.kakao.com/v2/user/me";

			// 헤더 설정 (Bearer 토큰 포함)
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth(accessToken);

			// HTTP 엔티티 생성
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

			// RestTemplate으로 GET 요청
			ResponseEntity<JsonNode> userInfoResponse = restTemplate.exchange(userInfoUri, HttpMethod.GET, entity,
					JsonNode.class);

			// 응답 처리
			JsonNode userInfo = userInfoResponse.getBody();
			if (userInfo == null) {
				throw new IllegalStateException("사용자 정보를 가져오지 못했습니다.");
			}

//			System.out.println(userInfo.toString());
			String name = userInfo.get("kakao_account").get("profile").get("nickname").asText();
			String email = userInfo.get("kakao_account").get("email").asText();
			String profileImg = userInfo.get("kakao_account").get("profile").get("profile_image_url").asText();
			

			return new KakaoUser(email, name, "kakao", profileImg);

		} catch (Exception e) {
			throw new IllegalStateException("사용자 정보 요청 중 오류 발생: " + e.getMessage());
		}
	}

	// 기본 이메일 로그인
	public TokenInfo login(User user) {
		User loginUser = null;

		if (user.getUserEmail() == null || user.getUserPassword() == null)
			throw new IllegalArgumentException("로그인 입력 정보 없음");

		// 입력된 이메일 날림 => user 정보 받아오기
		User getUser = userService.searchByEmail(user.getUserEmail());

		// 아이디 또는 비밀번호 없음
		String encodePw = getUser.getUserPassword();
		if (getUser == null || !passwordEncoder.matches(user.getUserPassword(), encodePw)) { // user가 입력한 pw와 db의 암호화된
			throw new IllegalArgumentException("아이디 또는 비밀번호 틀림");
		}

		// 로그인 성공
		loginUser = getUser;

		// 회원가입 정보가 맞을 경우, 로그인 요청 시 => JWT 발급
		return jwtUtil.createTokenInfo(loginUser);

	}

}
