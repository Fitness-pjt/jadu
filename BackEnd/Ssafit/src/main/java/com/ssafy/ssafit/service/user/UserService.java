package com.ssafy.ssafit.service.user;

import com.ssafy.ssafit.model.dto.User;

public interface UserService {
	public void signup(User user);

	public void update(User user);

	public User searchById(int id);

	public void remove(int id);

	public User searchByEmail(String userEmail);
	
	public void deleteUser(int id);
}
