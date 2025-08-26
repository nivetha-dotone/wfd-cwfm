package com.wfd.dot1.cwfm.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.pojo.DeptMapping;
import com.wfd.dot1.cwfm.pojo.WorkmenBulkUpload;

@Repository
public class DepartmentMappingDaoImpl implements DepartmentMappingDao{

	@Autowired
    private JdbcTemplate jdbcTemplate;

	 @Override
	    public boolean existsMapping(int principalEmployerId, int departmentId) {
	        String sql = "SELECT COUNT(*) FROM UnitDepartmentMapping WHERE principalEmployerId = ? AND departmentId = ?";
	        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, principalEmployerId, departmentId);
	        return count != null && count > 0;
	    }
	 
	 @Override
	    public String getPrincipalEmployerNameById(int id) {
	        String sql = "SELECT NAME FROM CMSPRINCIPALEMPLOYER WHERE UNITID = ?";
	        return jdbcTemplate.queryForObject(sql, String.class, id);
	    }

	    @Override
	    public String getDepartmentNameById(int id) {
	        String sql = "SELECT GMNAME FROM CMSGENERALMASTER WHERE GMID = ?";
	        return jdbcTemplate.queryForObject(sql, String.class, id);
	    }
	 
	 @Override
	    public void save(List<DeptMapping> mappings) {
	        String sql = "INSERT INTO UnitDepartmentMapping (principalEmployerId, departmentId, subDepartmentId) VALUES (?, ?, ?)";
	        for (DeptMapping mapping : mappings) {
	            jdbcTemplate.update(sql, mapping.getPrincipalEmployerId(), mapping.getDepartmentId(), mapping.getSubDepartmentId());
	        }
	 }
	 @Override
	    public List<DeptMapping> findAll() {
		 List<DeptMapping> peList= new ArrayList<DeptMapping>();
	        String sql = "SELECT  pe.name AS principalEmployerName,cmsg.GMNAME AS departmentName,  cmsg1.GMNAME AS subDepartmentName  FROM UnitDepartmentMapping udm \r\n"
	        		+ "JOIN CMSPRINCIPALEMPLOYER pe ON udm.principalEmployerId = pe.UNITID \r\n"
	        		+ "left JOIN CMSGENERALMASTER cmsg ON udm.departmentId = cmsg.GMID \r\n"
	        		+ "left JOIN CMSGENERALMASTER cmsg1 ON udm.subDepartmentId = cmsg1.GMID ";
	        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
	    	while(rs.next()) {
	        	DeptMapping mapping = new DeptMapping();
	           // mapping.setMappingId(rs.getInt("mappingId"));
	            mapping.setPrincipalEmployer(rs.getString("principalEmployerName"));
	            mapping.setDepartment(rs.getString("departmentName"));
	            mapping.setSubDepartment(rs.getString("subDepartmentName"));
	            peList.add(mapping);
	    	}
	    	return peList;
	    	}
}
