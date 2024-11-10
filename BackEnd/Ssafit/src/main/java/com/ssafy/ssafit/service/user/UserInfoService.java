package com.ssafy.ssafit.service.user;

import com.ssafy.ssafit.model.dto.UserInfo;

public interface UserInfoService {
	void insertUserInfo(UserInfo userInfo);

	UserInfo selectUserInfo(Integer userId);

	void updateUserInfo(UserInfo userInfo);
}
