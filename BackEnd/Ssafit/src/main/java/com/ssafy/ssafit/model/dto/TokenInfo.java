package com.ssafy.ssafit.model.dto;

public class TokenInfo {
	private String grantType; // 발행되는 토큰의 타입 = "Bearer"
	private String accessToken; // access-token
	private String refreshToken; // refresh-token

	public TokenInfo() {
	}

	public TokenInfo(String grantType, String accessToken) {
		this.grantType = grantType;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "TokenInfo [grantType=" + grantType + ", accessToken=" + accessToken + ", refreshToken=" + refreshToken
				+ "]";
	}
	
	

}
