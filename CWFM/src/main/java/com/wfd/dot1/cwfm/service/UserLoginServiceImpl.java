package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wfd.dot1.cwfm.dao.UserLoginDAO;
import com.wfd.dot1.cwfm.entity.UserLogin;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserLoginDAO userLoginRepository;

    @Transactional
    @Override
    public void addUser(UserLogin user) {
        // Implement your logic to add a user
        userLoginRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserLogin> getAllUsers() {
        // Implement your logic to get all users
        return userLoginRepository.findAll();
    }
}

