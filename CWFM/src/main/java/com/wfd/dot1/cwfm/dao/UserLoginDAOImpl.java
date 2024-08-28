package com.wfd.dot1.cwfm.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wfd.dot1.cwfm.entity.UserLogin;
@Repository
public class UserLoginDAOImpl implements UserLoginDAO{
	@Autowired
    private SessionFactory sessionFactory;
	@Override
    public void save(UserLogin user) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
    }
	@Transactional
    @Override
    public List<UserLogin> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from UserLogin", UserLogin.class).getResultList();
    }

}
