package com.wfd.dot1.cwfm.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.MinimumWageDTO;
import com.wfd.dot1.cwfm.pojo.CMSContrPemm;
import com.wfd.dot1.cwfm.pojo.CMSSubContractor;
import com.wfd.dot1.cwfm.pojo.CMSWorkorderLN;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.ContractorWorkorderTYP;
import com.wfd.dot1.cwfm.pojo.GatePassMain;
import com.wfd.dot1.cwfm.pojo.KTCWorkorderStaging;
import com.wfd.dot1.cwfm.pojo.MimumWageMasterTemplate;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.WorkmenBulkUpload;
import com.wfd.dot1.cwfm.pojo.Workorder;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

@Repository
public class FileUploadDaoImpl implements FileUploadDao {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	 public String saveGeneralMasterTemplate() {
		    return QueryFileWatcher.getQuery("SAVE_GENERAL_MASTER_TEMPLATE");
		}
	 public String isGmNameGmDescriptionExists() {
		    return QueryFileWatcher.getQuery("GMNAME_GMDESCRIPION_EXISTS");
		}
	 public String savePrincipalEmployer() {
		    return QueryFileWatcher.getQuery("SAVE_PRINCIPALEMPLOYER_TEMPLATE");
		}
	 public String saveContractorTemplate() {
		    return QueryFileWatcher.getQuery("SAVE_CONTRACTOR_TEMPLATE");
		}
	 public String getUnitIdByPlantCodeAndOrg() {
		    return QueryFileWatcher.getQuery("GET_UNITID_BY_PLANTCODE_ORG");
		}
	 public String savePemmForContTemplate() {
		    return QueryFileWatcher.getQuery("SAVE_PEMM_FOR_CONT_TEMPLATE");
		}
	 public String saveWCForContTemplate() {
		    return QueryFileWatcher.getQuery("SAVE_WC_FOR_CONT_TEMPLATE");
		}
	 public String saveCMSSUBCONTForContTemplate() {
		    return QueryFileWatcher.getQuery("SAVE_CMSSUBCONT_FOR_CONT_TEMPLATE");
		}
	 public String getContractorIdbyUnitId() {
		    return QueryFileWatcher.getQuery("GET_CONTRACTORID_BY_UNITID");
		}
	 public String isPrincipalEmployerCodeExists() {
		    return QueryFileWatcher.getQuery("IS_PRINCIPALEMPLOYER_EXISTS");
		}
	 public String isContractorCodeExists() {
		    return QueryFileWatcher.getQuery("IS_CONTRACTORCODE_EXISTS");
		}
	 public String getStateIdByName() {
		    return QueryFileWatcher.getQuery("GET_STATEID_BY_NAME");
		}
	 public String savePEState() {
		    return QueryFileWatcher.getQuery("SAVE_PE_STATE");
		}
	 public String saveWorkorderToStaging() {
		    return QueryFileWatcher.getQuery("SAVE_WORKORDER_TO_STAGGING");
		}
	 public String getTradeIdByName() {
		    return QueryFileWatcher.getQuery("GET_TRADEID_BY_NAME");
		}
	 public String getGeneralMasterId() {
		    return QueryFileWatcher.getQuery("GET_GENERALMASTER_ID");
		}
	 public String getWCECId() {
		    return QueryFileWatcher.getQuery("GET_WCECID");
		}
	 public String getUnitIdByName() {
		    return QueryFileWatcher.getQuery("GET_UNITID_BY_NAME");
		}
	 public String getContractorIdByName() {
		    return QueryFileWatcher.getQuery("GET_CONTRACTORID_BY_NAME");
		}
	 public String getSkillIdByName() {
		    return QueryFileWatcher.getQuery("GET_SKILLID_BY_NAME");
		}
	 public String getLlNumber() {
		    return QueryFileWatcher.getQuery("GET_LLNUMBER");
		}
	 public String geteicId() {
		    return QueryFileWatcher.getQuery("GET_EICID");
		}
	 public String getWorkorderId() {
		    return QueryFileWatcher.getQuery("GET_WORKORDER_ID");
		}
	 public String saveWorkmenBulkDraftUploadToStaging() {
		    return QueryFileWatcher.getQuery("SAVE_WORKMEN_BULK_DRAFT_UPLOAD_STAGGING");
		}
	 public String saveWorkmenBulkUploadToStaging() {
		    return QueryFileWatcher.getQuery("SAVE_WORKMEN_BULK_UPLOAD_STAGGING");
		}
	 public String getTransactionIdOfDraft() {
		    return QueryFileWatcher.getQuery("GET_TRANSACTIONID_OF_WORKMENDRAFT");
		}
	 public String saveToGatePassMain() {
		    return QueryFileWatcher.getQuery("SAVE_WORKMEN_DRAFT_BULK_IN_GATEPASS");
		}
	 public String updateRecordStatusByTransactionId() {
		    return QueryFileWatcher.getQuery("UPDATE_RECORDSTATUS_BY_TRANSACTIONID");
		}
	 public String isAadharNumberExistsInWorkmenDraft() {
		    return QueryFileWatcher.getQuery("IS_AADAHAR_EXISTS_IN_WORKMEN_DRAFT");
		}
	 public String isAadharNumberExistsInGatepass() {
		    return QueryFileWatcher.getQuery("IS_AADAHAR_EXISTS_IN_GATEPASS");
		}
    @Override
    public void saveData(String[] data) {
    	 String rowData = String.join(",", data); // Convert array to CSV format
         String sql = "INSERT INTO CsvData (rowData) VALUES (?)";
         jdbcTemplate.update(sql, rowData);
		
        System.out.println("Saving data to the database: " + String.join(",", data));
    }
    
    @Override
    public void saveGeneralMaster(CmsGeneralMaster gm) {
        
    	//String sql=saveGeneralMasterTemplate();
         String sql = "insert into CMSGENERALMASTER(GMNAME,GMDESCRIPTION,GMTYPEID,UPDATEDBY) values (?,?,?,'Admin')";
        jdbcTemplate.update(sql, gm.getGmName(), gm.getGmDescription(), gm.getGmTypeId());
    }

    @Override
	public boolean isGmNameGmDescriptionExists(String gmName, String gmDescription) {
    	String sql=isGmNameGmDescriptionExists();
		// String sql = "select count (*) from CMSGENERALMASTER where GMNAME=? and GMDESCRIPTION =?";
		    Integer count = jdbcTemplate.queryForObject(sql, Integer.class, gmName,gmDescription);
		    return count != null && count > 0;
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
    	 String sql=savePrincipalEmployer();
        //String sql = "INSERT INTO CMSPRINCIPALEMPLOYER (ORGANIZATION,CODE,NAME,ADDRESS,MANAGERNAME,MANAGERADDRS,BUSINESSTYPE,MAXWORKMEN,MAXCNTRWORKMEN,BOCWAPPLICABILITY,ISMWAPPLICABILITY,LICENSENUMBER,PFCODE,WCNUMBER,FACTORYLICENCENUMBER) VALUES (?,?, ?, ?, ?,?,?, ?, ?, ?,?,?, ?, ?, ?)";
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
	    String sql=saveContractorTemplate();
	    //String sql = "INSERT INTO CMSCONTRACTOR(name, ADDRESS, city, reference, mobilenumber, CODE) VALUES (?, ?, ?, ?, ?, ?)";

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
		String sql=getUnitIdByPlantCodeAndOrg();
	   // String sql = "select unitid from CMSPRINCIPALEMPLOYER where code = ? and ORGANIZATION =? ";
	    try {
	        return jdbcTemplate.queryForObject(sql, new Object[]{plantCode, organization}, Long.class);
	    } catch (EmptyResultDataAccessException e) {
	        return null;
	    }
	}
	
	@Override
	public void savePemm(CMSContrPemm pemm) {
		String sql=savePemmForContTemplate();
	   // String sql = "INSERT INTO CMSCONTRPEMM (CONTRACTORID, UNITID, MANAGERNM, LICENSENUM, VALIDFROMDT, VALIDTODT, COVERAGE, TOTALSTRENGTH, MAXNOEMP, NATUREOFWORK, LOCOFWORK, PERIODSTARTDT, PERIODENDDT, PFCODE, PFNUM, PFAPPLYDT, ESIWC, ESIVALIDFROM, ESIVALIDTO) " +
	    //             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
		String sql=saveWCForContTemplate();
	    //String sql = "INSERT INTO CMSCONTRACTOR_WC(CONTRACTORID, UNITID, WC_CODE, WC_FROM_DTM, WC_TO_DTM, WC_TOTAL, LICENCE_TYPE) VALUES (?, ?, ?, ?, ?, ?,'wc')";
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
    	String sql=saveCMSSUBCONTForContTemplate();
       // String sql = "insert into CMSSUBCONTRACTOR(CONTRACTOR_ID,WORKORDER_NO,UNITID)values(?,?,?)";
        jdbcTemplate.update(sql,csc.getContractorId(),csc.getWorkOrderNumber(),csc.getUnitId());
    }

    @Override
	public Long getContractorIdbyUnitId(Long unitId ) {
    	String sql=getContractorIdbyUnitId();
	    //String sql = "select unitid from CMSPRINCIPALEMPLOYER where code = ? and ORGANIZATION =? ";
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
        switch (templateType) {
            case "Data-General Master":
                return "GMNAME,GMDESCRIPTION,GMTYPEID\n";

            case "Data-Principal Employer":
                return "ORGANISATION,PLANTCODE,NAME,ADDRESS,MANAGERNAME,MANAGERADDRS,BUSINESSTYPE,MAXWORKMEN,MAX CONTRACT WORKMEN,BOCWAPPLICABILITY,"
                		+ "ISMWAPPLICABILITY,LICENSENUMBER,PFCODE,ESWC,FACTORY LICENSE NUMBER,State\n";

            case "Data-Contractor":
                return "CONTRACTOR NAME,CONTRACTOR ADDRESS,City,Plant Code,Contractor MANAGER NAME,LICENSE NUM,LICENCSE VALID FROM,LICENCSE VALID TO,LICENCSE COVERAGE,"
                		+ "TOTAL STRENGTH,MAXIMUM NUMBER OF WORKMEN,NATURE OF WORK,LOCATION OF WORK,CONTRACTOR VALIDITY START DATE,CONTRACTOR VALIDITY END DATE,"
                		+ "CONTRACTOR ID,PF CODE,EC/WC number,EC/WC Validity Start Date,EC/WC Validity End Date,Coverage,PF NUMBER,PF APPLY DATE,Reference,"
                		+ "Mobile Number,ESI NUMBER,ESI VALID FROM,ESI VALID TO,Organisation,Main Contractor Code,Work Order Number\n";
            case "Data-Work Order":
            	return "Work Order Number,Item,Line,Line Number,Service Code,Short Text,Delivery Completion,Item Changed ON,Vendor Code,Vendor Name,Vendor Address,Blocked Vendor,Work Order Validitiy From,Work Order Validitiy To,Work Order Type,"
                        + "Plant code,Section Code,Department Code,G/L Code,Cost Center,Nature of Job,Rate / Unit,Quantity,Base Unit of Measure,Work Order Released,PM Order No,WBS Element,Qty Completed,Work Order Release Date,Service Entry Created Date,Service Entry Updated Date,Purchase Org Level,Company_code\n";
            case "Data-Workmen Bulk Upload":
            	return "First Name*,Last Name*,Father's Name or Husband's Name*,Date of Birth*,Trade*,Skill*,Nature of Work*,Hazardous Area*,"
                		+ "Aadhar/Id proof number*,Vendor Code*,Gender*,Date of Joining,Department*,Area,Work Order Number*,PF A/C Number,Marital Status*,"
                		+ "Technical Technical/Non Technical*,Academic,Blood Group,Accommodation*,Bank Name Branch,Account Number,"
                		+ "Mobile Number,Emergency Contact Number*,Police verification Date Valid To,Health chekup Date,Access Levels*,ESIC Number,UNIT CODE*,Organization name,"
                		+ "EIC Number*,EC number*,UAN Number,Emergency Contact Person*,Is eligible for PF,SpecializationName,Insurance type,LL number,Address,Zone,IdMark\n";
            case "Data-Workmen Bulk Upload Draft":
              return      "First Name,Last Name,Father's Name or Husband's Name,Date of Birth,Trade,Skill,Nature of Work,Hazardous Area,"
                		+ "Aadhar/Id proof number,Vendor Code,Gender,Date of Joining,Department,Area,Work Order Number,PF A/C Number,Marital Status,"
                		+ "Technical Technical/Non Technical,Academic,Blood Group,Accommodation,Bank Name Branch,Account Number,"
                		+ "Mobile Number,Emergency Contact Number,Police verification Date Valid To,Health chekup Date,Access Levels,ESIC Number,UNIT CODE,Organization name,"
                		+ "EIC Number,EC number,UAN Number,Emergency Contact Person,Is eligible for PF,SpecializationName,Insurance type,LL number,Address,Zone,IdMark\n";
            case "Data-Trade Skill":
            	return "PLANT CODE,TRADE,SKILL";
            case "Data-Department Area":
            	return "PLANT CODE,DEPARTMENT,SUBDEPARTMENT";
            default:
                // fallback/default template
                return "Template is Not Found to Download";
        }
    }
	
	@Override
	public boolean isPrincipalEmployerCodeExists(String code) {
		String sql=isPrincipalEmployerCodeExists();
	   // String sql = "SELECT COUNT(*) FROM CMSPRINCIPALEMPLOYER WHERE code = ?";
	    Integer count = jdbcTemplate.queryForObject(sql, Integer.class, code);
	    return count != null && count > 0;
	}

	@Override
	public boolean isContractorCodeExists(String contractorCode) {
		String sql=isContractorCodeExists();
		 //String sql = "SELECT COUNT(*) FROM CMSCONTRACTOR WHERE code = ?";
		    Integer count = jdbcTemplate.queryForObject(sql, Integer.class, contractorCode);
		    return count != null && count > 0;
	}

	 @Override
	    public Long getStateIdByName(String stateName) {
			String sql=getStateIdByName();
	        //String sql = "select STATEID from CMSSTATE WHERE STATENM = ?";
	        try {
	            return jdbcTemplate.queryForObject(sql, Long.class, stateName);
	        } catch (EmptyResultDataAccessException e) {
	            return null; // State not found
	        }
	    }

	 @Override
	    public void savePEState(Long unitId, Long stateId) {
		 String sql=savePEState();
	        //String sql = "INSERT INTO CMSPESTATE (UNITID,STATEID ) VALUES (?, ?)";
	        jdbcTemplate.update(sql, unitId, stateId);
	    }
	 
	 private Date parseSqlDate(String input) {
		    try {
		        // Adjust to match your CSV date format
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		        LocalDate localDate = LocalDate.parse(input, formatter);
		        return Date.valueOf(localDate);
		    } catch (Exception e) {
		        return null; // or throw new RuntimeException("Invalid date: " + input);
		    }
		}

	 @Override
	 public void saveWorkorderToStaging(KTCWorkorderStaging workorder) {
		 String sql=saveWorkorderToStaging();
	     //String sql = "insert into KTC_WORKORDER_STAGING_ON_REQ(WORKORDER_NUM,ITEM_NUM,SVC_LN_ITEM_DEL,SVC_LN_ITEM_NUM,SVC_NUM,SVC_LN_ITEM_NAME,DELV_COMPLETION_SW,ITEM_CHANGED_ON_DATE,\r\n"
	     //		+ "VENDOR_CODE,VENDOR_NAME,VENDOR_ADDRESS,BLOCKED_PO,WORKORDER_VALID_FROM,WORKORDER_VALID_TO,SAP_WORKORDER_TYPE,UNIT_CODE,SEC_NAME,DEPT_NAME,\r\n"
	     //		+ "GL_CODE,COST_CENTRE_CODE,JOB_NAME,RATE,QTY,UOM,WORKORDER_RELEASED_SW,PM_WORKORDER_NUM,WBS_ELEMENT,QTY_COMPLETED,WORKORDER_RELEASED_DATE,\r\n"
	     //		+ "SERVICE_ENTRY_CREATE_DATE,SERVICE_ENTRY_UPDATED_DATE,PURCHASE_ORG_LEVEL,COMPANY_CODE,EIC_NUM,RECORD_CREATED_ON,RECORD_UPDATED_ON,\r\n"
	     //		+ "RECORD_PROCESSED,RECORD_STATUS,NATURE_OF_JOB)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,null,?,?,?,?,?,null,null,null,null,null,null)\r\n";
	     jdbcTemplate.update(sql,
	         workorder.getWorkOrderNumber(),
	         workorder.getItem(),
	         workorder.getLine(),
	         workorder.getLineNumber(),
	         workorder.getServiceCode(),
	         workorder.getShortText(),
	         workorder.getDeliveryCompletion(),
	         parseSqlDate(workorder.getItemChangedON()),
	         workorder.getVendorCode(),
	         workorder.getVendorName(),
	         workorder.getVendorAddress(),
	         workorder.getBlockedVendor(),
	         parseSqlDate(workorder.getWorkOrderValiditiyFrom()),
	         parseSqlDate(workorder.getWorkOrderValiditiyTo()),
	         workorder.getWorkOrderType(),
	         workorder.getPlantcode(),
	         workorder.getSectionCode(),
	         workorder.getDepartmentCode(),
	         workorder.getGLCode(),
	         workorder.getCostCenter(),
	         workorder.getNatureofJob(),
	         workorder.getRateUnit(),
	         workorder.getQuantity(),
	         workorder.getBaseUnitofMeasure(),
	         workorder.getWorkOrderReleased(),
	         workorder.getPMOrderNo(),
	         workorder.getWBSElement(),
	         parseSqlDate(workorder.getWorkOrderReleaseDate()),
	         parseSqlDate(workorder.getServiceEntryCreatedDate()),
	         parseSqlDate(workorder.getServiceEntryUpdatedDate()),
	         workorder.getPurchaseOrgLevel(),
	         workorder.getCompanycode()
	     );
	 }

	 @Override
	 public void callWorkorderProcessingSP() {
	     jdbcTemplate.execute("EXEC CMS_PROCESS_WORKORDERS_ON_REQ");
	 }
	 
	 @Override
	 public Integer getTradeIdByName(String name) {
		    if (name == null || name.trim().isEmpty()) return null;
		    String sql=getTradeIdByName();
		   // String sql = "SELECT TRADEID FROM CMSTRADE WHERE NAME = ?";
		    List<Integer> result = jdbcTemplate.queryForList(sql, Integer.class, name.trim());
		    return result.isEmpty() ? null : result.get(0);
		}

	 @Override
	 public Integer getGeneralMasterId(String gmName) {
		    if (gmName == null || gmName.trim().isEmpty()) return null;
		    String sql=getGeneralMasterId();
		    //String sql = "SELECT GMID FROM CMSGENERALMASTER WHERE GMNAME = ?";
		    List<Integer> result = jdbcTemplate.query(sql, new Object[]{gmName.trim()},
		        (rs, rowNum) -> rs.getInt("GMID"));
		    return result.isEmpty() ? null : result.get(0);
		}

	 @Override
	 public Integer getWCECId(String ECNumber, Integer unitId, Integer contractorId) {
		    if (ECNumber == null || ECNumber.trim().isEmpty()) return null;
		    String sql=getWCECId();
		    //String sql = "SELECT WCID FROM CMSCONTRACTOR_WC WHERE WC_CODE =? and UNITID=? and CONTRACTORID=?  and (LICENCE_TYPE='WC' or LICENCE_TYPE='ESIC')";
		    List<Integer> result = jdbcTemplate.queryForList(sql, Integer.class, ECNumber.trim(),unitId,contractorId);
		    return result.isEmpty() ? null : result.get(0);
		}

		@Override
		public Integer getUnitIdByName(String unitCode) {
		    if (unitCode == null || unitCode.trim().isEmpty()) return null;
		    String sql=getUnitIdByName();
		    //String sql = "SELECT UNITID FROM CMSPRINCIPALEMPLOYER WHERE CODE = ?";
		    List<Integer> result = jdbcTemplate.queryForList(sql, Integer.class, unitCode.trim());
		    return result.isEmpty() ? null : result.get(0);
		}

		@Override
		public Integer getContractorIdByName(String vendorCode) {
		    if (vendorCode == null || vendorCode.trim().isEmpty()) return null;
		    String sql=getContractorIdByName();
		    //String sql = "SELECT CONTRACTORID FROM CMSCONTRACTOR WHERE CODE = ?";
		    List<Integer> result = jdbcTemplate.queryForList(sql, Integer.class, vendorCode.trim());
		    return result.isEmpty() ? null : result.get(0);
		}

		@Override
		public Integer getSkillIdByName(String skill) {
		    if (skill == null || skill.trim().isEmpty()) return null;
		    String sql=getSkillIdByName();
		    //String sql = "SELECT SKILLID FROM CMSSKILL WHERE SKILLNM = ?";
		    List<Integer> result = jdbcTemplate.queryForList(sql, Integer.class, skill.trim());
		    return result.isEmpty() ? null : result.get(0);
		}

		@Override
		public Integer getLlNumber(String LLNumber, Integer unitId, Integer contractorId) {
		    if (LLNumber == null || LLNumber.trim().isEmpty()) return null;
		    String sql=getLlNumber();
		    //String sql = "SELECT WCID FROM CMSCONTRACTOR_WC WHERE WC_CODE =? and UNITID=? and CONTRACTORID=?  and LICENCE_TYPE='LL'";
		    List<Integer> result = jdbcTemplate.queryForList(sql, Integer.class, LLNumber.trim(),unitId,contractorId);
		    return result.isEmpty() ? null : result.get(0);
		}
		
		@Override
		public Integer geteicId(String department, Integer unitId, String ECnumber) {
		    if (department == null || department.trim().isEmpty() ||
		        unitId == null || ECnumber == null || ECnumber.trim().isEmpty()) return null;
		    String sql=geteicId();
			/*
			 * String sql = "SELECT DISTINCT mu.UserId FROM ORGLEVELENTRY ole " +
			 * "JOIN ORGLEVELDEF old ON old.ORGLEVELDEFID = ole.ORGLEVELDEFID " +
			 * "JOIN OLACCTSETMM oasm ON oasm.ORGLEVELENTRYID = ole.ORGLEVELENTRYID " +
			 * "JOIN ORGACCTSET oas ON oas.ORGACCTSETID = oasm.ORGACCTSETID " +
			 * "JOIN MASTERUSER mu ON mu.userAccount = oas.SHORTNM " +
			 * "JOIN UserRoleMapping urm ON urm.UserId = mu.UserId " +
			 * "JOIN CMSGENERALMASTER cgm ON cgm.GMID = urm.RoleId " +
			 * "LEFT JOIN CMSPRINCIPALEMPLOYER cpe ON cpe.CODE = ole.NAME AND old.NAME LIKE 'Principal%' "
			 * + "WHERE cgm.GMNAME IN ('EIC') " +
			 * "AND ((old.NAME LIKE 'Dep%' AND ole.NAME = ?) OR (cpe.UNITID = ?)) " +
			 * "AND mu.userAccount = ?";
			 */
		    List<Integer> result = jdbcTemplate.queryForList(sql, Integer.class,
		            department.trim(), unitId, ECnumber.trim());
		    return result.isEmpty() ? null : result.get(0);
		}

		@Override
		public Integer getWorkorderId(String workorderNumber,Integer unitId, Integer contractorId) {
		    if (workorderNumber == null || workorderNumber.trim().isEmpty()) return null;
		    String sql=getWorkorderId();
		    //String sql = "SELECT WORKORDERID FROM CMSWORKORDER WHERE NAME = ? and UNITID=? and CONTRACTORID=?";
		    List<Integer> result = jdbcTemplate.queryForList(sql, Integer.class, workorderNumber.trim(),unitId,contractorId);
		    return result.isEmpty() ? null : result.get(0);
		}
		
	@Override
	public int saveWorkmenBulkDraftUploadToStaging(WorkmenBulkUpload staging) {
		String sql=saveWorkmenBulkDraftUploadToStaging();
		/*
		 * String sql =
		 * "insert into CMSRequestItemDraftBulkUpload(TransactionID,GatePassStatus,AadharNumber,FirstName,LastName,DOB,Gender,RelativeName,IdMark,MobileNumber,\r\n"
		 * +
		 * "MaritalStatus,UnitId,ContractorId,WorkorderId,TradeId,SkillId,DepartmentId,AreaId,EicId,NatureOfJob,WcEsicNo,HazardousArea,AccessAreaId,\r\n"
		 * +
		 * "UanNumber,HealthCheckDate,BloodGroupId,Accommodation,AcademicId,Technical,IfscCode,AccountNumber,EmergencyContactNumber,EmergencyContactName,\r\n"
		 * +
		 * "WorkmenWageCategoryId,PfCap,AadharDocName,PoliceVerificationDocName,UpdatedDate,Address,DOJ,pfnumber,esicNumber,policeverificationDate,specialization,\r\n"
		 * +
		 * "LLNumber,pfapplicable,RecordProcessed,RecordStatus,organizationname,insurencetype,gatepasstypeid,Gatepassid,zoneid)values((SELECT ISNULL(MAX(TransactionID),0)+1 FROM CMSRequestItemDraftBulkUpload),1,?,?,?,?,?,?,?,?,?,?,\r\n"
		 * +
		 * "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,null,'Yes',null,null,getdate(),?,?,?,?,?,?,?,?,'N',null,?,?,1,null,?)\r\n";
		 */
				 jdbcTemplate.update(sql,
						 staging.getAadhaarNumber(),
						 staging.getFirstName(),
						 staging.getLastName(),
						 staging.getDateOfBirth(),
						 staging.getGender(),
						 staging.getRelationName(),
						 staging.getIdMark(),
						 staging.getMobileNumber(),
						 staging.getMaritalStatus(),
						 staging.getUnitCode(),
						 staging.getVendorCode(),
						 staging.getWorkorderNumber(),
						 staging.getTrade(),
						 staging.getSkill(),
						 staging.getDepartment(),
						 staging.getArea(),
						 staging.getEICNumber(),
						 staging.getNatureOfWork(),
						 staging.getECnumber(),
						 staging.getHazardousArea(),
						 staging.getAccessArea(),
						 staging.getUanNumber(),
						 staging.getHealthCheckDate(),
						 staging.getBloodGroup(),
						 staging.getAccommodation(),
						 staging.getAcademic(),
						 staging.getTechnical(),
						 staging.getBankName(),
						 staging.getAccountNumber(),
						 staging.getEmergencyNumber(),
						 staging.getEmergencyName(),
						 staging.getAddress(),
						 staging.getDoj(),
						 staging.getPfNumber(),
						 staging.getEsicNumber(),
						 staging.getPoliceVerificationDate(),
						 staging.getSpecializationName(),
						 staging.getLLnumber(),
						 staging.getPfApplicable(),
						 staging.getOrganizationName(),
						 staging.getInsuranceType(),
						 staging.getZone());
				 return jdbcTemplate.queryForObject("SELECT MAX(TransactionID) FROM CMSRequestItemDraftBulkUpload", Integer.class);
		 
	}

	@Override
	public void saveWorkmenBulkUploadToStaging(WorkmenBulkUpload staging) {
		String sql=saveWorkmenBulkUploadToStaging();
		//String sql = "insert into CMSRequestItemBulkUpload(TransactionID,GatePassStatus,AadharNumber,FirstName,LastName,DOB,Gender,RelativeName,IdMark,MobileNumber,\r\n"
		//		+ "MaritalStatus,UnitId,ContractorId,WorkorderId,TradeId,SkillId,DepartmentId,AreaId,EicId,NatureOfJob,WcEsicNo,HazardousArea,AccessAreaId,\r\n"
		//		+ "UanNumber,HealthCheckDate,BloodGroupId,Accommodation,AcademicId,Technical,IfscCode,AccountNumber,EmergencyContactNumber,EmergencyContactName,\r\n"
		//		+ "WorkmenWageCategoryId,PfCap,AadharDocName,PoliceVerificationDocName,UpdatedDate,Address,DOJ,pfnumber,esicNumber,policeverificationDate,specialization,\r\n"
		//		+ "LLNumber,pfapplicable,RecordProcessed,RecordStatus,organizationname,insurencetype,gatepasstypeid,Gatepassid,zoneid)values((SELECT ISNULL(MAX(TransactionID),0)+1 FROM CMSRequestItemBulkUpload),1,?,?,?,?,?,?,?,?,?,?,\r\n"
		//		+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,null,'Yes',null,null,getdate(),?,?,?,?,?,?,?,?,'N',null,?,?,1,null,?)\r\n";
				 jdbcTemplate.update(sql,
						 staging.getAadhaarNumber(),
						 staging.getFirstName(),
						 staging.getLastName(),
						 staging.getDateOfBirth(),
						 staging.getGender(),
						 staging.getRelationName(),
						 staging.getIdMark(),
						 staging.getMobileNumber(),
						 staging.getMaritalStatus(),
						 staging.getUnitCode(),
						 staging.getVendorCode(),
						 staging.getWorkorderNumber(),
						 staging.getTrade(),
						 staging.getSkill(),
						 staging.getDepartment(),
						 staging.getArea(),
						 staging.getEICNumber(),
						 staging.getNatureOfWork(),
						 staging.getECnumber(),
						 staging.getHazardousArea(),
						 staging.getAccessArea(),
						 staging.getUanNumber(),
						 staging.getHealthCheckDate(),
						 staging.getBloodGroup(),
						 staging.getAccommodation(),
						 staging.getAcademic(),
						 staging.getTechnical(),
						 staging.getBankName(),
						 staging.getAccountNumber(),
						 staging.getEmergencyNumber(),
						 staging.getEmergencyName(),
						 staging.getAddress(),
						 staging.getDoj(),
						 staging.getPfNumber(),
						 staging.getEsicNumber(),
						 staging.getPoliceVerificationDate(),
						 staging.getSpecializationName(),
						 staging.getLLnumber(),
						 staging.getPfApplicable(),
						 staging.getOrganizationName(),
						 staging.getInsuranceType(),
						 staging.getZone());
		 
	}

	@Override
	public void callWorkmenBulkUploadDraftProcessingSP() {
		jdbcTemplate.execute("EXEC KTC_ENTRYPASS_BULK_UPLOAD");
	 }

	@Override
	public WorkmenBulkUpload getByTransactionId(int transactionId) {
		String sql=getTransactionIdOfDraft();
	    //String sql = "select cribu.AadharNumber as aadhaarNumber,cribu.FirstName as firstName,cribu.LastName as lastName,cribu.DOB as dateOfBirth,cgmg.GMNAME as gender,\r\n"
	    //		+ "cribu.RelativeName as relationName,cribu.IdMark as idMark,cribu.MobileNumber as mobileNumber,   \r\n"
	    //		+ "cribu.MaritalStatus as maritalStatus,cpe.CODE as unitCode,cmsc.CODE as vendorCode,cmswo.NAME as workorderNumber,   \r\n"
	   // 		+ "cmst.NAME as trade,cmss.SKILLNM as skill,cmsgmdep.GMNAME as department,cmsgma.GMNAME as area,mu.userAccount  AS EICNumber,cribu.NatureOfJob as natureOfWork,   \r\n"
	  //  		+ "ccwc.WC_CODE as ECnumber,cribu.HazardousArea as hazardousArea,cmsgmaa.GMNAME as accessArea,   \r\n"
	  //  		+ "cribu.uanNumber,cribu.healthCheckDate,cmsgmb.GMNAME as bloodGroup,cribu.Accommodation as accommodation,cmsgmac.GMNAME as academic,cribu.Technical as technical,   \r\n"
	 //   		+ "cribu.IfscCode as bankName,cribu.AccountNumber as accountNumber,cribu.EmergencyContactNumber as emergencyNumber,cribu.EmergencyContactName  as emergencyName   \r\n"
	 //   		+ ",cribu.doj,cribu.pfNumber,cribu.esicNumber,cribu.policeVerificationDate,cribu.pfApplicable,cmsgmz.GMNAME as zone,cribu.Address as address from CMSRequestItemDraftBulkUpload cribu   \r\n"
	  //  		+ "left join CMSPRINCIPALEMPLOYER cpe on cpe.unitid=cribu.UnitId left join CMSCONTRACTOR cmsc on cmsc.CONTRACTORID=cribu.ContractorId left join CMSWORKORDER cmswo on cmswo.WORKORDERID=cribu.WorkorderId \r\n"
	  //  		+ "left join CMSTRADE cmst on cmst.TRADEID = cribu.TradeId   left join CMSSKILL cmss on cmss.skillid=cribu.SkillId \r\n"
	  //  		+ "left join CMSGENERALMASTER cmsgmdep on cmsgmdep.GMID=cribu.DepartmentId left join CMSGENERALMASTER cmsgma on cmsgma.GMID=cribu.AreaId \r\n"
	   // 		+ "left join CMSGENERALMASTER cmsgmaa on cmsgmaa.GMID=cribu.AccessAreaId left join CMSGENERALMASTER cmsgmb on cmsgmb.GMID=cribu.BloodGroupId \r\n"
	   // 		+ "left join CMSGENERALMASTER cmsgmac on cmsgmac.GMID=cribu.AcademicId left join CMSGENERALMASTER cmsgmz on cmsgmz.GMID=cribu.zoneid LEFT JOIN CMSGENERALMASTER cgmg ON cgmg.GMID = cribu.gender  \r\n"
	   // 		+ "LEFT JOIN MASTERUSER mu ON mu.UserId = cribu.EicId left join CMSCONTRACTOR_WC ccwc on ccwc.WCID=cribu.WcEsicNo where TransactionID=?";
	    return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(WorkmenBulkUpload.class), transactionId);
	}
	 
	 public String getMaxGatePassIdQuery() {
		 return QueryFileWatcher.getQuery("GET_NEXT_GATEPASSID_SEQ");
	 }
	 
	private String generateGatePassId() {
	    String gatePassId = null;
	    String maxTestReqId = null;	
	    DecimalFormat decimalFormat = new DecimalFormat("00");
	    try {
	    	String query = getMaxGatePassIdQuery();
	        SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
	        if(rs.next()){
				
				maxTestReqId=String.valueOf(rs.getInt(1));
				//log.info("maxTestReqId"+maxTestReqId);
				
			}
	        if(maxTestReqId==null  || maxTestReqId.equals("0")){
				
	        	gatePassId ="GP700001";
			}else{
							
				long incrMaxId = Long.parseLong(maxTestReqId)+1;
				gatePassId = "GP" + decimalFormat.format(incrMaxId);
			}
	    } catch (Exception e) {
	       // log.error("Error generating GatePassId", e);
	    }
	    return gatePassId;
	}
	
	public String getSaveContractWorkmen() {
		 return QueryFileWatcher.getQuery("SAVE_CONTRACT_WORKMEN"); 
	}
	public String getNextTransactionId() {
		String transactionId=null;
		try {
			 transactionId = jdbcTemplate.queryForObject("EXEC GetNextGatepassTransactionId", String.class);


	}catch(Exception e) {
		 System.out.println("Failed to fetch transaction ID: " + e.getMessage());
		e.printStackTrace();
	}
	    return transactionId;
	}

//	@Override
//	public void saveToGatePassMain(WorkmenBulkUpload data) {
//		String gatePassId = this.generateGatePassId();
//		
//		String sql = this.getSaveContractWorkmen();
//	   String transId = this.getNextTransactionId();
//	   
//	   Object[] parameters = this.prepareGatePassDraftParameters(transId, data); 
//
//       try {
//       	String query = this.getSaveContractWorkmen();
//           int result = jdbcTemplate.update(query, parameters);
//           if (result > 0) {
//              // log.info("GatePass drafted successfully for transId: " + transId);
//           } else {
//               //log.warn("Failed to draft GatePass for transId: " + transId);
//           }
//       } catch (Exception e) {
//          // log.error("Error saving GatePass for transId: " + transId, e);
//          // return null;
//       }
//       
//	    }
	
	@Override
	public void saveToGatePassMain(WorkmenBulkUpload data) {
		//String gatePassId = this.generateGatePassId();
		String sql=saveToGatePassMain();
		String transId = this.getNextTransactionId();
	    //String sql = "INSERT INTO  GATEPASSMAIN (TransactionId, GatePassId, GatePassTypeId, GatePassStatus, AadharNumber, FirstName, LastName, DOB, Gender, RelativeName, IdMark, MobileNumber,\r\n"
	    //		+ "MaritalStatus, UnitId, ContractorId, WorkorderId, TradeId, SkillId, DepartmentId, AreaId, EicId, NatureOfJob, WcEsicNo, HazardousArea  \r\n"
	    //		+ ",  AccessAreaId ,  UanNumber,  HealthCheckDate,  BloodGroupId,  Accommodation,  AcademicId ,  Technical ,  IfscCode,  AccountNumber,  EmergencyContactNumber  \r\n"
	    //		+ ",  EmergencyContactName, WorkmenWageCategoryId, BonusPayoutId, ZoneId, Basic, DA, HRA, WashingAllowance, OtherAllowance  \r\n"
	    //		+ ",  UniformAllowance,  PfCap,  AadharDocName ,  PhotoName ,  BankDocName ,  PoliceVerificationDocName,  IdProof2DocName,  MedicalDocName,  EducationDocName,  Form11DocName  \r\n"
	    //		+ ",  TrainingDocName,  OtherDocName,  UpdatedDate,  UpdatedBy,  WorkFlowType,  Comments,  Address,  DOJ,  DOT,  pfnumber,  esicNumber,  policeverificationDate  \r\n"
	    //		+ ",  OnboardingType ,  pfapplicable,LLNo )\r\n"
	    //		+ "VALUES ( ?,?,1,1,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,\r\n"
	    //		+ "?,?,?,?,?,?,?,?,?,?,?,null,null,?,'0.00','0.00','0.00','0.00','0.00','0.00','Yes',null,null,null,null,null,null,null,null,\r\n"
	    //		+ "null,null,getdate(),7,null,null,?,?,null,?,?,?,'regular',?,?)";
	    jdbcTemplate.update(sql, transId,
	    		data.getGatepassid()!=null? data.getGatepassid():" ",
	    		data.getAadhaarNumber()!=null? data.getAadhaarNumber():" ", 
	    		data.getFirstName()!=null? data.getFirstName():" ",
	    		data.getLastName()!=null? data.getLastName():" ",
	    		data.getDateOfBirth()!=null? data.getDateOfBirth():" ",
	    		data.getGender()!=null? data.getGender():" ",
	    		data.getRelationName()!=null? data.getRelationName():" ",
	    		data.getIdMark()!=null? data.getIdMark():" ",
	    		data.getMobileNumber()!=null? data.getMobileNumber():" ",
	            data.getMaritalStatus()!=null? data.getMaritalStatus():" ",
	            data.getUnitCode()!=null && !data.getUnitCode().trim().isEmpty()? data.getUnitCode():" ", 
	            data.getVendorCode()!=null&& !data.getVendorCode().trim().isEmpty()? data.getVendorCode():" ", 
	            data.getWorkorderNumber()!=null&& !data.getWorkorderNumber().trim().isEmpty()? data.getWorkorderNumber():" ",
	            data.getTrade()!=null&& !data.getTrade().trim().isEmpty()? data.getTrade():" ",
	            data.getSkill()!=null&& !data.getSkill().trim().isEmpty()? data.getSkill():" ",
	            data.getDepartment()!=null&& !data.getDepartment().trim().isEmpty()? data.getDepartment():" ",
	            data.getArea()!=null&& !data.getArea().trim().isEmpty()? data.getArea():" ",
	            data.getEICNumber()!=null&& !data.getEICNumber().trim().isEmpty()? data.getEICNumber():" ",
	            data.getNatureOfWork()!=null? data.getNatureOfWork():" ",
	            data.getECnumber()!=null&& !data.getECnumber().trim().isEmpty()? data.getECnumber():" ",
	            data.getHazardousArea()!=null? data.getHazardousArea():" ",
	            data.getAccessArea()!=null&& !data.getAccessArea().trim().isEmpty()? data.getAccessArea():" ",
	            data.getUanNumber()!=null? data.getUanNumber():" ",
	            data.getHealthCheckDate()!=null? data.getHealthCheckDate():" ",
	            data.getBloodGroup()!=null&& !data.getBloodGroup().trim().isEmpty()? data.getBloodGroup():" ",
	            data.getAccommodation()!=null? data.getAccommodation():" ",
	            data.getAcademic()!=null&& !data.getAcademic().trim().isEmpty()? data.getAcademic():" ",
	            data.getTechnical()!=null? data.getTechnical():" ",
	            data.getBankName()!=null? data.getBankName():" ",
	            data.getAccountNumber()!=null? data.getAccountNumber():" ",
	            data.getEmergencyNumber()!=null? data.getEmergencyNumber():" ",
	            data.getEmergencyName()!=null? data.getEmergencyName():" ",
	            data.getZone()!=null? data.getZone():" ",
	            data.getAddress()!=null? data.getAddress():" ",
	            data.getDoj()!=null? data.getDoj():" ",
	            data.getPfNumber()!=null? data.getPfNumber():" ",
	            data.getEsicNumber()!=null? data.getEsicNumber():" ",
	            data.getPoliceVerificationDate()!=null? data.getPoliceVerificationDate():" ",
	            data.getPfApplicable()!=null? data.getPfApplicable():" ",
	            data.getLLnumber()!=null? data.getLLnumber():" ");
	}

	@Override
	public void updateRecordStatusByTransactionId(int txnId, String combinedErrors) {
		String sql=updateRecordStatusByTransactionId();
	   // String sql = "UPDATE CMSRequestItemDraftBulkUpload SET RecordStatus = ? WHERE TransactionID = ?";
	    jdbcTemplate.update(sql, combinedErrors, txnId);
	}
	@Override
	public boolean isAadharNumberExists(String aadharNumber) {
		String sql1=isAadharNumberExistsInWorkmenDraft();
		String sql2=isAadharNumberExistsInGatepass();
		 //String sql1 = "SELECT COUNT(*) FROM CMSRequestItemDraftBulkUpload WHERE AadharNumber = ?";
		   // String sql2 = "SELECT COUNT(*) FROM GatePassMain WHERE AadharNumber = ?";

		    Integer count1 = jdbcTemplate.queryForObject(sql1, new Object[]{aadharNumber}, Integer.class);
		    Integer count2 = jdbcTemplate.queryForObject(sql2, new Object[]{aadharNumber}, Integer.class);

		    return (count1 != null && count1 > 0) || (count2 != null && count2 > 0);
	}
	//@Override
	//public void updateRecordProcessedByTransactionId(Integer txnId) {
	///    String sql = "UPDATE CMSRequestItemBulkUpload SET RecordProcessed = 'Y' WHERE TransactionID = ?";
	//    jdbcTemplate.update(sql, txnId);
	//}
	@Override
	public Integer getdepartmentIdByUnitId(Integer unitId, String department) {
		
		String sql=" select udm.departmentid from UnitDepartmentMapping udm\r\n"
				+ "join CMSGENERALMASTER cgm on  cgm.GMID = udm.departmentId where udm.principalEmployerId=? and cgm.GMNAME=?";
		 List<Integer> result = jdbcTemplate.queryForList(sql, Integer.class,unitId, department.trim());
		    return result.isEmpty() ? null : result.get(0);
		}
	@Override
	public Integer getAreaByDeptID(Integer unitId, Integer departmentId, String area) {
		String sql = "select udm.subDepartmentId from UnitDepartmentMapping udm\r\n"
				+ "join CMSGENERALMASTER cgm on  cgm.GMID = udm.subDepartmentId where udm.principalEmployerId=? and udm.departmentId=? and cgm.GMNAME=?";
	
		List<Integer> result = jdbcTemplate.queryForList(sql, Integer.class,unitId, departmentId,area.trim());
	    return result.isEmpty() ? null : result.get(0);
	}
	@Override
	public Integer getTradeIdByUnitId(Integer unitId, String trade) {
		String sql="select utm.TradeId from UnitTradeSkillMapping utm\r\n"
				+ "join CMSGENERALMASTER cgm on  cgm.GMID = utm.TradeId where utm.principalEmployerId=? and cgm.GMNAME=?";
		 List<Integer> result = jdbcTemplate.queryForList(sql, Integer.class,unitId, trade.trim());
		    return result.isEmpty() ? null : result.get(0);
		}
	@Override
	public Integer getSkillIdByTradeId(Integer unitId, Integer tradeId, String skill) {
		String sql="select utm.SkillId from UnitTradeSkillMapping utm\r\n"
				+ "join CMSGENERALMASTER cgm on  cgm.GMID = utm.SkillId where utm.principalEmployerId=? and TradeId=? and cgm.GMNAME=?";
		 List<Integer> result = jdbcTemplate.queryForList(sql, Integer.class,unitId, tradeId,skill.trim());
		    return result.isEmpty() ? null : result.get(0);
		}
	
	    @Override
	    public Integer getGeneralMasterId(String gmType, String gmName) {
	        try {
	            String sql = "SELECT GM.GMID  FROM CMSGENERALMASTER GM  JOIN CMSGMTYPE GT ON GT.GMTYPEID = GM.GMTYPEID  WHERE GT.GMTYPE = ? AND GM.GMNAME = ? and ISACTIVE=1";
	            return jdbcTemplate.queryForObject(sql, Integer.class, gmType, gmName);
	        } catch (EmptyResultDataAccessException e) {
	            return null;
	        }
	    }

	    @Override
	    public Integer insertGeneralMaster(String gmType, String gmName) {
	        // First get the GMTYPEID
	        String getTypeIdSql = "SELECT GMTYPEID FROM CMSGMTYPE WHERE GMTYPE = ?";
	        Integer gmTypeId = jdbcTemplate.queryForObject(getTypeIdSql, Integer.class, gmType);

	        String insertSql = "INSERT INTO CMSGENERALMASTER ( GMNAME,GMDESCRIPTION,GMTYPEID,UPDATEDBY) VALUES (?, ?, ?,'Admin')";
	        jdbcTemplate.update(insertSql, gmName,gmName,gmTypeId);

	        // Return newly inserted GMID
	        String fetchIdSql = " SELECT GMID FROM CMSGENERALMASTER \r\n"
	        		+ "	            WHERE GMNAME = ? AND GMTYPEID = ? and ISACTIVE=1 ORDER BY GMID DESC ";
	        return jdbcTemplate.queryForObject(fetchIdSql, Integer.class, gmName, gmTypeId);
	    }

	    @Override
	    public boolean existsUnitTradeSkillMapping(Integer unitId, Integer tradeId, Integer skillId) {
	        String sql = "SELECT COUNT(*) FROM UnitTradeSkillMapping  WHERE PrincipalEmployerId = ? AND TRADEID = ? AND SKILLID =?";
	        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, unitId, tradeId, skillId);
	        return count != null && count > 0;
	    }

	    @Override
	    public void insertUnitTradeSkillMapping(Integer unitId, Integer tradeId, Integer skillId) {
	        String sql = "INSERT INTO UnitTradeSkillMapping (PrincipalEmployerId, TRADEID, SKILLID) VALUES (?, ?, ?)";
	        jdbcTemplate.update(sql, unitId, tradeId, skillId);
	    }
	    
	    @Override
	    public void insertUnitDepartmentSubDepartmentMapping(Integer unitId, Integer departmentId, Integer subDepartmentId) {
	        String sql = "INSERT INTO UnitDepartmentMapping (principalEmployerId, departmentId, subDepartmentId) VALUES (?, ?, ?)";
	        jdbcTemplate.update(sql, unitId, departmentId, subDepartmentId);
	    }
	
	}



