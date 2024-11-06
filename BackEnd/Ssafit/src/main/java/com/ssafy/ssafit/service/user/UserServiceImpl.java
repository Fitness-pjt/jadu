package com.ssafy.ssafit.service.user;

import org.springframework.stereotype.Service;

import com.ssafy.ssafit.model.dao.UserDao;
import com.ssafy.ssafit.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userdao;

	public UserServiceImpl(UserDao userdao) {
		this.userdao = userdao;
	}

	@Override
	public void signup(User user) {
		userdao.insertUser(user);
	}

}
