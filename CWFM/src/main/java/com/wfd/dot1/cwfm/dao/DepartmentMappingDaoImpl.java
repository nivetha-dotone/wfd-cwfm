package com.wfd.dot1.cwfm.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.pojo.DeptMapping;
import com.wfd.dot1.cwfm.pojo.WorkmenBulkUpload;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

@Repository
public class DepartmentMappingDaoImpl implements DepartmentMappingDao{

	@Autowired
    private JdbcTemplate jdbcTemplate;

	 public String existsPEDepMapping() {
		    return QueryFileWatcher.getQuery("GET_EXISTS_PE-DEP-MAPPING");
		}
	 public String getPrincipalEmployerNameById() {
		    return QueryFileWatcher.getQuery("GET_PE_NAME_BY_ID");
		}
	 public String getDepartmentNameById() {
		    return QueryFileWatcher.getQuery("GET_DEPT_NAME_BY_ID");
		}
	 public String getTradeNameById() {
		    return QueryFileWatcher.getQuery("GET_TRADE_NAME_BY_ID");
		}
	 public String saveUnitDeptMaping() {
		    return QueryFileWatcher.getQuery("SAVE_UNIT_DEPT_MAPPING");
		}
	 public String FindAllPeDeptMapping() {
		    return QueryFileWatcher.getQuery("GET_ALL_PE_DEPT_LIST");
		}
	 public String GetAllTradeSkillMappings() {
		    return QueryFileWatcher.getQuery("GET_ALL_PE_TRADE_LIST");
		}
	 public String saveTradeSkillMappings() {
		    return QueryFileWatcher.getQuery("SAVE_TRADE_SKILL_MAPPING");
		}
	 public String existsTradeSkillMapping() {
		    return QueryFileWatcher.getQuery("GET_EXISTS_TRADE_SKILL_MAPPING");
		}
	 
	 @Override
	    public boolean existsMapping(int principalEmployerId, int departmentId) {
		   String sql=existsPEDepMapping();
	        //String sql = "SELECT COUNT(*) FROM UnitDepartmentMapping WHERE principalEmployerId = ? AND departmentId = ?";
	        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, principalEmployerId, departmentId);
	        return count != null && count > 0;
	    }
	 
	 @Override
	    public String getPrincipalEmployerNameById(int id) {
		 String sql=getPrincipalEmployerNameById();
	        //String sql = "SELECT NAME FROM CMSPRINCIPALEMPLOYER WHERE UNITID = ?";
	        return jdbcTemplate.queryForObject(sql, String.class, id);
	    }

	    @Override
	    public String getDepartmentNameById(int id) {
	    	String sql=getDepartmentNameById();
	       // String sql = "SELECT GMNAME FROM CMSGENERALMASTER WHERE GMID = ?";
	        return jdbcTemplate.queryForObject(sql, String.class, id);
	    }
	    
	    @Override
	    public String getTradeNameById(int id) {
	    	String sql=getTradeNameById();
	       // String sql = "SELECT GMNAME FROM CMSGENERALMASTER WHERE GMID = ?";
	        return jdbcTemplate.queryForObject(sql, String.class, id);
	    }
	 
	 @Override
	    public void save(List<DeptMapping> mappings) {
		 String sql=saveUnitDeptMaping();
	       // String sql = "INSERT INTO UnitDepartmentMapping (principalEmployerId, departmentId, subDepartmentId) VALUES (?, ?, ?)";
	        for (DeptMapping mapping : mappings) {
	            jdbcTemplate.update(sql, mapping.getPrincipalEmployerId(), mapping.getDepartmentId(), mapping.getSubDepartmentId());
	        }
	 }
	 @Override
	    public List<DeptMapping> findAll() {
		 List<DeptMapping> peList= new ArrayList<DeptMapping>();
		 String sql=FindAllPeDeptMapping();
	        /*String sql = "SELECT  pe.name AS principalEmployerName,cmsg.GMNAME AS departmentName,  cmsg1.GMNAME AS subDepartmentName  FROM UnitDepartmentMapping udm \r\n"
	        		+ "JOIN CMSPRINCIPALEMPLOYER pe ON udm.principalEmployerId = pe.UNITID \r\n"
	        		+ "JOIN CMSGENERALMASTER cmsg ON udm.departmentId = cmsg.GMID \r\n"
	        		+ "JOIN CMSGENERALMASTER cmsg1 ON udm.subDepartmentId = cmsg1.GMID ";*/
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
	 
	 @Override
	    public List<DeptMapping> findAllTradeSkillMappings() {
		 List<DeptMapping> peList= new ArrayList<DeptMapping>();
		 String sql=GetAllTradeSkillMappings();
	        /*String sql = "SELECT  pe.name AS principalEmployerName,cmsg.GMNAME AS Trade,  cmsg1.GMNAME AS Skill  FROM UnitTradeSkillMapping utsm \r\n"
	        		+ "	        		JOIN CMSPRINCIPALEMPLOYER pe ON utsm.PrincipalEmployerId = pe.UNITID \r\n"
	        		+ "	        		JOIN CMSGENERALMASTER cmsg ON utsm.TradeId = cmsg.GMID \r\n"
	        		+ "	        		JOIN CMSGENERALMASTER cmsg1 ON utsm.SkillId = cmsg1.GMID";*/
	        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
	    	while(rs.next()) {
	        	DeptMapping mapping = new DeptMapping();
	           // mapping.setMappingId(rs.getInt("mappingId"));
	            mapping.setPrincipalEmployer(rs.getString("PrincipalEmployerName"));
	            mapping.setTrade(rs.getString("Trade"));
	            mapping.setSkill(rs.getString("Skill"));
	            peList.add(mapping);
	    	}
	    	return peList;
	    	}
	 
	 @Override
	    public void saveTradeSkillMappings(List<DeptMapping> mappings) {
		 String sql=saveTradeSkillMappings();
	        //String sql = "INSERT INTO UnitTradeSkillMapping(principalEmployerId, tradeid, skillid) VALUES (?, ?, ?)";
	        for (DeptMapping mapping : mappings) {
	            jdbcTemplate.update(sql, mapping.getPrincipalEmployerId(), mapping.getTradeId(), mapping.getSkillId());
	        }
	 }

	@Override
	public boolean existsTradeSkillMapping(int principalEmployerId, int tradeId, int skillId) {
		String sql=existsTradeSkillMapping();
		  //String sql = "SELECT COUNT(*) FROM UnitTradeSkillMapping WHERE PrincipalEmployerId = ? AND TradeId = ? AND SkillId = ?";
	        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, principalEmployerId, tradeId,skillId);
	        return count != null && count > 0;
	    }
	@Override
	public String getSubDepartmentNameById(int subDepartmentId) {
		//String sql=getPrincipalEmployerNameById();
		 String sql = "SELECT GMNAME FROM CMSGENERALMASTER WHERE GMID = ?";
        return jdbcTemplate.queryForObject(sql, String.class, subDepartmentId);
    }
}
