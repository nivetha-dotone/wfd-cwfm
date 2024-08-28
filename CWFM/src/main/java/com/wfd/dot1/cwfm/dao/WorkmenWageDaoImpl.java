package com.wfd.dot1.cwfm.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wfd.dot1.cwfm.entity.WorkmenWage;

@Repository
public class WorkmenWageDaoImpl implements WorkmenWageDao {
	@Autowired
    private DataSource dataSource; 
    @Autowired
    private HibernateTemplate hibernateTemplate;
    @Autowired
    private SessionFactory sessionFactory;
    private static final Logger logger = LoggerFactory.getLogger(WorkmenWageDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;
    @Transactional
    public int saveUser(WorkmenWage user) {
        return (Integer) hibernateTemplate.save(user);
    }

    public List<WorkmenWage> getAllWorkmenWage() {
        try {
            List<WorkmenWage> list = hibernateTemplate.loadAll(WorkmenWage.class);
            return list;
        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Failed to retrieve all workmen wages", e);
        }
    }

    @Transactional
    public void updateWorkmenWages(List<WorkmenWage> workmenWages) {
        for (WorkmenWage workmenWage : workmenWages) {
            hibernateTemplate.merge(workmenWage); // Use merge instead of update for detached entities
        }
    }
    @Override
    public WorkmenWage findById(String id) {
        WorkmenWage workmenWage = null;
        String sql = "SELECT * FROM CMS_WORKMEN_WAGE_MASTER WHERE WORKMEN_ID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    workmenWage = new WorkmenWage();
                    // Set all properties of the WorkmenWage object based on ResultSet values
                    workmenWage.setWORKMEN_ID(rs.getString("WORKMEN_ID"));
                    workmenWage.setINSURANCE_TYPE(rs.getString("INSURANCE_TYPE"));
                    workmenWage.setWAGE_TYPE(rs.getString("WAGE_TYPE"));
                    workmenWage.setWKMEN_CATOGERY(rs.getString("WKMEN_CATOGERY"));
                    workmenWage.setBASICPAY(rs.getString("BASICPAY"));
                    workmenWage.setDA(rs.getString("DA"));
                    workmenWage.setHRA(rs.getString("HRA"));
                    workmenWage.setOther_Allowances(rs.getString("Other_Allowances"));
                    workmenWage.setUniform_Allowance(rs.getString("Uniform_Allowance"));
                    workmenWage.setWashing_allowance(rs.getString("Washing_allowance"));
                    workmenWage.setStatutory_Bonus(rs.getString("Statutory_Bonus"));
                    workmenWage.setLeave_Encashment(rs.getString("Leave_Encashment"));
                    workmenWage.setEX_Serviceman_Allowance(rs.getString("EX_Serviceman_Allowance"));
                    workmenWage.setSupervisor_Allowance(rs.getString("Supervisor_Allowance"));
                    workmenWage.setHardship_Allowance(rs.getString("Hardship_Allowance"));
                    workmenWage.setGunman_Allowance(rs.getString("Gunman_Allowance"));
                    workmenWage.setTechnical_Allowance(rs.getString("Technical_Allowance"));
                    workmenWage.setDriver_Allowance(rs.getString("Driver_Allowance"));
                    workmenWage.setQRT_Allowance(rs.getString("QRT_Allowance"));
                    workmenWage.setVALID_FROM(rs.getString("VALID_FROM"));
                    workmenWage.setVALID_TO(rs.getString("VALID_TO"));
                    workmenWage.setELIG_STATE(rs.getString("ELIG_STATE"));
                    workmenWage.setACTIVE_STATUS(rs.getString("ACTIVE_STATUS"));
                    workmenWage.setRECORD_UPDATED(rs.getString("RECORD_UPDATED"));
                    workmenWage.setPF_CAP(rs.getString("PF_CAP"));
                    workmenWage.setUNIQID(rs.getString("UNIQID"));
                    workmenWage.setBonusPayout(rs.getString("BonusPayout"));
                    workmenWage.setPF(rs.getString("PF"));
                }
            }
        } catch (SQLException e) {
            // Handle exceptions
            e.printStackTrace();
        }
        return workmenWage;
    }

//    @Transactional
//    public void updateWorkmenWage(WorkmenWage workmenWage) {
//        // Fetch the workmen's wage object by WORKMEN_ID
//        WorkmenWage existingWorkmenWage = hibernateTemplate.get(WorkmenWage.class, workmenWage.getWORKMEN_ID());
//        if (existingWorkmenWage != null) {
//            // Update the fields of the existing workmen's wage object
//            existingWorkmenWage.setBASICPAY(workmenWage.getBASICPAY());
//            existingWorkmenWage.setDA(workmenWage.getDA());
//            existingWorkmenWage.setHRA(workmenWage.getHRA());
//            // Update other fields as needed
//            // Save the updated workmen's wage object
//            hibernateTemplate.update(existingWorkmenWage);
//        } else {
//            // Handle the case where the workmen's wage object is not found
//            // You can throw an exception or handle it according to your application's logic
//        }
//    }
    @Override
    @Transactional
    public void updateWorkmenWage(WorkmenWage workmenWage) {
        try {
            logger.info("Updating workmen wage: {}", workmenWage);
            entityManager.merge(workmenWage);
            logger.info("Workmen wage updated successfully.");
        } catch (Exception e) {
            logger.error("Error updating workmen wage: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update workmen wage", e);
        }
    }

    @Override
    public WorkmenWage findByWorkmenId(String workmenId) {
        return entityManager.find(WorkmenWage.class, workmenId);
    }

    @Override
    public void saveOrUpdate(WorkmenWage workmenWage) {
        entityManager.merge(workmenWage);
    }
//    @Override
//    public WorkmenWage findByWorkmenId(String workmenId) {
//        Session session = sessionFactory.getCurrentSession();
//        return session.get(WorkmenWage.class, workmenId);
//    }
//
//    @Override
//    public void saveOrUpdate(WorkmenWage workmenWage) {
//        Session session = sessionFactory.getCurrentSession();
//        session.saveOrUpdate(workmenWage);
//    }
//
//    @Override
//    public List<WorkmenWage> findAll() {
//        Session session = sessionFactory.getCurrentSession();
//        CriteriaBuilder builder = session.getCriteriaBuilder();
//        CriteriaQuery<WorkmenWage> criteria = builder.createQuery(WorkmenWage.class);
//        Root<WorkmenWage> root = criteria.from(WorkmenWage.class);
//        criteria.select(root);
//        Query<WorkmenWage> query = session.createQuery(criteria);
//        return query.getResultList();
//    }

}
