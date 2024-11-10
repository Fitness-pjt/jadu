package com.ssafy.ssafit.service.user;

import java.util.List;

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
}
