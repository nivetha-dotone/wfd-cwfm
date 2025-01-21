package com.wfd.dot1.cwfm.dao;

import java.util.List;

import org.springframework.security.core.userdetails.User;

import com.wfd.dot1.cwfm.pojo.MasterUser;

public interface UserDAO {
    MasterUser getUserById(String userId);
    List<MasterUser> getAllUsers();
    void saveUser(MasterUser user);
    void updateUser(MasterUser user);
    void deleteUser(String userId);
	void saveUser(MasterUser user, List<Long> roleIds);
	void saveUser(User user, List<Long> roleIds);
}
