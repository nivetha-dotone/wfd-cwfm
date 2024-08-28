package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.entity.UserLogin;

public interface UserLoginDAO {

	void save(UserLogin user);

	List<UserLogin> findAll();

}
