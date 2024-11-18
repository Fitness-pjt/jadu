package com.ssafy.ssafit.model.dto;


public class KakaoTokenResponse {
	public String tokenType;
	public String accessToken;
	public String idToken;
	public Integer expiresIn;
	public String refreshToken;
	public Integer refreshTokenExpiresIn;
	public String scope;

	public KakaoTokenResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public KakaoTokenResponse(String tokenType, String accessToken, String idToken, Integer expiresIn,
			String refreshToken, Integer refreshTokenExpiresIn, String scope) {
		super();
		this.tokenType = tokenType;
		this.accessToken = accessToken;
		this.idToken = idToken;
		this.expiresIn = expiresIn;
		this.refreshToken = refreshToken;
		this.refreshTokenExpiresIn = refreshTokenExpiresIn;
		this.scope = scope;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getIdToken() {
		return idToken;
	}

	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Integer getRefreshTokenExpiresIn() {
		return refreshTokenExpiresIn;
	}

	public void setRefreshTokenExpiresIn(Integer refreshTokenExpiresIn) {
		this.refreshTokenExpiresIn = refreshTokenExpiresIn;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public String toString() {
		return "KakaoTokenResponse [tokenType=" + tokenType + ", accessToken=" + accessToken + ", idToken=" + idToken
				+ ", expiresIn=" + expiresIn + ", refreshToken=" + refreshToken + ", refreshTokenExpiresIn="
				+ refreshTokenExpiresIn + ", scope=" + scope + "]";
	}

}
