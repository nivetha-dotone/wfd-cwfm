package com.wfd.dot1.cwfm.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wfd.dot1.cwfm.entity.Emp;

@Repository
@Transactional
public class EmpDaoImpl implements EmpDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public int saveEmp(Emp emp) {
        try {
            int id = (Integer) hibernateTemplate.save(emp);
            return id;
        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Failed to save employee", e);
        }
    }

    public Emp getEmpById(int id) {
        try {
            Emp emp = hibernateTemplate.get(Emp.class, id);
            return emp;
        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Failed to retrieve employee with id: " + id, e);
        }
    }

    public List<Emp> getAllEmp() {
        try {
            List<Emp> list = hibernateTemplate.loadAll(Emp.class);
            return list;
        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Failed to retrieve all employees", e);
        }
    }

    public void update(Emp emp) {
        try {
            hibernateTemplate.update(emp);
        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Failed to update employee with id: " + emp.getId(), e);
        }
    }

    public void deleteEmp(int id) {
        try {
            Emp emp = hibernateTemplate.get(Emp.class, id);
            if (emp != null) {
                hibernateTemplate.delete(emp);
            }
        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Failed to delete employee with id: " + id, e);
        }
    }
}
