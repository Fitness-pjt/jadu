package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.UserInfo;

public interface UserInfoDao {
	// UserInfo CRUD
		void insertUserInfo(UserInfo userInfo);

		UserInfo selectUserInfo(Integer userId);

		void updateUserInfo(UserInfo userInfo);
}
