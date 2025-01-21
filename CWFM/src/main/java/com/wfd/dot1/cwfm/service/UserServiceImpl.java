package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.UserDAO;
import com.wfd.dot1.cwfm.pojo.MasterUser;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public MasterUser getUserById(String userId) {
        return userDAO.getUserById(userId);
    }

    @Override
    public List<MasterUser> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public void saveUser(MasterUser user) {
        userDAO.saveUser(user);
    }

    @Override
    public void updateUser(MasterUser user) {
        userDAO.updateUser(user);
    }

    @Override
    public void deleteUser(String userId) {
        userDAO.deleteUser(userId);
    }

	@Override
	public void saveUser(MasterUser user, List<Long> roleIds) {
		userDAO.saveUser(user,roleIds);	
	}
}
