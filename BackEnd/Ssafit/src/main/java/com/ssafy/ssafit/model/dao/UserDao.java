package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.model.dto.UserInfo;

public interface UserDao {

	// 사용자 등록
	int insertUser(User user);

	// 사용자 정보 수정
	int updateUser(User user);

	// 사용자 삭제
	int deleteUser(int userId);

	// 사용자 단일 조회 (By ID)
	User selectUserById(int userId);

	User selectUserByEmail(String userEmail);

}
