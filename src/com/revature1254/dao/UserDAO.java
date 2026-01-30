package com.revature1254.dao;

import com.revature1254.model.User;

public interface UserDAO {
	void save(User user);

	User findByEmail(String email);

	void updatePassword(int userId, String password);

	void updateProfile(int userId, String name, String email);

}
