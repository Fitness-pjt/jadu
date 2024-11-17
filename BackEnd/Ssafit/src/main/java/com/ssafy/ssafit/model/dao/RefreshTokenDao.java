package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.RefreshToken;

public interface RefreshTokenDao {

	// Refresh Token DB 추가  
	void insertRefreshToken(RefreshToken refreshToken);

	// Refresh Token UserId로 찾기 
	String getRefreshTokenByUserId(int userId);

	// Refresh Token UserId로 삭제 
	void deleteRefreshTokenByUserId(int userId);
}
