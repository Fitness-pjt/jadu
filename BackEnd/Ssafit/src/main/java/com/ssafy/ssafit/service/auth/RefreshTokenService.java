package com.ssafy.ssafit.service.auth;

import com.ssafy.ssafit.model.dto.RefreshToken;

public interface RefreshTokenService {

	// Refresh Token DB 추가  
	void addRefreshToken(RefreshToken refreshToken);

	// Refresh Token UserId로 찾기 
	String getRefreshTokenByUserId(int userId);

	// Refresh Token UserId로 삭제 
	void deleteRefreshTokenByUserId(int userId);
}
