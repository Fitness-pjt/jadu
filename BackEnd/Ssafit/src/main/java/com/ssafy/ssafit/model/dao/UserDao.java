package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.model.dto.UserInfo;

public interface UserDao {

	void insertUser(User user);

	User selectUserById(Integer userId);

	User selectUserByEmail(String userEmail);

	void updateUser(User user);

	void deleteUser(Integer userId);

	

}
