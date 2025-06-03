package com.wfd.dot1.cwfm.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.MinimumWageDTO;
import com.wfd.dot1.cwfm.pojo.CMSContrPemm;
import com.wfd.dot1.cwfm.pojo.CMSSubContractor;
import com.wfd.dot1.cwfm.pojo.CMSWorkorderLN;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.ContractorWorkorderTYP;
import com.wfd.dot1.cwfm.pojo.MimumWageMasterTemplate;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.Workorder;

@Repository
public class FileUploadDaoImpl implements FileUploadDao {
	@Autowired
    private JdbcTemplate jdbcTemplate;
   
    @Override
    public void saveData(String[] data) {
    	 String rowData = String.join(",", data); // Convert array to CSV format
         String sql = "INSERT INTO CsvData (rowData) VALUES (?)";
         jdbcTemplate.update(sql, rowData);
		
        System.out.println("Saving data to the database: " + String.join(",", data));
    }
    
    

    @Override
    public void saveGeneralMaster(CmsGeneralMaster gm) {
    	 java.sql.Date createdDate = null;
    	    java.sql.Date updatedDate = null;
    	    
    	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	    
    	 // Parse CreatedTM if it's valid
    	    if (gm.getCreatedTM() != null && !gm.getCreatedTM().trim().equalsIgnoreCase("NULL") && !gm.getCreatedTM().trim().isEmpty()) {
    	        LocalDate createdLocalDate = LocalDate.parse(gm.getCreatedTM().trim(), formatter);
    	        createdDate = java.sql.Date.valueOf(createdLocalDate);
    	    }

    	    // Parse UpdatedTM if it's valid
    	    if (gm.getUpdatedTM() != null && !gm.getUpdatedTM().trim().equalsIgnoreCase("NULL") && !gm.getUpdatedTM().trim().isEmpty()) {
    	        LocalDate updatedLocalDate = LocalDate.parse(gm.getUpdatedTM().trim(), formatter);
    	        updatedDate = java.sql.Date.valueOf(updatedLocalDate);
    	    }
    	    
        String sql = "INSERT INTO CMSGENERALMASTER (GMNAME,GMDESCRIPTION,GMTYPEID,ISACTIVE,CREATEDTM,UPDATEDTM,UPDATEDBY)VALUES(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, gm.getGmName(), gm.getGmDescription(), gm.getGmTypeId(),gm.isActive(),createdDate, updatedDate, gm.getUpdatedBy());
     }
    
    
    @Override
    public Long insertIntoWageTable(MinimumWageDTO dto) {
        // Manually generate next wage ID
        Long nextWageId = jdbcTemplate.queryForObject("SELECT ISNULL(MAX(WAGEID), 0) + 1 FROM CMSWAGE", Long.class);

        String sql = "INSERT INTO CMSWAGE (WAGEID, BASIC, DA, ALLOWANCE) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, nextWageId, dto.getBasic(), dto.getDa(), dto.getAllowance());

        return nextWageId;
    }

    @Override
    public void insertIntoMinimumWageTable(LocalDate fromDate, Long wageId) {
        String sql = "INSERT INTO CMSMINIMUMWAGE (WAGEID, FRMDTM) VALUES (?, ?)";
        jdbcTemplate.update(sql, wageId, Date.valueOf(fromDate));
    }
    
    @Override
    public void savePrincipalEmployer(PrincipalEmployer pe) {
    	//java.sql.Date sqlDate = null;

       // if (pe.getUpdatedTM() != null && !pe.getUpdatedTM().trim().equalsIgnoreCase("NULL") && !pe.getUpdatedTM().trim().isEmpty()) {
       //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
       //     LocalDate localDate = LocalDate.parse(pe.getUpdatedTM().trim(), formatter);
       //     sqlDate = java.sql.Date.valueOf(localDate); // Convert LocalDate to java.sql.Date
        //}
		/*
		 * DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		 * LocalDate date = LocalDate.parse(pe.getUpdatedTM(), formatter);
		 */	
        String sql = "INSERT INTO CMSPRINCIPALEMPLOYER (ORGANIZATION,CODE,NAME,ADDRESS,MANAGERNAME,MANAGERADDRS,BUSINESSTYPE,MAXWORKMEN,MAXCNTRWORKMEN,BOCWAPPLICABILITY,ISMWAPPLICABILITY,LICENSENUMBER,PFCODE,WCNUMBER,FACTORYLICENCENUMBER) VALUES (?,?, ?, ?, ?,?,?, ?, ?, ?,?,?, ?, ?, ?)";
        jdbcTemplate.update(sql,pe.getOrganization(),pe.getCode(), pe.getName(), pe.getAddress(), pe.getManagerName(),pe.getManagerAddrs(),pe.getBusinessType(),pe.getMaxWorkmen(),pe.getMaxCntrWorkmen(),pe.getBocwApplicability(),pe.getIsMwApplicability(),pe.getLicenseNumber(),pe.getPfCode(),pe.getWcNumber(),pe.getFactoryLicenseNumber());
    }



	@Override
	public void saveMinimumWage(MimumWageMasterTemplate mw) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Long saveContractor(Contractor contractor) {
	    KeyHolder keyHolder = new GeneratedKeyHolder();

	    String sql = "INSERT INTO CMSCONTRACTOR(name, ADDRESS, city, reference, mobilenumber, CODE) VALUES (?, ?, ?, ?, ?, ?)";

	    jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        ps.setString(1, contractor.getContractorName());
	        ps.setString(2, contractor.getContractorAddress());
	        ps.setString(3, contractor.getCity());
	        ps.setString(4, contractor.getReference());
	        ps.setLong(5, contractor.getMobileNumber());
	        ps.setString(6, contractor.getContractorCode());
	        return ps;
	    }, keyHolder);

	    return keyHolder.getKey().longValue();  // This is your auto-generated contractorId
	}

	/*
	 * public String getUnitIdByCode(String code) { String sql =
	 * "SELECT UNITID FROM CMSPRINCIPALEMPLOYER WHERE CODE = ?"; return
	 * jdbcTemplate.queryForObject(sql, new Object[]{code}, String.class); }
	 */
	
	@Override
	public Long savePemm(CMSContrPemm pemm) {
	    String sql = "INSERT INTO CMSCONTRPEMM (CONTRACTORID, UNITID, MANAGERNM, LICENSENUM, VALIDFROMDT, VALIDTODT, COVERAGE, TOTALSTRENGTH, MAXNOEMP, NATUREOFWORK, LOCOFWORK, PERIODSTARTDT, PERIODENDDT, PFCODE, PFNUM, PFAPPLYDT, ESIWC, ESIVALIDFROM, ESIVALIDTO) " +
	                 "VALUES (?, 1, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection.prepareStatement(sql);
	        ps.setLong(1, pemm.getContractorId());
	        ps.setString(2, pemm.getManagerNm());
	        ps.setString(3, pemm.getLicenseNumber());
	        ps.setDate(4, new java.sql.Date(pemm.getLicenseValidFrom().getTime()));
	        ps.setDate(5, new java.sql.Date(pemm.getLicenseValidTo().getTime()));
	        ps.setString(6, pemm.getCoverage());
	        ps.setInt(7, pemm.getTotalStrength());
	        ps.setInt(8, pemm.getMaxNoEmp());
	        ps.setString(9, pemm.getNatureofWork());
	        ps.setString(10, pemm.getLocationofWork());
	        ps.setDate(11, new java.sql.Date(pemm.getPeriodStartDt().getTime()));
	        ps.setDate(12, new java.sql.Date(pemm.getPeriodEndDt().getTime()));
	        ps.setString(13, pemm.getPfCode());
	        ps.setString(14, pemm.getPfNum());
	        ps.setDate(15, new java.sql.Date(pemm.getPfApplyDt().getTime()));
	        ps.setString(16, pemm.getEsiwc());
	        ps.setDate(17, new java.sql.Date(pemm.getEsiValidFrom().getTime()));
	        ps.setDate(18, new java.sql.Date(pemm.getEsiValidTo().getTime()));
	        return ps;
	    });

	    return 1L; // explicitly returning 0 as unitId since it's hardcoded in the insert
	}



	
	@Override
	public void savewc(CmsContractorWC wc) {
	    String sql = "INSERT INTO CMSCONTRACTOR_WC(CONTRACTORID, UNITID, WC_CODE, WC_FROM_DTM, WC_TO_DTM, WC_TOTAL, LICENCE_TYPE) VALUES (?, ?, ?, ?, ?, ?,'wc')";
	    jdbcTemplate.update(sql,
	        wc.getContractorId(),
	        wc.getUnitId(),
	        wc.getWcCode(),
	        wc.getWcFromDtm(),
	        wc.getWcToDtm(),
	        wc.getWcTotal()	
	       // wc.getLicenceType() // if this should be NULL, pass `null` here
	    );
	}

    @Override
    public void savecsc(CMSSubContractor csc) {
        String sql = "insert into CMSSUBCONTRACTOR(CONTRACTOR_ID,WORKORDER_NO)values(?,?)";
        jdbcTemplate.update(sql,csc.getContractorId(),csc.getWorkOrderNumber());
    }



	@Override
	public Long saveWorkorder(Workorder workorder) {
		 KeyHolder keyHolder = new GeneratedKeyHolder();

		    String sql = "insert into CMSWORKORDER(UNITID,CONTRACTORID,name,VALIDFROM,VALIDDT,TYPEID,DEPID,SECID,GLCODE,COSTCENTER,STATUS,RELEASED_DATE,SAP_WORKORDER_NUM)values(2,2,'nam',?,?,1,1,1,?,?,1,?,?)";

		    jdbcTemplate.update(connection -> {
		        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		        ps.setString(1, workorder.getValidFrom());
		        ps.setString(2, workorder.getValidTo());
		        ps.setString(3, workorder.getGlCode());
		        ps.setString(4, workorder.getCostCenter());
		        ps.setString(5, workorder.getReleasedDate());
		        ps.setString(6, workorder.getSapWorkorderNumber());
		       
		        return ps;
		    }, keyHolder);

		    return keyHolder.getKey().longValue();  // This is your auto-generated contractorId
		
	}



	@Override
	public void saveWorkorderLN(CMSWorkorderLN woln) {
		 String sql = "insert into CMSWORKORDERLN(WORKORDERID,ITEM_NUM,DELIVERY_COMPLETED_SW,CHANGED_ON,JOB,RATE,QTY,PM_ORDER_NUM,WBS_ELEMENT,QTY_COMPLETED,SE_ENTRY_CREATED_ON,SE_ENTRY_UPDATED_ON)values(?,?,?,?,?,?,?,?,?,?,?,?)";
		 		
		    jdbcTemplate.update(sql,
		    		//woln.getw(),
		    		woln.getWorkorderid(),
		    		woln.getItemNum(),
		    		woln.getDeliveryCompletion(),
		    		woln.getChangedon(),
		    		woln.getJob(),
		    		woln.getRate(),
		    		woln.getQty(),
		    		woln.getPmOrderNum(),
		    		woln.getWbsElement(),
		    		woln.getQtyCompleted(),
		    		woln.getSeCreatedOn(),
		    		woln.getSeUpdatedOn() // if this should be NULL, pass `null` here
		    );
		
	}



	@Override
	public void saveWorkorderTyp(ContractorWorkorderTYP wotyp) {
		 String sql = "insert into CMSWORKORDERTYP(name,SAP_TYPE,SHORT_DESC)values('nam',?,?)";
	 		
		    jdbcTemplate.update(sql,
		    		//wotyp.getName(),
		    		wotyp.getSapType(),
		    		wotyp.getShortName()
		    		
		    );
	}
	public Long getWorkorderIdBySapNumber(String sapWorkorderNumber) {
	    String sql = "SELECT workorderid FROM CMSWORKORDER WHERE sapWorkorderNumber = ?";
	    try {
	        return jdbcTemplate.queryForObject(sql, new Object[]{sapWorkorderNumber}, Long.class);
	    } catch (EmptyResultDataAccessException e) {
	        return null;
	    }
	}

}

