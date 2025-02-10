package com.wfd.dot1.cwfm.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
@Repository
public class PrincipalEmployerDaoImpl implements PrincipalEmployerDao {

	private static final Logger log = LoggerFactory.getLogger(PrincipalEmployerDaoImpl.class.getName());
	
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	    public String getAllPes() {
		    return QueryFileWatcher.getQuery("GET_ALL_PES");
		}
	    
	    public String getPeByUnitId() {
		    return QueryFileWatcher.getQuery("GET_PE_BY_UNITID");
		}
	 
	@Override
	public List<PrincipalEmployer> getAllPrincipalEmployer(String userAccount) {
		log.info("Entering into getAllPrincipalEmployer dao method ");
		List<PrincipalEmployer> peList= new ArrayList<PrincipalEmployer>();
		String query = getAllPes();
		log.info("Query to getAllPrincipalEmployer "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,userAccount);
		while(rs.next()) {
			PrincipalEmployer pe = new PrincipalEmployer();
			pe.setUnitId(rs.getInt("UNITID"));
			pe.setName(rs.getString("NAME"));
			pe.setAddress(rs.getString("ADDRESS"));
			pe.setManagerName(rs.getString("MANAGERNAME"));
			pe.setManagerAddrs(rs.getString("MANAGERADDRS"));
			pe.setBusinessType(rs.getString("BUSINESSTYPE"));
			pe.setMaxWorkmen(rs.getInt("MAXWORKMEN"));
			pe.setMaxCntrWorkmen(rs.getInt("MAXCNTRWORKMEN"));
			pe.setBocwApplicability(rs.getInt("BOCWAPPLICABILITY"));
			pe.setIsMwApplicability(rs.getInt("ISMWAPPLICABILITY"));
			pe.setCode(rs.getString("CODE"));
			pe.setOrganization(rs.getString("ORGANIZATION"));
			pe.setPfCode(rs.getString("PFCODE"));
			pe.setLicenseNumber(rs.getString("LICENSENUMBER"));
			pe.setWcNumber(rs.getString("WCNUMBER"));
			pe.setEsicNumber(rs.getString("ESICNUMBER"));
			pe.setPtRegNo(rs.getString("PTREGNO"));
			pe.setLwfRegNo(rs.getString("LWFREGNO"));
			pe.setFactoryLicenseNumber(rs.getString("FACTORYLICENCENUMBER"));
			pe.setIsRcApplicable(rs.getString("ISRCAPPLICABLE"));
			pe.setRcNumber(rs.getString("RCNUMBER"));
			pe.setAttachmentNm(rs.getString("ATTACHMENTNM"));
			pe.setStateId(rs.getInt("STATEID"));
			pe.setIsActive(rs.getInt("ISACTIVE"));
			
			peList.add(pe);
		}
		log.info("Exiting from getAllPrincipalEmployer dao method "+peList.size());
		return peList;
	}

	@Override
	public PrincipalEmployer getIndividualPEDetailByUnitId(String id) {
		log.info("Entering into getIndividualPEDetailByUnitId dao method ");
		PrincipalEmployer pe = new PrincipalEmployer();
		String query =getPeByUnitId();
		log.info("Query to getIndividualPEDetailByUnitId "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,id);
		while(rs.next()) {
		
			pe.setUnitId(rs.getInt("UNITID"));
			pe.setName(rs.getString("NAME"));
			pe.setAddress(rs.getString("ADDRESS"));
			pe.setManagerName(rs.getString("MANAGERNAME"));
			pe.setManagerAddrs(rs.getString("MANAGERADDRS"));
			pe.setBusinessType(rs.getString("BUSINESSTYPE"));
			pe.setMaxWorkmen(rs.getInt("MAXWORKMEN"));
			pe.setMaxCntrWorkmen(rs.getInt("MAXCNTRWORKMEN"));
			pe.setBocwApplicability(rs.getInt("BOCWAPPLICABILITY"));
			pe.setIsMwApplicability(rs.getInt("ISMWAPPLICABILITY"));
			pe.setCode(rs.getString("CODE"));
			pe.setOrganization(rs.getString("ORGANIZATION"));
			pe.setPfCode(rs.getString("PFCODE"));
			pe.setLicenseNumber(rs.getString("LICENSENUMBER"));
			pe.setWcNumber(rs.getString("WCNUMBER"));
			pe.setEsicNumber(rs.getString("ESICNUMBER"));
			pe.setPtRegNo(rs.getString("PTREGNO"));
			pe.setLwfRegNo(rs.getString("LWFREGNO"));
			pe.setFactoryLicenseNumber(rs.getString("FACTORYLICENCENUMBER"));
			pe.setIsRcApplicable(rs.getString("ISRCAPPLICABLE"));
			pe.setRcNumber(rs.getString("RCNUMBER"));
			pe.setAttachmentNm(rs.getString("ATTACHMENTNM"));
			pe.setStateId(rs.getInt("STATEID"));
			pe.setIsActive(rs.getInt("ISACTIVE"));
			
		}
		log.info("Exiting from getIndividualPEDetailByUnitId dao method ");
		return pe;
	}

}
