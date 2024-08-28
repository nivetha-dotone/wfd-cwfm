package com.wfd.dot1.cwfm.dao;
import java.util.List;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.entity.Emp;
import com.wfd.dot1.cwfm.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public int saveUser(User user) {
        return (Integer) hibernateTemplate.save(user);
    }

    public User loginUser(String email, String password) {
        String sql = "from User where email=:em and password=:pwd";
        return (User) hibernateTemplate.execute(session -> {
            Query<User> query = session.createQuery(sql, User.class);
            query.setParameter("em", email);
            query.setParameter("pwd", password);
            return query.uniqueResult();
        });
    }
    
    public List<User> getAllUsers() {
        try {
            List<User> list = hibernateTemplate.loadAll(User.class);
            System.out.println("checkk3 "+list.get(0).getFullname());
            System.out.println(list.get(0).getFullname());
            return list;
        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Failed to retrieve all users", e);
        }
    }
}

