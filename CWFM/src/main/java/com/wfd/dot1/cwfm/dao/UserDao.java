package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.entity.Emp;
import com.wfd.dot1.cwfm.entity.User;

public interface UserDao {

	public int saveUser(User user);

	public User loginUser(String email, String passsword);
	public List<User> getAllUsers();
}
