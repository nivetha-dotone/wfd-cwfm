package com.wfd.dot1.cwfm.service;

import com.wfd.dot1.cwfm.entity.UserLogin;

public interface UserLoginService {

	void addUser(UserLogin user);

	Object getAllUsers();

}
