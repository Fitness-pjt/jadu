package com.ssafy.ssafit.model.dao;

import org.apache.ibatis.annotations.Param;

import com.ssafy.ssafit.model.dto.UserInfo;

public interface UserInfoDao {
	// UserInfo CRUD
	void insertUserInfo(UserInfo userInfo);

	UserInfo selectUserInfo(Integer userId);

	void updateUserInfo(UserInfo userInfo);

	void deleteKeyword(int userId);

	void insertKeyword(@Param("loginUserId") int loginUserId, @Param("keyword") int keyword);

}
