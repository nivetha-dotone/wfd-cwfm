package com.wfd.dot1.cwfm.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.pojo.Workorder;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
@Repository
public class WorkorderDaoImpl implements WorkorderDao{

	
	private static final Logger log = LoggerFactory.getLogger(WorkorderDaoImpl.class.getName());
	
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	
	 
	 public String getWOById() {
		    return QueryFileWatcher.getQuery("GET_WO_BY_ID");
		}
	@Override
	public Workorder getWorkorderById(String id) {
		String query=getWOById();
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,id);
        
        if (rs.next()) {
            Workorder wo = new Workorder();
            wo.setWorkorderId(rs.getString("WORKORDERID"));
            wo.setName(rs.getString("NAME"));
            wo.setSapWorkorderNumber(rs.getString("SAP_WORKORDER_NUM"));
            wo.setContractorId(rs.getString("CONTRACTORID"));
            wo.setValidFrom(rs.getString("VALIDFROM"));
            wo.setValidTo(rs.getString("VALIDDT"));
            wo.setUnitId(rs.getString("UNITID"));
            wo.setTypeId(String.valueOf(rs.getInt("TYPEID")));
            wo.setDepId(String.valueOf(rs.getInt("DEPID")));
            wo.setSecId(String.valueOf(rs.getInt("SECID")));
            wo.setStatus(String.valueOf(rs.getInt("STATUS")));
            wo.setEicNumber(rs.getString("EICNUMBER"));
            wo.setGlCode(rs.getString("GLCODE"));
            wo.setCostCenter(rs.getString("COSTCENTER"));
            return wo;
        }
    

return null;
	}
	
	@Override
    public Map<String, Workorder> getWorkOrdersByCodes(List<String> cleanedCodes) {
        if (cleanedCodes == null || cleanedCodes.isEmpty()) return Collections.emptyMap();

        String inSql = String.join(",", Collections.nCopies(cleanedCodes.size(), "?"));

        String sql = "select WORKORDERID,SAP_WORKORDER_NUM from CMSWORKORDER where SAP_WORKORDER_NUM IN (" + inSql + ")";

        List<Workorder> list = jdbcTemplate.query(sql, cleanedCodes.toArray(), (rs, rowNum) -> {
        	Workorder wo = new Workorder();
            wo.setWorkorderId(String.valueOf(rs.getInt("WORKORDERID")));
            wo.setSapWorkorderNumber(rs.getString("SAP_WORKORDER_NUM"));
            return wo;
        });

        return list.stream().collect(Collectors.toMap(Workorder::getSapWorkorderNumber, wo -> wo));
    }

}
