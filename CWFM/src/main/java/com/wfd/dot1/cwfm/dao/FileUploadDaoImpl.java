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
    public Long savePrincipalEmployer(PrincipalEmployer p) {
    	 KeyHolder keyHolder = new GeneratedKeyHolder();
    	 
        String sql = "INSERT INTO CMSPRINCIPALEMPLOYER (ORGANIZATION,CODE,NAME,ADDRESS,MANAGERNAME,MANAGERADDRS,BUSINESSTYPE,MAXWORKMEN,MAXCNTRWORKMEN,BOCWAPPLICABILITY,ISMWAPPLICABILITY,LICENSENUMBER,PFCODE,WCNUMBER,FACTORYLICENCENUMBER) VALUES (?,?, ?, ?, ?,?,?, ?, ?, ?,?,?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        ps.setString(1, p.getOrganization());
	        ps.setString(2, p.getCode());
	        ps.setString(3, p.getName());
	        ps.setString(4, p.getAddress());
	        ps.setString(5, p.getManagerName());
	        ps.setString(6, p.getManagerAddrs());
	        ps.setString(7, p.getBusinessType());
	        ps.setInt(8, p.getMaxWorkmen());
	        ps.setInt(9, p.getMaxCntrWorkmen());
	        ps.setInt(10, p.getBocwApplicability());
	        ps.setLong(11, p.getIsMwApplicability());
	        ps.setString(12,p.getLicenseNumber());
	        ps.setString(13, p.getPfCode());
	        ps.setString(14, p.getWcNumber());
	        ps.setString(15,p.getFactoryLicenseNumber());
	        return ps;
	    }, keyHolder);

	    return keyHolder.getKey().longValue();  // This is your auto-generated unitId
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

	@Override
	public Long getUnitIdByPlantCodeAndOrg(String plantCode, String organization) {
	    String sql = "select unitid from CMSPRINCIPALEMPLOYER where code = ? and ORGANIZATION =? ";
	    try {
	        return jdbcTemplate.queryForObject(sql, new Object[]{plantCode, organization}, Long.class);
	    } catch (EmptyResultDataAccessException e) {
	        return null;
	    }
	}

	
	@Override
	public void savePemm(CMSContrPemm pemm) {
	    String sql = "INSERT INTO CMSCONTRPEMM (CONTRACTORID, UNITID, MANAGERNM, LICENSENUM, VALIDFROMDT, VALIDTODT, COVERAGE, TOTALSTRENGTH, MAXNOEMP, NATUREOFWORK, LOCOFWORK, PERIODSTARTDT, PERIODENDDT, PFCODE, PFNUM, PFAPPLYDT, ESIWC, ESIVALIDFROM, ESIVALIDTO) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection.prepareStatement(sql);
	        ps.setLong(1, pemm.getContractorId());
	        ps.setLong(2, pemm.getUnitId());
	        ps.setString(3, pemm.getManagerNm());
	        ps.setString(4, pemm.getLicenseNumber());
	        ps.setDate(5, new java.sql.Date(pemm.getLicenseValidFrom().getTime()));
	        ps.setDate(6, new java.sql.Date(pemm.getLicenseValidTo().getTime()));
	        ps.setString(7, pemm.getCoverage());
	        ps.setInt(8, pemm.getTotalStrength());
	        ps.setInt(9, pemm.getMaxNoEmp());
	        ps.setString(10, pemm.getNatureofWork());
	        ps.setString(11, pemm.getLocationofWork());
	        ps.setDate(12, new java.sql.Date(pemm.getPeriodStartDt().getTime()));
	        ps.setDate(13, new java.sql.Date(pemm.getPeriodEndDt().getTime()));
	        ps.setString(14, pemm.getPfCode());
	        ps.setString(15, pemm.getPfNum());
	        ps.setDate(16, new java.sql.Date(pemm.getPfApplyDt().getTime()));
	        ps.setString(17, pemm.getEsiwc());
	        ps.setDate(18, new java.sql.Date(pemm.getEsiValidFrom().getTime()));
	        ps.setDate(19, new java.sql.Date(pemm.getEsiValidTo().getTime()));
	        return ps;
	    });

	   // explicitly returning 0 as unitId since it's hardcoded in the insert
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
	        //wc.getLicenceType() // if this should be NULL, pass `null` here
	    );
	}

    @Override
    public void savecsc(CMSSubContractor csc) {
        String sql = "insert into CMSSUBCONTRACTOR(CONTRACTOR_ID,WORKORDER_NO,UNITID)values(?,?,?)";
        jdbcTemplate.update(sql,csc.getContractorId(),csc.getWorkOrderNumber(),csc.getUnitId());
    }

    @Override
	public Long getContractorIdbyUnitId(Long unitId ) {
	    String sql = "select unitid from CMSPRINCIPALEMPLOYER where code = ? and ORGANIZATION =? ";
	    try {
	        return jdbcTemplate.queryForObject(sql, new Object[]{unitId}, Long.class);
	    } catch (EmptyResultDataAccessException e) {
	        return null;
	    }
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
	
	@Override
    public String getCSVHeaders(String templateType) {
        switch (templateType.toLowerCase()) {
            case "generalmaster":
                return "GMNAME,GMDESCRIPTION,GMTYPEID,ISACTIVE,CREATEDTM,UPDATEDTM,UPDATEDBY\n";

            case "principalemployer":
                return "ORGANISATION,PLANTCODE,NAME,ADDRESS,MANAGERNAME,MANAGERADDRS,BUSINESSTYPE,MAXWORKMEN,MAX CONTRACT WORKMEN,BOCWAPPLICABILITY,"
                		+ "ISMWAPPLICABILITY,LICENSENUMBER,PFCODE,ESWC,FACTORY LICENSE NUMBER,State\n";

            case "contractor":
                return "CONTRACTOR NAME,CONTRACTOR ADDRESS,City,Plant Code,Contractor MANAGER NAME,LICENSE NUM,LICENCSE VALID FROM,LICENCSE VALID TO,LICENCSE COVERAGE,"
                		+ "TOTAL STRENGTH,MAXIMUM NUMBER OF WORKMEN,NATURE OF WORK,LOCATION OF WORK,CONTRACTOR VALIDITY START DATE,CONTRACTOR VALIDITY END DATE,"
                		+ "CONTRACTOR ID,PF CODE,EC/WC number,EC/WC Validity Start Date,EC/WC Validity End Date,Coverage,PF NUMBER,PF APPLY DATE,Reference,"
                		+ "Mobile Number,ESI NUMBER,ESI VALID FROM,ESI VALID TO,Organisation,Main Contractor Code,Work Order Number\n";
            case "workorder":
            	return "Work Order Number,Item,Short Text,Delivery Complition,Item Changed ON,Work Order Validitiy From,Work Order Validitiy To,Work Order Type,"
            			+ "G/L Code,Plant code,Cost Center,Nature of Job,Rate,Quantity,PM Order No,WBS Element,Quantity Completed,Work Order Release Date,Service Entry Created Date,Service Entry Updated Date,Organisation\n";
            default:
                // fallback/default template
                return "Template is Not Found to Download";
        }
    }
	
	@Override
	public boolean isPrincipalEmployerCodeExists(String code) {
	    String sql = "SELECT COUNT(*) FROM CMSPRINCIPALEMPLOYER WHERE code = ?";
	    Integer count = jdbcTemplate.queryForObject(sql, Integer.class, code);
	    return count != null && count > 0;
	}

	@Override
	public boolean isContractorCodeExists(String contractorCode) {
		 String sql = "SELECT COUNT(*) FROM CMSCONTRACTOR WHERE code = ?";
		    Integer count = jdbcTemplate.queryForObject(sql, Integer.class, contractorCode);
		    return count != null && count > 0;
	}

	 @Override
	    public Long getStateIdByName(String stateName) {
	        String sql = "select STATEID from CMSSTATE WHERE STATENM = ?";
	        try {
	            return jdbcTemplate.queryForObject(sql, Long.class, stateName);
	        } catch (EmptyResultDataAccessException e) {
	            return null; // State not found
	        }
	    }

	 @Override
	    public void savePEState(Long unitId, Long stateId) {
	        String sql = "INSERT INTO CMSPESTATE (UNITID,STATEID ) VALUES (?, ?)";
	        jdbcTemplate.update(sql, unitId, stateId);
	    }
}

