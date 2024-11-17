package com.ssafy.ssafit.model.dto;

import java.time.LocalDateTime;

public class RefreshToken {
	private int userId;
	private String refreshToken;
	private LocalDateTime expiresAt;

	public RefreshToken() {
	}

	public RefreshToken(int userId, String refreshToken, LocalDateTime expiresAt) {
		this.userId = userId;
		this.refreshToken = refreshToken;
		this.expiresAt = expiresAt;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}

	@Override
	public String toString() {
		return "RefreshToken [userId=" + userId + ", refreshToken=" + refreshToken + ", expiresAt=" + expiresAt + "]";
	}

}
