package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.security.core.userdetails.User;

import com.wfd.dot1.cwfm.pojo.MasterUser;

public interface UserService {
    MasterUser getUserById(String userId);
    List<MasterUser> getAllUsers();
    void saveUser(MasterUser user);
    void updateUser(MasterUser user);
    void deleteUser(String userId);
	void saveUser(MasterUser user, List<Long> roleIds);
}
