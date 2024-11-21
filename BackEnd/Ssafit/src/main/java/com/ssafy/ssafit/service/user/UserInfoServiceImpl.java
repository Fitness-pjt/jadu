package com.ssafy.ssafit.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ssafit.model.dao.UserInfoDao;
import com.ssafy.ssafit.model.dto.UserInfo;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	UserInfoDao userInfoDao;

	public UserInfoServiceImpl(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	@Override
	@Transactional
	public void insertUserInfo(UserInfo userInfo) {
		userInfoDao.insertUserInfo(userInfo);

		for (int keyword : userInfo.getKeyword()) {
			userInfoDao.insertKeyword(userInfo.getUserId(), keyword);
		}
	}

	@Override
	public UserInfo selectUserInfo(Integer userId) {
		return userInfoDao.selectUserInfo(userId);

	}

	@Override
	@Transactional
	public void updateUserInfo(UserInfo userInfo) {
		userInfoDao.updateUserInfo(userInfo);

		userInfoDao.deleteKeyword(userInfo.getUserId());

		for (int keyword : userInfo.getKeyword()) {
			userInfoDao.insertKeyword(userInfo.getUserId(), keyword);
		}
	}

}
