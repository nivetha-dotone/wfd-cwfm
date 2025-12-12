package com.wfd.dot1.cwfm.dao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.enums.DotType;
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
	public String getSelectedDotType() {
	    return QueryFileWatcher.getQuery("GET_SELECTED_DOT_TYPE");
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
	    @Override
	    public Integer  getSelectedDotType(Long principalEmployerId, Long businessTypeId) {
	    	String sql = getSelectedDotType();
	        //String sql = "select top 1 gwt.WorkflowType\r\n"
	        //		+ " from GATEPASSWORKFLOWTYPE gwt\r\n"
	        //		+ " join CMSBUUnitMapping cbu on cbu.BUId=gwt.BusinessTypeId \r\n"
	        //		+ " join CMSPRINCIPALEMPLOYER cpe on cpe.UNITID=cbu.UnitID where cbu.BUId=? and cbu.UnitID=? ORDER BY gwt.WorkflowTypeId DESC;";

	        List<Integer> results = jdbcTemplate.query(sql, new Object[]{businessTypeId, principalEmployerId},
	                (rs, rowNum) -> rs.getInt("WorkflowType"));

	            if (results.isEmpty()) {
	                return 0;
	            }

	            int workflowType = results.get(0);

	            // ✅ Map integer ID to enum name
	            for (DotType dotType : DotType.values()) {
	                if (dotType.getStatus() == workflowType) {
	                    return dotType.getStatus(); // returns the enum name like "LLWCWO"
	                }
	            }

	            return 0;
	        }

}
