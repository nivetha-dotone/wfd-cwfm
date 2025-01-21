package com.wfd.dot1.cwfm.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.pojo.CMSPrincipalEmployer;

@Repository
public class CMSPRINCIPALEMPLOYERDAOImpl implements CMSPRINCIPALEMPLOYERDAO {
   // private static final String SELECT_ALL_CMSPRINCIPALEMPLOYERS = "SELECT * FROM CMSPRINCIPALEMPLOYER";
    private static final Logger logger = LoggerFactory.getLogger(CMSPRINCIPALEMPLOYERDAOImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
//    
//    @Override
//    public List<CMSPrincipalEmployer> getAllCMSPRINCIPALEMPLOYERs() {
//        String hql = "FROM CMSPrincipalEmployer";
//        List<CMSPrincipalEmployer> result = hibernateTemplate.loadAll(CMSPrincipalEmployer.class);
//        logger.info("Fetched " + result.size() + " CMSPrincipalEmployer records");
//        for (CMSPrincipalEmployer entity : result) {
//            logger.debug("Fetched CMSPrincipalEmployer: " + entity);
//        }
//        return result;
//    }
//    @Override
//    
//    public List<CMSPrincipalEmployer> getAllCMSPRINCIPALEMPLOYERs(int page, int pageSize) {
//        // Calculate the offset
//        int firstResult = (page - 1) * pageSize;
//        
//        String hql = "FROM CMSPrincipalEmployer";
//        Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql);
//        query.setFirstResult(firstResult);
//        query.setMaxResults(pageSize);
//        
//        return query.list();
//    }
////    
////    @Override
////    public List<CMSPrincipalEmployer> getAllCMSPRINCIPALEMPLOYERs(int page, int pageSize) {
////        // Calculate the offset
////    	 String hql = "FROM CMSPrincipalEmployer";
////    	    Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql);
////    	    int firstResult = (page - 1) * pageSize;
////    	    query.setFirstResult(firstResult);
////    	    query.setMaxResults(pageSize);
////    	    return query.list();
////    }
//
//    
//    @Override
//    public CMSPrincipalEmployer getCMSPRINCIPALEMPLOYERById(Long id) {
//        return hibernateTemplate.get(CMSPrincipalEmployer.class, id);
//    }
//    
//    @Override
//    public List<CMSPrincipalEmployer> getEmployeesAfterId(Long lastSeenId, int pageSize) {
//        // Construct the query to fetch employees after the last seen ID
//        String hql = "FROM CMSPrincipalEmployer WHERE id > :lastSeenId ORDER BY id ASC";
//        Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql);
//        query.setParameter("lastSeenId", lastSeenId);
//        query.setMaxResults(pageSize);
//        return query.list();
//    }
//    
//    @Override
//    public void addCMSPRINCIPALEMPLOYER(CMSPrincipalEmployer principalEmployer) {
//        hibernateTemplate.save(principalEmployer);
//    }
//    
//    
//    public void updateCMSPRINCIPALEMPLOYER(CMSPrincipalEmployer principalEmployer) {
//        try {
//            logger.info("Updating principal employer: {}", principalEmployer.getADDRESS());
//            entityManager.merge(principalEmployer);
//            logger.info("Principal employer updated successfully.");
//        } catch (Exception e) {
//            logger.error("An error occurred while updating principal employer: {}", e.getMessage(), e);
//            throw new RuntimeException("Failed to update principal employer.", e);
//        }
//    }
////    
////    @Override
////    public void updateCMSPRINCIPALEMPLOYER(CMSPRINCIPALEMPLOYER principalEmployer) {
////        try {
////            logger.info("Updating principal employer: {}", principalEmployer.getADDRESS());
////            sessionFactory.getCurrentSession().update(principalEmployer);
////            logger.info("Principal employer updated successfully.");
////        } catch (Exception e) {
////            logger.error("An error occurred while updating principal employer: {}", e.getMessage(), e);
////            throw new RuntimeException("Failed to update principal employer.", e);
////        }
////    }
//    
//    @Override
//    public void deleteCMSPRINCIPALEMPLOYER(Long id) {
//        CMSPrincipalEmployer principalEmployer = hibernateTemplate.get(CMSPrincipalEmployer.class, id);
//        hibernateTemplate.delete(principalEmployer);
//    }
//    
//    @Override
//    public List<CMSPrincipalEmployer> searchCMSPRINCIPALEMPLOYERs(String query) {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<CMSPrincipalEmployer> criteriaQuery = criteriaBuilder.createQuery(CMSPrincipalEmployer.class);
//        Root<CMSPrincipalEmployer> root = criteriaQuery.from(CMSPrincipalEmployer.class);
//
//        List<Predicate> predicates = new ArrayList<>();
//
//        predicates.add(criteriaBuilder.like(root.get("CODE"), "%" + query + "%"));
//        predicates.add(criteriaBuilder.like(root.get("NAME"), "%" + query + "%"));
//        predicates.add(criteriaBuilder.like(root.get("ORGANIZATION"), "%" + query + "%"));
//
//        Predicate finalPredicate = criteriaBuilder.or(predicates.toArray(new Predicate[0]));
//
//        criteriaQuery.where(finalPredicate);
//
//        return entityManager.createQuery(criteriaQuery).getResultList();
//    }
//    
//    @Override
//    public boolean existsByUnitCode(String unitCode) {
//        String hql = "SELECT COUNT(*) FROM CMSPrincipalEmployer WHERE CODE = :unitCode";
//        Query<Long> query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql, Long.class);
//        query.setParameter("unitCode", unitCode);
//        Long count = query.uniqueResult();
//        return count != null && count > 0;
//    }
//    
//    @Override
//    public boolean existsByUnitName(String unitName) {
//        String hql = "SELECT COUNT(*) FROM CMSPrincipalEmployer WHERE NAME = :unitName";
//        Query<Long> query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql, Long.class);
//        query.setParameter("unitName", unitName);
//        Long count = query.uniqueResult();
//        return count != null && count > 0;
//    }
//    
//    @Override
//    public boolean existsByOrganization(String organization) {
//        String hql = "SELECT COUNT(*) FROM CMSPrincipalEmployer WHERE ORGANIZATION = :organization";
//        Query<Long> query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql, Long.class);
//        query.setParameter("organization", organization);
//        Long count = query.uniqueResult();
//        return count != null && count > 0;
//    }
//    
//    @Override
//    public boolean existsByOrganizationAndNotUnitId(String organization, long unitId) {
//        String hql = "SELECT COUNT(*) FROM CMSPrincipalEmployer WHERE ORGANIZATION = :organization AND UNITID != :unitId";
//        Query<Long> query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql, Long.class);
//        query.setParameter("organization", organization);
//        query.setParameter("unitId", unitId);
//        Long count = query.uniqueResult();
//        return count != null && count > 0;
//    }
//    
//    @Override
//    public boolean existsByUnitCodeAndNotUnitId(String code, long unitId) {
//        String hql = "SELECT COUNT(*) FROM CMSPrincipalEmployer WHERE CODE = :code AND UNITID != :unitId";
//        Query<Long> query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql, Long.class);
//        query.setParameter("code", code);
//        query.setParameter("unitId", unitId);
//        Long count = query.uniqueResult();
//        return count != null && count > 0;
//    }
//
//    
//    @Override
//    public boolean existsByUnitNameAndNotUnitId(String name, long unitId) {
//        String hql = "SELECT COUNT(*) FROM CMSPrincipalEmployer WHERE NAME = :name AND UNITID != :unitId";
//        Query<Long> query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql, Long.class);
//        query.setParameter("name", name);
//        query.setParameter("unitId", unitId);
//        Long count = query.uniqueResult();
//        return count != null && count > 0;
//    }
//    @Override
//    public int getTotalRecords() {
//        String hql = "SELECT COUNT(*) FROM CMSPrincipalEmployer";
//        Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql);
//        Long count = (Long) query.uniqueResult();
//        return count.intValue();
//    }
   
	@Override
	public List<CMSPrincipalEmployer> getAllCMSPRINCIPALEMPLOYERs() {
		 String sql = "SELECT * FROM CMSPRINCIPALEMPLOYER";
	        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CMSPrincipalEmployer.class));
	}
	@Override
	public CMSPrincipalEmployer getCMSPRINCIPALEMPLOYERById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void addCMSPRINCIPALEMPLOYER(CMSPrincipalEmployer principalEmployer) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateCMSPRINCIPALEMPLOYER(CMSPrincipalEmployer principalEmployer) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteCMSPRINCIPALEMPLOYER(Long id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<CMSPrincipalEmployer> searchCMSPRINCIPALEMPLOYERs(String query) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean existsByUnitCode(String unitCode) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean existsByUnitName(String unitName) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean existsByOrganization(String organization) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean existsByOrganizationAndNotUnitId(String organization, long unitId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean existsByUnitCodeAndNotUnitId(String code, long unitId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean existsByUnitNameAndNotUnitId(String name, long unitId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<CMSPrincipalEmployer> getAllCMSPRINCIPALEMPLOYERs(int page, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getTotalRecords() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<CMSPrincipalEmployer> getEmployeesAfterId(Long lastSeenId, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}


}
