package com.ssafy.ssafit.service.user;

import org.springframework.stereotype.Service;

import com.ssafy.ssafit.model.dao.UserInfoDao;
import com.ssafy.ssafit.model.dto.UserInfo;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	UserInfoDao userInfoDao;

	public UserInfoServiceImpl(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	@Override
	public void insertUserInfo(UserInfo userInfo) {
		userInfoDao.insertUserInfo(userInfo);
	}

	@Override
	public UserInfo selectUserInfo(Integer userId) {
		return userInfoDao.selectUserInfo(userId);

	}

	@Override
	public void updateUserInfo(UserInfo userInfo) {
		userInfoDao.updateUserInfo(userInfo);
	}

}
