package com.wfd.dot1.cwfm.dao;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.entity.CMSMinimumWage;
import com.wfd.dot1.cwfm.entity.CMSWage;

@Repository
public class MinimumWageDAOImpl implements MinimumWageDAO {

    @PersistenceContext
    private EntityManager entityManager;
    private SessionFactory sessionFactory;
    private HibernateTemplate hibernateTemplate;

    @Override
    public List<CMSMinimumWage> findByPrincipalEmployerId(long principalEmployerId) {
        return entityManager.createQuery("SELECT mw FROM CMSMinimumWage mw WHERE mw.principalEmployerId = :principalEmployerId", CMSMinimumWage.class)
                .setParameter("principalEmployerId", principalEmployerId)
                .getResultList();
    }
    @SuppressWarnings("unchecked")
    @Override
    public String fetchTradeName(long tradeId) {
        List<String> results = hibernateTemplate.execute(session -> {
            return session.createQuery("select name from Trade where id = :tradeId", String.class)
                          .setParameter("tradeId", tradeId)
                          .getResultList();
        });
        return results.isEmpty() ? null : results.get(0);
    }
    @Override
    public String fetchSkillName(long skillId) {
        try (Session session = sessionFactory.openSession()) {
            List<String> results = session.createQuery("select name from Skill where id = :skillId", String.class)
                    .setParameter("skillId", skillId)
                    .getResultList();
            return results.isEmpty() ? null : results.get(0);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception appropriately
            return null;
        }
    }

    @Override
    public String fetchStateName(long stateId) {
        try (Session session = sessionFactory.openSession()) {
            List<String> results = session.createQuery("select name from State where id = :stateId", String.class)
                    .setParameter("stateId", stateId)
                    .getResultList();
            return results.isEmpty() ? null : results.get(0);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception appropriately
            return null;
        }
    }
    
//    @Override
//    public List<CMSMinimumWage> getMinimumWagesByPrincipalEmployerAndEffectiveDate(long principalEmployerId, Date effectiveDate) {
//    	List<Long> stateIds = entityManager.createQuery(
//    		    "SELECT pe.STATEID FROM CMSPeState pe WHERE pe.UNITID = :principalEmployerId", Long.class)
//    		    .setParameter("principalEmployerId", principalEmployerId)
//    		    .getResultList();
//        if (stateIds.isEmpty()) {
//            return Collections.emptyList();
//        }
//
//        String hql = "FROM CMSMinimumWage mw WHERE mw.stateId IN (:stateIds) AND :effectiveDate BETWEEN mw.fromDtm AND mw.toDtm";
//        return entityManager.createQuery(hql, CMSMinimumWage.class)
//                .setParameter("stateIds", stateIds)
//                .setParameter("effectiveDate", effectiveDate)
//                .getResultList();
//    }

   

    @Override
    public List<CMSMinimumWage> getMinimumWagesByPrincipalEmployerAndEffectiveDate(long principalEmployerId, Date effectiveDate) {
        System.out.println("Principal Employer ID: " + principalEmployerId);
        System.out.println("Effective Date: " + effectiveDate);
        
        List<Integer> stateIds = entityManager.createQuery(
                "SELECT pe.STATEID FROM CMSPrincipalEmployer pe WHERE pe.UNITID = :principalEmployerId", Integer.class)
                .setParameter("principalEmployerId", principalEmployerId)
                .getResultList();
        
        // Print stateIds, principalEmployerId, and effectiveDate for debugging
        System.out.println("State IDs: " + stateIds);
        System.out.println("Principal Employer ID: " + principalEmployerId);
        System.out.println("Effective Date: " + effectiveDate);
        
        if (stateIds.isEmpty()) {
            return Collections.emptyList();
        }

        // Convert effectiveDate to match the format 'yyyy-MM-dd HH:mm:ss'
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(effectiveDate);
        System.out.println("Effective Date222: " + effectiveDate);
        System.out.println("Effective formattedDate: " + formattedDate);
        
        // HQL query to fetch CMSMinimumWage objects along with related entities
        String hql = "SELECT mw FROM CMSMinimumWage mw " +
                     "JOIN FETCH mw.state " +
                     "JOIN FETCH mw.trade " +
                     "JOIN FETCH mw.skill " +
                     "JOIN FETCH mw.cmsWage " +
                     "WHERE mw.stateId IN (:stateIds) " +
                     "AND :effectiveDate BETWEEN mw.frmDtm AND mw.toDtm";
        
        // Log the HQL query with parameter values
        System.out.println("HQL Query: " + hql);
        System.out.println("Parameters: stateIds=" + stateIds + ", effectiveDate=" + formattedDate);

        List<CMSMinimumWage> minimumWages = null;
        
        try {
            minimumWages = entityManager.createQuery(hql, CMSMinimumWage.class)
                    .setParameter("stateIds", stateIds)
                    .setParameter("effectiveDate", effectiveDate, TemporalType.TIMESTAMP)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }

        // Log or print the retrieved minimum wages
        if (minimumWages != null && !minimumWages.isEmpty()) {
            for (CMSMinimumWage wage : minimumWages) {
                System.out.println(wage);
            }
        } else {
            System.out.println("No minimum wages found.");
        }

        return minimumWages != null ? minimumWages : Collections.emptyList();
    }


    @Override
    public CMSWage fetchCMSWage(long wageId) {
        try {
            return entityManager.createQuery("SELECT w FROM CMSWage w WHERE w.wageId = :wageId", CMSWage.class)
                    .setParameter("wageId", wageId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // No wage found for the given ID
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception appropriately
            return null;
        }
    }
	@Override
	public CMSWage fetchCMSWageBasedOnWageId(int wageId) {
		 try {
	            return entityManager.createQuery("SELECT w FROM CMSWage w WHERE w.wageId = :wageId", CMSWage.class)
	                    .setParameter("wageId", wageId)
	                    .getSingleResult();
	        } catch (NoResultException e) {
	            return null; // No wage found for the given ID
	        } catch (Exception e) {
	            e.printStackTrace(); // Handle exception appropriately
	            return null;
	        }
	}
}
