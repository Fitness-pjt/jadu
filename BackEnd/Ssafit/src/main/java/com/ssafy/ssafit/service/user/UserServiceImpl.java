package com.ssafy.ssafit.service.user;

import org.springframework.stereotype.Service;

import com.ssafy.ssafit.model.dao.UserDao;
import com.ssafy.ssafit.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void signup(User user) {
		userDao.insertUser(user);
	}

	@Override
	public void update(User user) {
		userDao.updateUser(user);
	}

	@Override
	public User searchById(int id) {

		return userDao.selectUserById(id);
	}

	@Override
	public void remove(int id) {

	}

	@Override
	public User searchByEmail(String userEmail) {

		return userDao.selectUserByEmail(userEmail);
	}

	@Override
	public void deleteUser(int userId) {

		userDao.deleteUser(userId);
	}

	@Override
	public boolean isEmailAvailable(String email) {
		try {
			User user = userDao.findByEmail(email);
			return user == null; // 사용자가 없으면 true (사용 가능)
		} catch (Exception e) {
			// 데이터베이스 오류 등이 발생하면 false 반환
			return false;
		}
	}

	@Override
	public boolean isNicknameAvailable(String nickname) {
		try {
			User user = userDao.findByNickname(nickname);
			return user == null; // 사용자가 없으면 true (사용 가능)
		} catch (Exception e) {
			// 데이터베이스 오류 등이 발생하면 false 반환
			return false;
		}
	}
}
