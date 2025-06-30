package com.wfd.dot1.cwfm.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

@Repository
public class DotTypeDaoImpl implements DotTypeDao {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public String findBuidByUnitId() {
	    return QueryFileWatcher.getQuery("FIND_BUID_BY_UNITID");
	}
	public String insertWorkflowType() {
	    return QueryFileWatcher.getQuery("INSERT_WORKFLOWTYPE");
	}
	 @Override
	    public Long findBuidByUnitId(Long unitId) {
		 String query = findBuidByUnitId();
		 //String sql = "SELECT BUID FROM CMSBUUnitMapping WHERE UnitID = ?";
		    try {
		        return jdbcTemplate.queryForObject(query, new Object[]{unitId}, Long.class);
		    } catch (EmptyResultDataAccessException e) {
		        return null;
		    }
	 }
	    @Override
	    public void insertWorkflowType(Long businessTypeId, int workflowType, String createdBy) {
	    	String query = insertWorkflowType();
	       // String sql = "INSERT INTO GATEPASSWORKFLOWTYPE (BusinessTypeId, WorkflowType, CreatedBy, CreatedDate, LastUpdatedBy, LastUpdatedDate) " +
	          //           "VALUES (?, ?, ?, GETDATE(), ?, GETDATE())";
	        jdbcTemplate.update(query, businessTypeId, workflowType, createdBy, createdBy);
	    }

}
