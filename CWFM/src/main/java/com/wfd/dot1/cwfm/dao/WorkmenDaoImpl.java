package com.wfd.dot1.cwfm.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.ApproveRejectGatePassDto;
import com.wfd.dot1.cwfm.dto.ApproverStatusDTO;
import com.wfd.dot1.cwfm.dto.GatePassActionDto;
import com.wfd.dot1.cwfm.dto.GatePassListingDto;
import com.wfd.dot1.cwfm.dto.GatePassStatusLogDto;
import com.wfd.dot1.cwfm.enums.GatePassStatus;
import com.wfd.dot1.cwfm.enums.GatePassType;
import com.wfd.dot1.cwfm.enums.WorkFlowType;
import com.wfd.dot1.cwfm.pojo.ApprovalStatus;
import com.wfd.dot1.cwfm.pojo.ApproverInfo;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.GatePassMain;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.Skill;
import com.wfd.dot1.cwfm.pojo.Trade;
import com.wfd.dot1.cwfm.pojo.Workorder;
import com.wfd.dot1.cwfm.queries.WorkmenQueryBank;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

@Repository
public class WorkmenDaoImpl implements WorkmenDao{

	private static final Logger log = LoggerFactory.getLogger(WorkmenDaoImpl.class.getName());
	
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	
	 
	 public String getAllPes() {
		    return QueryFileWatcher.getQuery("GET_ALL_PES");
		}
	 
	 public String getAllContractorBasedOnPE() {
		    return QueryFileWatcher.getQuery("GET_ALL_CONTRACTOR_BY_PE");
		}
	 
	 
	 public String getAllSkillQuery() {
		    return QueryFileWatcher.getQuery("GET_ALL_SKILL");
		}
	 
	
	 public String getAllGeneralMaster() {
		    return QueryFileWatcher.getQuery("GET_ALL_CMSGENERALMASTER");
		}
	 
	 public String getAllTradesByPe() {
		    return QueryFileWatcher.getQuery("GET_ALL_TRADES_BY_PE");
		}
	 
	 public String getAllWoByPeAndCont() {
		    return QueryFileWatcher.getQuery("GET_ALL_WORKORDER_BY_PE_AND_CONT");
		}
	 
	 public String getAllWc() {
		    return QueryFileWatcher.getQuery("GET_ALL_WC");
		}
	 
	 public String getWorkflowType() {
		    return QueryFileWatcher.getQuery("GET_WORKFLOW_TYPE_BY_PE");
		}
	 
	 public String getDotType() {
		    return QueryFileWatcher.getQuery("GET_DOT_TYPE_BY_PE");
		}
	 
	 public String getAllGatePassForContractor() {
		    return QueryFileWatcher.getQuery("GET_ALL_GATE_PASS_FOR_CREATOR");
		}
	 
	 public String getAllGatePassForSquential() {
		    return QueryFileWatcher.getQuery("GET_ALL_GATE_PASS_FOR_SEQUENTIAL_APPROVER");
		}
	 
	 public String getAllGatePassForParallel() {
		    return QueryFileWatcher.getQuery("GET_ALL_GATE_PASS_FOR_PARALLEL_APPROVER");
		}
	 public String getApproverHierarchy() {
		    return QueryFileWatcher.getQuery("GET_APPROVER_INFO_BY_GPTID");
		}
	 public  String getApprovalStatusOfGatePass() {
		 return QueryFileWatcher.getQuery("GET_APPROVAL_STATUS_BY_TRNSID");
		}
	 public String getGatePassActionListingDetailsQuery() {
		 return QueryFileWatcher.getQuery("GET_ALL_GATE_PASS_ACTION_FOR_CREATOR");
	 }
	 public String getAllEicManagerQuery() {
		 return QueryFileWatcher.getQuery("GET_ALL_EIC");
	 }
	 
	 public String getLastApproverQuery() {
		 return QueryFileWatcher.getQuery("LAST_APPROVER");
	 }
	 
	 
	 public String getContrForAdmin() {
		 return QueryFileWatcher.getQuery("GET_ALL_CONTR_FOR_ADMIN");
	 }
	 
	 public String getTransactionIdExistsQuery() {
		 return  QueryFileWatcher.getQuery("TRANSACTION_ID_EXISTS");
	 }
	@Override
	public List<PrincipalEmployer> getAllPrincipalEmployer(String userAccount) {
		log.info("Entering into getAllPrincipalEmployer dao method "+userAccount);
		List<PrincipalEmployer> peList= new ArrayList<PrincipalEmployer>();
		String query=getAllPes();
		log.info("Query to getAllPrincipalEmployer "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query, userAccount);
		while(rs.next()) {
			PrincipalEmployer pe = new PrincipalEmployer();
			pe.setUnitId(rs.getInt("UNITID"));
			pe.setName(rs.getString("NAME"));
			peList.add(pe);
		}
		log.info("Exiting from getAllPrincipalEmployer dao method "+peList.size());
		return peList;
	}

	@Override
	public List<Contractor> getAllContractorBasedOnPE(String unitId,String userAccount) {
		log.info("Entering into getAllContractorBasedOnPE dao method "+unitId+" "+userAccount);
		List<Contractor> contList= new ArrayList<Contractor>();
		String query=getAllContractorBasedOnPE();
		log.info("Query to getAllContractorBasedOnPE "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,userAccount,unitId);
		while(rs.next()) {
			Contractor cont = new Contractor();
			cont.setContractorId(rs.getString("contractorid"));
			cont.setContractorName(rs.getString("contractorname"));
			cont.setUnitId(unitId);
			cont.setContractorCode(rs.getString("contractorcode"));
			cont.setContractorAddress(rs.getString("contractoraddress"));
			contList.add(cont);
		}
		log.info("Exiting from getAllContractorBasedOnPE dao method "+contList.size());
		return contList;
	}

	@Override
	public List<Workorder> getAllWorkordersBasedOnPEAndContractor(String unitId, String contractorId) {
		log.info("Entering into getAllWorkordersBasedOnPEAndContractor dao method "+unitId+" "+contractorId);
		List<Workorder> woList= new ArrayList<Workorder>();
		String query=getAllWoByPeAndCont();
		log.info("Query to getAllWorkordersBasedOnPEAndContractor "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,unitId, contractorId,contractorId);
		while(rs.next()) {
			Workorder wo = new Workorder();
			wo.setWorkorderId(rs.getString("WORKORDERID"));
			wo.setName(rs.getString("NAME"));
			woList.add(wo);
		}
		log.info("Exiting from getAllWorkordersBasedOnPEAndContractor dao method "+woList.size());
		return woList;
	}

	@Override
	public List<Trade> getAllTradesBasedOnPE(String unitId) {
		log.info("Entering into getAllTradesBasedOnPE dao method "+unitId);
		List<Trade> tradeList= new ArrayList<Trade>();
		String query = getAllTradesByPe();
		log.info("Query to getAllTradesBasedOnPE "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,unitId);
		while(rs.next()) {
			Trade trade = new Trade();
			trade.setTradeId(rs.getString("TRADEID"));
			trade.setTradeName(rs.getString("NAME"));
			tradeList.add(trade);
		}
		log.info("Exiting from getAllTradesBasedOnPE dao method "+tradeList.size());
		return tradeList;
	}

	@Override
	public List<Skill> getAllSkill() {
		log.info("Entering into getAllSkill dao method ");
		List<Skill> skillList= new ArrayList<Skill>();
		String query = getAllSkillQuery();
		log.info("Query to getAllSkill "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		while(rs.next()) {
			Skill skill = new Skill();
			skill.setSkillId(rs.getString("SKILLID"));
			skill.setSkillName(rs.getString("SKILLNM"));
			skill.setStatus(rs.getInt("ISACTIVE"));
			skillList.add(skill);
		}
		log.info("Exiting from getAllSkill dao method "+skillList.size());
		return skillList;
	}

	@Override
	public List<CmsGeneralMaster> getAllDepartmentAndSubDepartment(String userId) {
		log.info("Entering into getAllDepartmentAndSubDepartment dao method ");
		List<CmsGeneralMaster> gmList= new ArrayList<CmsGeneralMaster>();
		log.info("Query to getAllDepartmentAndSubDepartment "+WorkmenQueryBank.GET_ALL_DEPT_AND_SUBDEPT);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_ALL_DEPT_AND_SUBDEPT,userId);
		while(rs.next()) {
			CmsGeneralMaster gm = new CmsGeneralMaster();
			gm.setGmId(rs.getString("GMID"));
			gm.setGmName(rs.getString("GMNAME"));
			gm.setAuthorizationOn(rs.getString("AuthorizationOn"));
			gmList.add(gm);
		}
		log.info("Exiting from getAllDepartmentAndSubDepartment dao method "+gmList.size());
		return gmList;
	}

	@Override
	public List<MasterUser> getAllEicManager(String unitId,String deptId) {
		log.info("Entering into getAllEicManager dao method ");
		List<MasterUser> eicList= new ArrayList<MasterUser>();
		//log.info("Query to getAllEicManager "+WorkmenQueryBank.GET_ALL_EIC);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(this.getAllEicManagerQuery(),deptId,unitId);
		while(rs.next()) {
			MasterUser mu = new MasterUser();
			mu.setUserId(rs.getInt("UserId"));
			mu.setFullName(rs.getString("FullName"));
			eicList.add(mu);
		}
		log.info("Exiting from getAllEicManager dao method "+eicList.size());
		return eicList;
	}

	@Override
	public List<CmsContractorWC> getAllWCBasedOnPEAndCont(String unitId, String contractorId) {
		log.info("Entering into getAllWCBasedOnPEAndCont dao method ");
		List<CmsContractorWC> wcList= new ArrayList<CmsContractorWC>();
		String query = getAllWc();
		log.info("Query to getAllWCBasedOnPEAndCont "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,unitId,contractorId);
		while(rs.next()) {
			CmsContractorWC wc = new CmsContractorWC();
			wc.setWcId(rs.getString("WCID"));
			wc.setWcCode(rs.getString("WC_CODE"));
			wcList.add(wc);
		}
		log.info("Exiting from getAllWCBasedOnPEAndCont dao method "+wcList.size());
		return wcList;	
	}

	@Override
	public List<CmsGeneralMaster> getAllGenders() {
		log.info("Entering into getAllGenders dao method ");
		List<CmsGeneralMaster> gmList= new ArrayList<CmsGeneralMaster>();
		log.info("Query to getAllGenders "+WorkmenQueryBank.GET_ALL_GENDER);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_ALL_GENDER);
		while(rs.next()) {
			CmsGeneralMaster gm = new CmsGeneralMaster();
			gm.setGmId(rs.getString("GMID"));
			gm.setGmName(rs.getString("GMNAME"));
			gmList.add(gm);
		}
		log.info("Exiting from getAllGenders dao method "+gmList.size());
		return gmList;
	}
	
	

	@Override
	public List<CmsGeneralMaster> getAllGeneralMasters() {
		log.info("Entering into getAllGeneralMasters dao method ");
		List<CmsGeneralMaster> gmList= new ArrayList<CmsGeneralMaster>();
		String query = getAllGeneralMaster();
		log.info("Query to getAllGeneralMasters "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		while(rs.next()) {
			CmsGeneralMaster gm = new CmsGeneralMaster();
			gm.setGmId(rs.getString("GMID"));
			gm.setGmName(rs.getString("GMNAME"));
			gm.setGmType(rs.getString("GMTYPE"));
			gmList.add(gm);
		}
		log.info("Exiting from getAllGeneralMasters dao method "+gmList.size());
		return gmList;
	}

	
	public static Timestamp getTimeInTimeStamp() {
		Timestamp timestamp = null;
		timestamp = new Timestamp(System.currentTimeMillis());
		return timestamp;
	}
	
	@Override
	public String saveGatePass(GatePassMain gatePassMain) {
	    log.info("Entering into saveGatePass dao method");
	    String transId=gatePassMain.getTransactionId();
	    boolean status=false;
	  //check record already exists with same transactionid
        SqlRowSet rs = jdbcTemplate.queryForRowSet(this.getTransactionIdExistsQuery(),transId);
		if(rs.next()){
			
				status = true;
		}
		
	    if(status) {//already record drafted
	    	
	    	Object[] parameters = prepareGatePassParameters1(gatePassMain.getTransactionId(), gatePassMain); 
	    	 try {
		            int result = jdbcTemplate.update(WorkmenQueryBank.UPDATE_CONTRACT_WORKMEN, parameters);
		            if (result > 0) {
		            	transId=gatePassMain.getTransactionId();
		                log.info("GatePass saved successfully for transId: " + transId);
		            } else {
		                log.warn("Failed to save GatePass for transId: " + transId);
		            }
		        } catch (Exception e) {
		            log.error("Error saving GatePass for transId: " + transId, e);
		            return null;
		        }
	    }else {//saving directly without draft

	    
	        log.info("Gatepass generated for Aadhaar: " + gatePassMain.getAadhaarNumber() + " is: " + transId);

	        Object[] parameters = prepareGatePassParameters(transId, gatePassMain); 

	        try {
	            int result = jdbcTemplate.update(WorkmenQueryBank.SAVE_CONTRACT_WORKMEN, parameters);
	            if (result > 0) {
	                log.info("GatePass saved successfully for transId: " + transId);
	            } else {
	                log.warn("Failed to save GatePass for transId: " + transId);
	            }
	        } catch (Exception e) {
	            log.error("Error saving GatePass for transId: " + transId, e);
	            return null;
	        }
	    
	    }
	    return transId;
	}

	private String generateGatePassId() {
	    String gatePassId = null;
	    try {
	        SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_MAX_GATEPASS_ID);
	        if (rs.next()) {
	            gatePassId = rs.getString("newGatepassId");
	        }
	    } catch (Exception e) {
	        log.error("Error generating GatePassId", e);
	    }
	    return gatePassId;
	}

	private Object[] prepareGatePassParameters(String transId, GatePassMain gatePassMain) {
		
	    return new Object[]{
	    		transId," ",
	        GatePassType.CREATE.getStatus(),
	        gatePassMain.getGatePassStatus(),
	        gatePassMain.getAadhaarNumber(),
	        gatePassMain.getFirstName(),
	        gatePassMain.getLastName(),
	        gatePassMain.getDateOfBirth(),
	        gatePassMain.getGender(),
	        gatePassMain.getRelationName(),
	        gatePassMain.getIdMark(),
	        gatePassMain.getMobileNumber(),
	        gatePassMain.getMaritalStatus(),
	        gatePassMain.getPrincipalEmployer(),
	        gatePassMain.getContractor(),
	        gatePassMain.getWorkorder(),
	        gatePassMain.getTrade(),
	        gatePassMain.getSkill(),
	        gatePassMain.getDepartment(),
	        gatePassMain.getSubdepartment(),
	        gatePassMain.getEic(),
	        gatePassMain.getNatureOfJob(),
	        gatePassMain.getWcEsicNo(),
	        gatePassMain.getHazardousArea(),
	        gatePassMain.getAccessArea(),
	        gatePassMain.getUanNumber(),
	        gatePassMain.getHealthCheckDate(),
	        gatePassMain.getBloodGroup(),
	        gatePassMain.getAccommodation(),
	        gatePassMain.getAcademic(),
	        gatePassMain.getTechnical(),
	        gatePassMain.getIfscCode(),
	        gatePassMain.getAccountNumber(),
	        gatePassMain.getEmergencyName(),
	        gatePassMain.getEmergencyNumber(),
	        gatePassMain.getWageCategory(),
	        gatePassMain.getBonusPayout(),
	        gatePassMain.getPfCap(),
	        gatePassMain.getZone(),
	        gatePassMain.getBasic(),
	        gatePassMain.getDa(),
	        gatePassMain.getHra(),
	        gatePassMain.getWashingAllowance(),
	        gatePassMain.getOtherAllowance(),
	        gatePassMain.getUniformAllowance(),
	        gatePassMain.getAadharDocName(),gatePassMain.getPhotoName(),gatePassMain.getBankDocName(),
	        gatePassMain.getPoliceVerificationDocName(),gatePassMain.getIdProof2DocName(),gatePassMain.getMedicalDocName(),
	        gatePassMain.getEducationDocName(),gatePassMain.getForm11DocName(),gatePassMain.getTrainingDocName(),gatePassMain.getOtherDocName(),
	        gatePassMain.getWorkFlowType(),
	        gatePassMain.getComments()!=null?gatePassMain.getComments():"",
	        		gatePassMain.getAddress()!=null?gatePassMain.getAddress():"",
	        				gatePassMain.getDoj(),gatePassMain.getDot(),
	        gatePassMain.getUserId()
	    };
	}

	@Override
	public List<GatePassListingDto> getGatePassListingDetails(String unitId,String deptId,String userId,String gatePassTypeId) {
		log.info("Entering into getGatePassListingDetails dao method ");
		List<GatePassListingDto> listDto= new ArrayList<GatePassListingDto>();
		String query =getAllGatePassForContractor();
		log.info("Query to getGatePassListingDetails "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,userId,gatePassTypeId,deptId,unitId,userId,gatePassTypeId);
		while(rs.next()) {
			GatePassListingDto dto = new GatePassListingDto();
			dto.setTransactionId(rs.getString("TransactionId"));
			dto.setGatePassId((rs.getString("GatePassId")));
			dto.setFirstName(rs.getString("firstName"));
			dto.setLastName(rs.getString("lastName"));
			dto.setGender(rs.getString("GMNAME"));
			dto.setDateOfBirth(rs.getString("DOB"));
			dto.setAadhaarNumber(rs.getString("AadharNumber"));
			dto.setContractorName(rs.getString("ContractorName"));
			dto.setVendorCode(rs.getString("VendorCode"));
			dto.setUnitName(rs.getString("UnitName"));
			String gatePassType = rs.getString("GatePassTypeId");
			if(gatePassType.equals(GatePassType.CREATE.getStatus())) {
				dto.setGatePassType("Create");
			}else if(gatePassType.equals(GatePassType.BLOCK.getStatus())) {
				dto.setGatePassType("Block");
			}
			else if(gatePassType.equals(GatePassType.UNBLOCK.getStatus())) {
				dto.setGatePassType("Unblock");
			}else if(gatePassType.equals(GatePassType.BLACKLIST.getStatus())) {
				dto.setGatePassType("Blacklist");
			}else if(gatePassType.equals(GatePassType.DEBLACKLIST.getStatus())) {
				dto.setGatePassType("Deblacklist");
			}else if(gatePassType.equals(GatePassType.CANCEL.getStatus())) {
				dto.setGatePassType("Cancel");
			}else if(gatePassType.equals(GatePassType.LOSTORDAMAGE.getStatus())) {
				dto.setGatePassType("Lost/Damage");
			}
			String status =rs.getString("GatePassStatus");
			if(status.equals(GatePassStatus.APPROVALPENDING.getStatus())) {
				dto.setStatus("Approval Pending");
			}else if(status.equals(GatePassStatus.APPROVED.getStatus())) {
				dto.setStatus("Approved");
			}else if(status.equals(GatePassStatus.REJECTED.getStatus())) {
				dto.setStatus("Rejected");
			}else if(status.equals(GatePassStatus.DRAFT.getStatus())) {
				dto.setStatus("Draft");
			}
			listDto.add(dto);
		}
		log.info("Exiting from getGatePassListingDetails dao method "+listDto.size());
		return listDto;
	}

	@Override
	public List<GatePassListingDto> getGatePassListingForApprovers(String roleId,int workFlowType,String gatePassTypeId,String deptId,String unitId) {
		log.info("Entering into getGatePassListingForApprovers dao method ");
		List<GatePassListingDto> listDto= new ArrayList<GatePassListingDto>();
		SqlRowSet rs =null;
		String query=null;
		if(workFlowType == WorkFlowType.SEQUENTIAL.getWorkFlowTypeId()) {
			query=this.getAllGatePassForSquential();
			log.info("Query to getGatePassListingForApprovers "+query);
			
			 rs = jdbcTemplate.queryForRowSet(query,gatePassTypeId,gatePassTypeId,gatePassTypeId,roleId,deptId,unitId);
		}else {
			query=this.getAllGatePassForParallel();
			log.info("Query to getGatePassListingForApprovers "+query);
			 rs = jdbcTemplate.queryForRowSet(query,roleId,roleId,gatePassTypeId,deptId,unitId);
		}
		
		while(rs.next()) {
			GatePassListingDto dto = new GatePassListingDto();
			dto.setTransactionId(rs.getString("TransactionId"));
			dto.setGatePassId((rs.getString("GatePassId")));
			dto.setFirstName(rs.getString("firstName"));
			dto.setLastName(rs.getString("lastName"));
			dto.setGender(rs.getString("GMNAME"));
			dto.setDateOfBirth(rs.getString("DOB"));
			dto.setAadhaarNumber(rs.getString("AadharNumber"));
			dto.setContractorName(rs.getString("ContractorName"));
			dto.setVendorCode(rs.getString("VendorCode"));
			dto.setUnitName(rs.getString("UnitName"));
			String gatePassType = rs.getString("GatePassTypeId");
			if(gatePassType.equals(GatePassType.CREATE.getStatus())) {
				dto.setGatePassType("Create");
			}else if(gatePassType.equals(GatePassType.BLOCK.getStatus())) {
				dto.setGatePassType("Block");
			}
			else if(gatePassType.equals(GatePassType.UNBLOCK.getStatus())) {
				dto.setGatePassType("Unblock");
			}else if(gatePassType.equals(GatePassType.BLACKLIST.getStatus())) {
				dto.setGatePassType("Blacklist");
			}else if(gatePassType.equals(GatePassType.DEBLACKLIST.getStatus())) {
				dto.setGatePassType("Deblacklist");
			}else if(gatePassType.equals(GatePassType.CANCEL.getStatus())) {
				dto.setGatePassType("Cancel");
			}else if(gatePassType.equals(GatePassType.LOSTORDAMAGE.getStatus())) {
				dto.setGatePassType("Lost/Damage");
			}
			String status =rs.getString("GatePassStatus");
			if(status.equals(GatePassStatus.APPROVALPENDING.getStatus())) {
				dto.setStatus("Approval Pending");
			}else if(status.equals(GatePassStatus.APPROVED.getStatus())) {
				dto.setStatus("Approved");
			}else if(status.equals(GatePassStatus.REJECTED.getStatus())) {
				dto.setStatus("Rejected");
			}else if(status.equals(GatePassStatus.DRAFT.getStatus())) {
				dto.setStatus("Draft");
			}
			listDto.add(dto);
		}
		log.info("Exiting from getGatePassListingForApprovers dao method "+listDto.size());
		return listDto;
	}

	@Override
	public GatePassMain getIndividualContractWorkmenDetails(String transactionId) {
		log.info("Entering into getIndividualContractWorkmenDetails dao method ");
		GatePassMain dto = null;
		log.info("Query to getIndividualContractWorkmenDetails "+WorkmenQueryBank.GET_CONTRACT_WORKMEN_DETAILS);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_CONTRACT_WORKMEN_DETAILS,transactionId);
		if(rs.next()) {
			dto = new GatePassMain();
			dto.setTransactionId(rs.getString("TransactionId"));
			dto.setGatePassId(rs.getString("GatePassId"));
			dto.setUnitId(rs.getString("peId"));
			dto.setGatePassAction(rs.getString("GatePassTypeId"));
			dto.setGatePassStatus(rs.getString("GatePassStatus"));
			dto.setAadhaarNumber(rs.getString("AadharNumber"));
			dto.setFirstName(rs.getString("FirstName"));
			dto.setLastName(rs.getString("LastName"));
			dto.setDateOfBirth(rs.getString("DOB"));
			dto.setGender(rs.getString("Gender"));
			dto.setRelationName(rs.getString("RelativeName"));
			dto.setIdMark(rs.getString("IdMark"));
			dto.setMobileNumber(rs.getString("MobileNumber"));
			dto.setMaritalStatus(rs.getString("MaritalStatus"));
			dto.setPrincipalEmployer(rs.getString("UnitId"));
			dto.setContractor(rs.getString("ContractorId"));
			dto.setWorkorder(rs.getString("WorkorderId"));
			dto.setTrade(rs.getString("TradeId"));
			dto.setSkill(rs.getString("SkillId"));
			dto.setDepartment(rs.getString("DepartmentId"));
			dto.setSubdepartment(rs.getString("AreaId"));
			dto.setEic(rs.getString("EicId"));
			dto.setNatureOfJob(rs.getString("NatureOfJob"));
			dto.setWcEsicNo(rs.getString("WcEsicNo"));
			dto.setHazardousArea(rs.getString("HazardousArea"));
			dto.setAccessArea(rs.getString("AccessAreaId"));
			dto.setUanNumber(rs.getString("UanNumber"));
			dto.setHealthCheckDate(rs.getString("HealthCheckDate"));
			dto.setBloodGroup(rs.getString("BloodGroupId"));
			dto.setAccommodation(rs.getString("Accommodation"));
			dto.setAcademic(rs.getString("AcademicId"));
			dto.setTechnical(rs.getString("Technical"));
			dto.setIfscCode(rs.getString("IfscCode"));
			dto.setAccountNumber(rs.getString("AccountNumber"));
			dto.setEmergencyName(rs.getString("EmergencyContactName"));
			dto.setEmergencyNumber(rs.getString("EmergencyContactNumber"));
			dto.setWageCategory(rs.getString("WorkmenWageCategoryId"));
			dto.setBonusPayout(rs.getString("BonusPayoutId"));
			dto.setZone(rs.getString("ZoneId"));
			dto.setBasic(new BigDecimal(rs.getString("Basic")));
			dto.setDa(new BigDecimal(rs.getString("DA")));
			dto.setHra(new BigDecimal(rs.getString("HRA")));
			dto.setWashingAllowance(new BigDecimal(rs.getString("WashingAllowance")));
			dto.setOtherAllowance(new BigDecimal(rs.getString("OtherAllowance")));
			dto.setUniformAllowance(new BigDecimal(rs.getString("UniformAllowance")));
			dto.setPfCap(rs.getString("PfCap"));
			dto.setCreatedBy(rs.getString("UpdatedBy"));
			dto.setAadharDocName(rs.getString("AadharDocName"));
			dto.setPhotoName(rs.getString("PhotoName"));
			dto.setPoliceVerificationDocName(rs.getString("PoliceVerificationDocName"));
			dto.setBankDocName(rs.getString("BankDocName"));
			dto.setIdProof2DocName(rs.getString("IdProof2DocName"));
			dto.setMedicalDocName(rs.getString("MedicalDocName"));
			dto.setForm11DocName(rs.getString("Form11DocName"));
			dto.setOtherDocName(rs.getString("OtherDocName"));
			dto.setTrainingDocName(rs.getString("TrainingDocName"));
			dto.setEducationDocName(rs.getString("EducationDocName"));
			dto.setComments(rs.getString("Comments"));
			dto.setAddress(rs.getString("Address"));
			dto.setDoj(rs.getString("DOJ"));
			dto.setDot(rs.getString("DOT"));
		}
		log.info("Exiting from getIndividualContractWorkmenDetails dao method "+transactionId);
		return dto;
	}
	
	@Override
	public List<CmsGeneralMaster> getAllGeneralMastersForGatePass(GatePassMain gpm) {
		log.info("Entering into getAllGeneralMastersForGatePass dao method ");
		List<CmsGeneralMaster> gmList= new ArrayList<CmsGeneralMaster>();
		Object[] obj = new Object[] {gpm.getGender()!=null?gpm.getGender():' ',
				gpm.getBloodGroup()!=null?gpm.getBloodGroup():' ',
						gpm.getAccessArea()!=null?gpm.getAccessArea():' ',
								gpm.getAcademic()!=null?gpm.getAcademic():' ',
										gpm.getZone()!=null?gpm.getZone():' ',
												gpm.getWageCategory()!=null?gpm.getWageCategory():' ',gpm.getBonusPayout()!=null?gpm.getBonusPayout():' ',
														gpm.getDepartment()!=null?gpm.getDepartment():' ',gpm.getSubdepartment()!=null?gpm.getSubdepartment():' '};
		log.info("Query to getAllGeneralMastersForGatePass "+WorkmenQueryBank.GET_ALL_CMSGENERALMASTER_FOR_GATE_PASS);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_ALL_CMSGENERALMASTER_FOR_GATE_PASS,obj);
		while(rs.next()) {
			CmsGeneralMaster gm = new CmsGeneralMaster();
			gm.setGmId(rs.getString("GMID"));
			gm.setGmName(rs.getString("GMNAME"));
			gm.setGmType(rs.getString("GMTYPE"));
			gmList.add(gm);
		}
		log.info("Exiting from getAllGeneralMastersForGatePass dao method "+gmList.size());
		return gmList;
	}

	@Override
	public int getWorkFlowTYpe(String principalEmployer) {
		log.info("Entering into getWorkFlowTYpe dao method ");
		int workflowTypeId = 0;
		String query = getWorkflowType();
		log.info("Query to getWorkFlowTYpe "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,principalEmployer);
		if(rs.next()) {
			workflowTypeId = rs.getInt("WorkflowType");
		}
		log.info("Exiting from getWorkFlowTYpe dao method "+principalEmployer);
		return workflowTypeId;
	}

	@Override
	public List<MasterUser> getApproversForGatePass(String createdBy) {
		log.info("Entering into getApproversForGatePass dao method ");
		List<MasterUser> approverList= new ArrayList<MasterUser>();
		log.info("Query to getApproversForGatePass "+WorkmenQueryBank.GET_APPROVERS_FOR_GATE_PASS);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_APPROVERS_FOR_GATE_PASS,createdBy);
		while(rs.next()) {
			MasterUser mu = new MasterUser();
			mu.setUserId(rs.getInt("UserId"));
			mu.setFullName(rs.getString("FullName"));
			mu.setRoleName(rs.getString("AuthorizationOn"));
			approverList.add(mu);
		}
		log.info("Exiting from getApproversForGatePass dao method "+approverList.size());
		return approverList;
	}
	
	@Override
	public  void saveGatePassApprover(final String gatePassId, final List<MasterUser> approverList,final String createdBy){
		log.info("Entering into getApproversForGatePass dao method ",gatePassId);
		String SAVE_GP_APPROVER = WorkmenQueryBank.SAVE_GATE_PASS_APPROVER;
		int[] save = jdbcTemplate.batchUpdate(SAVE_GP_APPROVER, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, gatePassId);
				ps.setString(2, approverList.get(i).getRoleName());
				ps.setInt(3, approverList.get(i).getUserId());
				ps.setInt(4, approverList.get(i).getIndex());
				ps.setInt(5,Integer.parseInt(approverList.get(i).getStatus()));
				ps.setString(6,createdBy);
				
			}

			public int getBatchSize() {
				return approverList.size();
			}
		});
	}

	@Override
	public String approveRejectGatePass(ApproveRejectGatePassDto dto) {
		 String result = null; 


		        Object[] parameters = new Object[] {dto.getTransactionId(),dto.getGatePassId(),dto.getApproverId(),dto.getApproverRole(),Integer.parseInt(dto.getStatus()),dto.getComments(),Integer.parseInt(dto.getGatePassType()),dto.getRoleId()}; 

		        try {
		            int status = jdbcTemplate.update(WorkmenQueryBank.SAVE_GATEPASS_APPROVAL_STATUS, parameters);
		            if (status > 0) {
		                log.info("GatePass approved/rejected successfully for GatePassId: " + dto.getTransactionId());
		                result="GatePass approved/rejected successfully";
		            } else {
		                log.warn("Failed to approve/reject GatePass for transactionId: " +  dto.getTransactionId());
		            }
		        } catch (Exception e) {
		            log.error("Error approving/rejecting GatePass for transactionId: " +  dto.getTransactionId(), e);
		            return null;
		        }
		    

		    return result;
	}

	@Override
	public int getWorkFlowTypeForApprovers(String businessType) {
		log.info("Entering into getWorkFlowTYpe dao method ");
		int workflowTypeId = 0;
		log.info("Query to getWorkFlowTYpe "+WorkmenQueryBank.GET_WORKFLOW_TYPE_BY_BT);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_WORKFLOW_TYPE_BY_BT,businessType);
		if(rs.next()) {
			workflowTypeId = rs.getInt("WorkflowType");
		}
		log.info("Exiting from getWorkFlowTYpe dao method "+businessType);
		return workflowTypeId;
	}

	@Override
	public synchronized boolean updateGatePassMainStatus(String gatePassId, String status) {
		boolean res=false;
		Object[] object=new Object[]{status,gatePassId};
		int i = jdbcTemplate.update(WorkmenQueryBank.UPDATE_GATEPASSMAIN_STATUS,object);
		if(i>0){
			res=true;
		}
		return res;
	}
	
	@Override
	public synchronized boolean updateGatePassMainStatusAndType(String gatePassId, String status,String gatePassType) {
		boolean res=false;
		Object[] object=new Object[]{status,gatePassType,gatePassId};
		int i = jdbcTemplate.update(WorkmenQueryBank.UPDATE_GATEPASSMAIN_STATUS_TYPE,object);
		if(i>0){
			res=true;
		}
		return res;
	}

	@Override
	public boolean isLastApprover(String roleName, String  gatePassTypeId) {
		boolean status=false;
		
		SqlRowSet rs = jdbcTemplate.queryForRowSet(this.getLastApproverQuery(),gatePassTypeId);
		if(rs.next()){
			if(roleName.equals(rs.getString("Role_Name")))
				status = true;
		}
		log.info("exit from isLastApprover method = "+status);
		return status; 
	}

	@Override
	public String gatePassAction(GatePassActionDto dto) {
		 String result = null; 


	        Object[] parameters = new Object[] {dto.getGatePassType(),dto.getGatePassStatus(),dto.getCreatedBy(),dto.getComments(),dto.getGatePassId()}; 
 
	        try {
	            int status = jdbcTemplate.update(WorkmenQueryBank.UPDATE_GATE_PASS_ACTION, parameters);
	            if (status > 0) {
	                log.info("GatePass action created successfully for GatePassId: " + dto.getGatePassId());
	                result="GatePass action created successfully";
	            } else {
	                log.warn("Failed to create GatePass action for GatePassId: " + dto.getGatePassId());
	            }
	        } catch (Exception e) {
	            log.error("Error creating GatePass action for GatePassId: " + dto.getGatePassId(), e);
	            return null;
	        }
	    

	    return result;
	}

	@Override
	public void saveGatePassStatusLog(GatePassStatusLogDto dto) {
		log.info("Entered into saveGatePassStatusLog for gatePassId: "+dto.getGatePassId() );

        Object[] parameters = new Object[] {dto.getTransactionId(),dto.getGatePassId(),dto.getGatePassType(),dto.getStatus(),dto.getComments(),dto.getUpdatedBy()};

        try {
            int result = jdbcTemplate.update(WorkmenQueryBank.SAVE_GATEPASS_STATUSLOG,parameters );
            if (result > 0) {
                log.info("GatePass status log saved successfully for GatePassId: " + dto.getGatePassId());
            } else {
                log.warn("Failed to save GatePass status log for GatePassId: " + dto.getGatePassId());
            }
        } catch (Exception e) {
            log.error("Error saving GatePass status log for GatePassId: " + dto.getGatePassId(), e);
        }
		log.info("Exiting from saveGatePassStatusLog for gatePassId: "+dto.getGatePassId() );
	}

	@Override
	public List<GatePassListingDto> getGatePassActionListingDetails(String unitId,String deptId,String userId, String gatePassTypeId,String previousGatePassAction) {
		log.info("Entering into getGatePassListingDetails dao method ");
		List<GatePassListingDto> listDto= new ArrayList<GatePassListingDto>();
		String query = getGatePassActionListingDetailsQuery();
		log.info("Query to getGatePassListingDetails "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,userId,deptId,unitId,previousGatePassAction,GatePassStatus.APPROVED.getStatus(),gatePassTypeId);
		while(rs.next()) {
			GatePassListingDto dto = new GatePassListingDto();
			dto.setTransactionId(rs.getString("TransactionId"));
			dto.setGatePassId((rs.getString("GatePassId")));
			dto.setFirstName(rs.getString("firstName"));
			dto.setLastName(rs.getString("lastName"));
			dto.setGender(rs.getString("GMNAME"));
			dto.setDateOfBirth(rs.getString("DOB"));
			dto.setAadhaarNumber(rs.getString("AadharNumber"));
			dto.setContractorName(rs.getString("ContractorName"));
			dto.setVendorCode(rs.getString("VendorCode"));
			dto.setUnitName(rs.getString("UnitName"));
			String gatePassType = rs.getString("GatePassTypeId");
			if(gatePassType.equals(GatePassType.CREATE.getStatus())) {
				dto.setGatePassType("Create");
			}else if(gatePassType.equals(GatePassType.BLOCK.getStatus())) {
				dto.setGatePassType("Block");
			}
			else if(gatePassType.equals(GatePassType.UNBLOCK.getStatus())) {
				dto.setGatePassType("Unblock");
			}else if(gatePassType.equals(GatePassType.BLACKLIST.getStatus())) {
				dto.setGatePassType("Blacklist");
			}else if(gatePassType.equals(GatePassType.DEBLACKLIST.getStatus())) {
				dto.setGatePassType("Deblacklist");
			}else if(gatePassType.equals(GatePassType.CANCEL.getStatus())) {
				dto.setGatePassType("Cancel");
			}else if(gatePassType.equals(GatePassType.LOSTORDAMAGE.getStatus())) {
				dto.setGatePassType("Lost/Damage");
			}
			String status =rs.getString("GatePassStatus");
			if(status.equals(GatePassStatus.APPROVALPENDING.getStatus())) {
				dto.setStatus("Approval Pending");
			}else if(status.equals(GatePassStatus.APPROVED.getStatus())) {
				dto.setStatus("Approved");
			}else if(status.equals(GatePassStatus.REJECTED.getStatus())) {
				dto.setStatus("Rejected");
			}else if(status.equals(GatePassStatus.DRAFT.getStatus())) {
				dto.setStatus("Draft");
			}
			listDto.add(dto);
		}
		log.info("Exiting from getGatePassListingDetails dao method "+listDto.size());
		return listDto;
	}
	
	@Override
	public List<MasterUser> getApproversForGatePassAction(String createdBy,String gatepassAction) {
		log.info("Entering into getApproversForGatePass dao method ");
		List<MasterUser> approverList= new ArrayList<MasterUser>();
		log.info("Query to getApproversForGatePass "+WorkmenQueryBank.GET_APPROVERS_FOR_GATE_PASS_ACTION);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_APPROVERS_FOR_GATE_PASS_ACTION,createdBy,gatepassAction);
		while(rs.next()) {
			MasterUser mu = new MasterUser();
			mu.setUserId(rs.getInt("UserId"));
			mu.setFullName(rs.getString("FullName"));
			mu.setRoleName(rs.getString("RoleName"));
			approverList.add(mu);
		}
		log.info("Exiting from getApproversForGatePass dao method "+approverList.size());
		return approverList;
	}
	
	@Override
	public List<GatePassListingDto> getWorkmenDetailBasedOnId(String gatePassId) {
		log.info("Entering into getWorkmenDetailBasedOnId dao method ");
		List<GatePassListingDto> listDto= new ArrayList<GatePassListingDto>();
		log.info("Query to getWorkmenDetailBasedOnId "+WorkmenQueryBank.GET_GATE_PASS_BY_ID);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_GATE_PASS_BY_ID,gatePassId);
		while(rs.next()) {
			GatePassListingDto dto = new GatePassListingDto();
			dto.setTransactionId(rs.getString("TransactionId"));
			dto.setGatePassId((rs.getString("GatePassId")));
			dto.setFirstName(rs.getString("firstName"));
			dto.setLastName(rs.getString("lastName"));
			dto.setGender(rs.getString("GMNAME"));
			dto.setDateOfBirth(rs.getString("DOB"));
			dto.setAadhaarNumber(rs.getString("AadharNumber"));
			dto.setContractorName(rs.getString("ContractorName"));
			dto.setVendorCode(rs.getString("VendorCode"));
			dto.setUnitName(rs.getString("UnitName"));
			String gatePassType = rs.getString("GatePassTypeId");
			if(gatePassType.equals(GatePassType.CREATE.getStatus())) {
				dto.setGatePassType("Create");
			}else if(gatePassType.equals(GatePassType.BLOCK.getStatus())) {
				dto.setGatePassType("Block");
			}
			else if(gatePassType.equals(GatePassType.UNBLOCK.getStatus())) {
				dto.setGatePassType("Unblock");
			}else if(gatePassType.equals(GatePassType.BLACKLIST.getStatus())) {
				dto.setGatePassType("Blacklist");
			}else if(gatePassType.equals(GatePassType.DEBLACKLIST.getStatus())) {
				dto.setGatePassType("Deblacklist");
			}else if(gatePassType.equals(GatePassType.CANCEL.getStatus())) {
				dto.setGatePassType("Cancel");
			}else if(gatePassType.equals(GatePassType.LOSTORDAMAGE.getStatus())) {
				dto.setGatePassType("Lost/Damage");
			}
			String status =rs.getString("GatePassStatus");
			if(status.equals(GatePassStatus.APPROVALPENDING.getStatus())) {
				dto.setStatus("Approval Pending");
			}else if(status.equals(GatePassStatus.APPROVED.getStatus())) {
				dto.setStatus("Approved");
			}else if(status.equals(GatePassStatus.REJECTED.getStatus())) {
				dto.setStatus("Rejected");
			}else if(status.equals(GatePassStatus.DRAFT.getStatus())) {
				dto.setStatus("Draft");
			}
			listDto.add(dto);
		}
		log.info("Exiting from getWorkmenDetailBasedOnId dao method "+listDto.size());
		return listDto;
	}
	

	@Override
	public int getDOTTYpe(String principalEmployer) {
		log.info("Entering into getDOTTYpe dao method ");
		int workflowTypeId = 0;
		String query = getDotType();
		log.info("Query to getDOTTYpe "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,principalEmployer);
		if(rs.next()) {
			workflowTypeId = rs.getInt("WorkflowType");
		}
		log.info("Exiting from getDOTTYpe dao method "+principalEmployer);
		return workflowTypeId;
	}
	@Override
	 public Map<String, LocalDate> getValidityDates(String workOrderId, String wcId) {
		 Map<String, LocalDate> validityDates = new HashMap<>();
		 SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_VALIDITY_OF_WO_WC,workOrderId,wcId);
		 while(rs.next()) {
			 LocalDate validTill = rs.getDate("validTill").toLocalDate();
             String source = rs.getString("source");
             validityDates.put(source, validTill);
			}
		 return validityDates;
	 }

	@Override
	public List<ApproverStatusDTO> getApprovalDetails(String transactionId) {
		 // Fetch approvers from GATEPASSAPPROVERINFO
        List<ApproverInfo> approverList = this.getApproversByGatePassId(GatePassType.CREATE.getStatus());

        // Fetch approval statuses from GATEPASSAPPROVALSTATUS
        List<ApprovalStatus> approvalStatuses = this.getApprovalStatusByGatePassId(transactionId);

        // Map to hold approval status by User ID
        Map<String, ApprovalStatus> statusMap = approvalStatuses.stream()
            .collect(Collectors.toMap(ApprovalStatus::getUserRole, status -> status));

        // Prepare the DTO list
        List<ApproverStatusDTO> approverStatusList = new ArrayList<>();
        for (ApproverInfo approver : approverList) {
            ApproverStatusDTO dto = new ApproverStatusDTO();
           
            dto.setUserRole(approver.getUserRole().toUpperCase());

            if (statusMap.containsKey(approver.getUserRole())) {
                ApprovalStatus status = statusMap.get(approver.getUserRole());
                dto.setStatus(status.getStatus() == 4 ? "Approved" : "Rejected");
                dto.setComments(status.getComments());
                dto.setTimestamp(status.getLastUpdatedDate());
            } else {
                dto.setStatus("Pending");
                dto.setComments("");
                dto.setTimestamp(null);
            }

            approverStatusList.add(dto);
        }

        return approverStatusList;
	}

	private List<ApproverInfo> getApproversByGatePassId(String gatePassTypeId) {
		 SqlRowSet rs = jdbcTemplate.queryForRowSet(getApproverHierarchy(),gatePassTypeId);
		 List<ApproverInfo> list = new ArrayList<ApproverInfo>();
		 while(rs.next()) {
			 ApproverInfo info=new ApproverInfo();
			 info.setGatePassApproverInfoId(rs.getString("hierarchy_id"));
			// info.setGatePassId(rs.getString("GatePassId"));
			 info.setIndex(rs.getInt("Index"));
			 info.setUserRole(rs.getString("Role_Name"));
			 //info.setStatus(rs.getInt("Status"));
			// info.setCreatedBy(rs.getString("CreatedBy"));
			// info.setCreatedDate(rs.getString("CreatedDate"));
			 list.add(info);
		 }
		 return list;
	}

	private List<ApprovalStatus> getApprovalStatusByGatePassId(String transactionId) {
		 SqlRowSet rs = jdbcTemplate.queryForRowSet(this.getApprovalStatusOfGatePass(),transactionId);
		 List<ApprovalStatus> list = new ArrayList<ApprovalStatus>();
		 while(rs.next()) {
			 ApprovalStatus info=new ApprovalStatus();
			 info.setGatePassApprovalStatusId(rs.getString("GatePassApprovalStatusId"));
			 info.setTransactionId(rs.getString("TransactionId"));
			 info.setGatePassId(rs.getString("GatePassId"));
			 info.setGatePassTypeId(rs.getInt("GatePassTypeId"));
			 info.setUserRole(rs.getString("UserRole"));
			 info.setUserId(rs.getString("UserId"));
			 info.setStatus(rs.getInt("Status"));
			 info.setComments(rs.getString("Comments"));
			 info.setLastUpdatedDate(rs.getString("LastUpdatedDate"));
			 list.add(info);
		 }
		 return list;
	}

	@Override
	public List<Contractor> getAllContractorForAdmin(String unitId) {
		log.info("Entering into getAllContractorForAdmin dao method "+unitId);
		List<Contractor> contList= new ArrayList<Contractor>();
		String query=this.getContrForAdmin();
		log.info("Query to getAllContractorForAdmin "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,unitId);
		while(rs.next()) {
			Contractor cont = new Contractor();
			cont.setContractorId(rs.getString("contractorid"));
			cont.setContractorName(rs.getString("contractorname"));
			cont.setUnitId(unitId);
			cont.setContractorCode(rs.getString("contractorcode"));
			cont.setContractorAddress(rs.getString("contractoraddress"));
			contList.add(cont);
		}
		log.info("Exiting from getAllContractorForAdmin dao method "+contList.size());
		return contList;
	}

	@Override
	public String draftGatePass(GatePassMain gatePassMain) {
	    log.info("Entering into draftGatePass dao method");

	    String transId = gatePassMain.getTransactionId();
	    		//generateTransationId(); 
	   boolean status=false;
	    if (transId != null) {
	        log.info("transId generated for Aadhaar: " + gatePassMain.getAadhaarNumber() + " is: " + transId);
	        //check record already exists with same transactionid
	        SqlRowSet rs = jdbcTemplate.queryForRowSet(this.getTransactionIdExistsQuery(),transId);
			if(rs.next()){
				
					status = true;
			}
			if(status) {
				Object[] parameters = prepareGatePassParameters1(gatePassMain.getTransactionId(), gatePassMain); 
		    	 try {
			            int result = jdbcTemplate.update(WorkmenQueryBank.UPDATE_CONTRACT_WORKMEN, parameters);
			            if (result > 0) {
			            	transId=gatePassMain.getTransactionId();
			                log.info("GatePass saved successfully for transId: " + transId);
			            } else {
			                log.warn("Failed to save GatePass for transId: " + transId);
			            }
			        } catch (Exception e) {
			            log.error("Error saving GatePass for transId: " + transId, e);
			            return null;
			        }
				
			}else {
				 Object[] parameters = prepareGatePassDraftParameters(transId, gatePassMain); 

			        try {
			            int result = jdbcTemplate.update(WorkmenQueryBank.SAVE_CONTRACT_WORKMEN, parameters);
			            if (result > 0) {
			                log.info("GatePass drafted successfully for transId: " + transId);
			            } else {
			                log.warn("Failed to draft GatePass for transId: " + transId);
			            }
			        } catch (Exception e) {
			            log.error("Error saving GatePass for transId: " + transId, e);
			            return null;
			        }
			}

	       
	    } else {
	        log.error("Failed to generate transId for Aadhaar: " + gatePassMain.getAadhaarNumber());
	    }

	    return transId;
	}
	
	@Override
	public String generateTransationId() {
	    String transactionId = null;
	    try {
	        SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_MAX_TRANSACTION_ID);
	        if (rs.next()) {
	        	transactionId = rs.getString("newTransactionId");
	        }
	    } catch (Exception e) {
	        log.error("Error generating transactionId", e);
	    }
	    return transactionId;
	}
private Object[] prepareGatePassDraftParameters(String transId, GatePassMain gatePassMain) {
		
	    return new Object[]{
	    		transId,
	    		gatePassMain.getGatePassId(),
	        GatePassType.CREATE.getStatus(),
	       gatePassMain.getGatePassStatus(),
	        gatePassMain.getAadhaarNumber()!=null? gatePassMain.getAadhaarNumber():" ",
	        gatePassMain.getFirstName()!=null? gatePassMain.getFirstName():" ",
	        gatePassMain.getLastName()!=null?  gatePassMain.getLastName():" ",
	        gatePassMain.getDateOfBirth()!=null?gatePassMain.getDateOfBirth():" ",
	        gatePassMain.getGender()!=null?gatePassMain.getGender():" ",
	        gatePassMain.getRelationName()!=null?gatePassMain.getRelationName():" ",
	        gatePassMain.getIdMark()!=null?gatePassMain.getIdMark(): " ",
	        gatePassMain.getMobileNumber()!=null?gatePassMain.getMobileNumber() : " ",
	        gatePassMain.getMaritalStatus()!=null?gatePassMain.getMaritalStatus():" ",
	        gatePassMain.getPrincipalEmployer()!=null?gatePassMain.getPrincipalEmployer():" ",
	        gatePassMain.getContractor()!=null? gatePassMain.getContractor():" ",
	        gatePassMain.getWorkorder()!=null?gatePassMain.getWorkorder():" ",
	        gatePassMain.getTrade()!=null?gatePassMain.getTrade(): " ",
	        gatePassMain.getSkill()!=null?gatePassMain.getSkill():" ",
	        gatePassMain.getDepartment()!=null?gatePassMain.getDepartment():" ",
	        gatePassMain.getSubdepartment()!=null? gatePassMain.getSubdepartment():" ",
	        gatePassMain.getEic()!=null?gatePassMain.getEic():" ",
	        gatePassMain.getNatureOfJob()!=null?gatePassMain.getNatureOfJob():" ",
	        gatePassMain.getWcEsicNo()!=null?gatePassMain.getWcEsicNo():" ",
	        gatePassMain.getHazardousArea()!=null?gatePassMain.getHazardousArea():" ",
	        gatePassMain.getAccessArea()!=null? gatePassMain.getAccessArea():" ",
	        gatePassMain.getUanNumber()!=null?gatePassMain.getUanNumber():" ",
	        gatePassMain.getHealthCheckDate()!=null?gatePassMain.getHealthCheckDate():" ",
	        gatePassMain.getBloodGroup()!=null?gatePassMain.getBloodGroup():" ",
	        gatePassMain.getAccommodation()!=null? gatePassMain.getAccommodation():" ",
	        gatePassMain.getAcademic()!=null?gatePassMain.getAcademic():" ",
	        gatePassMain.getTechnical()!=null?gatePassMain.getTechnical():" ",
	        gatePassMain.getIfscCode()!=null?gatePassMain.getIfscCode():" ",
	        gatePassMain.getAccountNumber()!=null?  gatePassMain.getAccountNumber():" ",
	        gatePassMain.getEmergencyName()!=null?gatePassMain.getEmergencyName():" ",
	        gatePassMain.getEmergencyNumber()!=null?gatePassMain.getEmergencyNumber():" ",
	        gatePassMain.getWageCategory()!=null?gatePassMain.getWageCategory():" ",
	        gatePassMain.getBonusPayout()!=null?gatePassMain.getBonusPayout():" ",
	        gatePassMain.getPfCap()!=null?gatePassMain.getPfCap():" ",
	        gatePassMain.getZone()!=null?gatePassMain.getZone():" ",
	        gatePassMain.getBasic()!=null?gatePassMain.getBasic():" ",
	        gatePassMain.getDa()!=null?gatePassMain.getDa():" ",
	        gatePassMain.getHra()!=null?gatePassMain.getHra():" ",
	        gatePassMain.getWashingAllowance()!=null?gatePassMain.getWashingAllowance():" ",
	        gatePassMain.getOtherAllowance()!=null?gatePassMain.getOtherAllowance():" ",
	        gatePassMain.getUniformAllowance()!=null?gatePassMain.getUniformAllowance():" ",
	        gatePassMain.getAadharDocName()!=null? gatePassMain.getAadharDocName():" ",
	        gatePassMain.getPhotoName()!=null?gatePassMain.getPhotoName():" ",
	        gatePassMain.getBankDocName()!=null?gatePassMain.getBankDocName():" ",
	        gatePassMain.getPoliceVerificationDocName()!=null?gatePassMain.getPoliceVerificationDocName():" ",
	        gatePassMain.getIdProof2DocName()!=null?gatePassMain.getIdProof2DocName():" ",
	        gatePassMain.getMedicalDocName()!=null? gatePassMain.getMedicalDocName():" ",
	        gatePassMain.getEducationDocName()!=null?gatePassMain.getEducationDocName():" ",
	        gatePassMain.getForm11DocName()!=null?gatePassMain.getForm11DocName():" ",
	        gatePassMain.getTrainingDocName()!=null?gatePassMain.getTrainingDocName():" ",
	        gatePassMain.getOtherDocName()!=null?gatePassMain.getOtherDocName():" ",
	        gatePassMain.getWorkFlowType()==0?gatePassMain.getWorkFlowType():0,
	        gatePassMain.getComments()!=null?gatePassMain.getComments():"",
	        gatePassMain.getAddress()!=null?gatePassMain.getAddress():"",
	        gatePassMain.getDoj()!=null?gatePassMain.getDoj():" ",
	        gatePassMain.getDot()!=null?gatePassMain.getDot():" ",
	        gatePassMain.getUserId()
	    };
	}


@Override
public GatePassMain getIndividualContractWorkmenDraftDetails(String transactionId) {
	log.info("Entering into getIndividualContractWorkmenDraftDetails dao method ");
	GatePassMain dto = null;
	log.info("Query to getIndividualContractWorkmenDraftDetails "+WorkmenQueryBank.GET_CONTRACT_WORKMEN_DRAFT_DETAILS);
	SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_CONTRACT_WORKMEN_DRAFT_DETAILS,transactionId);
	if(rs.next()) {
		dto = new GatePassMain();
		dto.setTransactionId(rs.getString("TransactionId"));
		dto.setGatePassId(rs.getString("GatePassId"));
		dto.setUnitId(rs.getString("peId"));
		dto.setGatePassAction(rs.getString("GatePassTypeId"));
		dto.setGatePassStatus(rs.getString("GatePassStatus"));
		dto.setAadhaarNumber(rs.getString("AadharNumber"));
		dto.setFirstName(rs.getString("FirstName"));
		dto.setLastName(rs.getString("LastName"));
		dto.setDateOfBirth(rs.getString("DOB"));
		dto.setGender(rs.getString("Gender"));
		dto.setRelationName(rs.getString("RelativeName"));
		dto.setIdMark(rs.getString("IdMark"));
		dto.setMobileNumber(rs.getString("MobileNumber"));
		dto.setMaritalStatus(rs.getString("MaritalStatus"));
		dto.setPrincipalEmployer(rs.getString("UnitId"));
		dto.setContractor(rs.getString("ContractorId"));
		dto.setWorkorder(rs.getString("WorkorderId"));
		dto.setTrade(rs.getString("TradeId"));
		dto.setSkill(rs.getString("SkillId"));
		dto.setDepartment(rs.getString("DepartmentId"));
		dto.setSubdepartment(rs.getString("AreaId"));
		dto.setEic(rs.getString("EicId"));
		dto.setNatureOfJob(rs.getString("NatureOfJob"));
		dto.setWcEsicNo(rs.getString("WcEsicNo"));
		dto.setHazardousArea(rs.getString("HazardousArea"));
		dto.setAccessArea(rs.getString("AccessAreaId"));
		dto.setUanNumber(rs.getString("UanNumber"));
		dto.setHealthCheckDate(rs.getString("HealthCheckDate"));
		dto.setBloodGroup(rs.getString("BloodGroupId"));
		dto.setAccommodation(rs.getString("Accommodation"));
		dto.setAcademic(rs.getString("AcademicId"));
		dto.setTechnical(rs.getString("Technical"));
		dto.setIfscCode(rs.getString("IfscCode"));
		dto.setAccountNumber(rs.getString("AccountNumber"));
		dto.setEmergencyName(rs.getString("EmergencyContactName"));
		dto.setEmergencyNumber(rs.getString("EmergencyContactNumber"));
		dto.setWageCategory(rs.getString("WorkmenWageCategoryId"));
		dto.setBonusPayout(rs.getString("BonusPayoutId"));
		dto.setZone(rs.getString("ZoneId"));
		
		String basicValue = rs.getString("Basic");

		if (basicValue == null || basicValue.trim().isEmpty()) { 
		    dto.setBasic(BigDecimal.ZERO);
		} else {
		    try {
		        dto.setBasic(new BigDecimal(basicValue.trim())); // Trim whitespace before conversion
		    } catch (NumberFormatException e) {
		        log.error("Invalid number format for 'Basic': " + basicValue, e);
		        dto.setBasic(BigDecimal.ZERO); // Default to zero if the value is not a valid number
		    }
		}
		
		String DA = rs.getString("DA");

		if (DA == null || DA.trim().isEmpty()) { 
		    dto.setDa(BigDecimal.ZERO);
		} else {
		    try {
		        dto.setDa(new BigDecimal(DA.trim())); // Trim whitespace before conversion
		    } catch (NumberFormatException e) {
		        log.error("Invalid number format for 'Basic': " + basicValue, e);
		        dto.setDa(BigDecimal.ZERO); // Default to zero if the value is not a valid number
		    }
		}
		
		String hra = rs.getString("HRA");

		if (hra == null || hra.trim().isEmpty()) { 
		    dto.setHra(BigDecimal.ZERO);
		} else {
		    try {
		        dto.setHra(new BigDecimal(hra.trim())); // Trim whitespace before conversion
		    } catch (NumberFormatException e) {
		        log.error("Invalid number format for 'Basic': " + basicValue, e);
		        dto.setHra(BigDecimal.ZERO); // Default to zero if the value is not a valid number
		    }
		}
		
		String WashingAllowance = rs.getString("WashingAllowance");

		if (WashingAllowance == null || WashingAllowance.trim().isEmpty()) { 
		    dto.setWashingAllowance(BigDecimal.ZERO);
		} else {
		    try {
		        dto.setWashingAllowance(new BigDecimal(WashingAllowance.trim())); // Trim whitespace before conversion
		    } catch (NumberFormatException e) {
		        log.error("Invalid number format for 'Basic': " + basicValue, e);
		        dto.setWashingAllowance(BigDecimal.ZERO); // Default to zero if the value is not a valid number
		    }
		}
		
		String OtherAllowance = rs.getString("OtherAllowance");

		if (OtherAllowance == null || OtherAllowance.trim().isEmpty()) { 
		    dto.setOtherAllowance(BigDecimal.ZERO);
		} else {
		    try {
		        dto.setOtherAllowance(new BigDecimal(OtherAllowance.trim())); // Trim whitespace before conversion
		    } catch (NumberFormatException e) {
		        log.error("Invalid number format for 'Basic': " + basicValue, e);
		        dto.setOtherAllowance(BigDecimal.ZERO); // Default to zero if the value is not a valid number
		    }
		}
		
		String UniformAllowance = rs.getString("UniformAllowance");

		if (UniformAllowance == null || UniformAllowance.trim().isEmpty()) { 
		    dto.setUniformAllowance(BigDecimal.ZERO);
		} else {
		    try {
		        dto.setUniformAllowance(new BigDecimal(UniformAllowance.trim())); // Trim whitespace before conversion
		    } catch (NumberFormatException e) {
		        log.error("Invalid number format for 'Basic': " + basicValue, e);
		        dto.setUniformAllowance(BigDecimal.ZERO); // Default to zero if the value is not a valid number
		    }
		}
//		dto.setBasic(rs.getString("Basic")!=null  || "".equals(rs.getString("Basic"))?new BigDecimal(rs.getString("Basic")):new BigDecimal("0"));
//		dto.setDa(rs.getString("DA")!=null || "".equals(rs.getString("DA"))?new BigDecimal(rs.getString("DA")):new BigDecimal("0"));
//		dto.setHra(rs.getString("HRA")!=null || "".equals(rs.getString("HRA"))?new BigDecimal(rs.getString("HRA")):new BigDecimal("0"));
//		dto.setWashingAllowance(rs.getString("WashingAllowance")!=null || "".equals(rs.getString("WashingAllowance"))?new BigDecimal(rs.getString("WashingAllowance")):new BigDecimal("0"));
//		dto.setOtherAllowance(rs.getString("OtherAllowance")!=null || "".equals(rs.getString("OtherAllowance"))?new BigDecimal(rs.getString("OtherAllowance")):new BigDecimal("0"));
//		dto.setUniformAllowance(rs.getString("UniformAllowance")!=null || "".equals(rs.getString("UniformAllowance"))?new BigDecimal(rs.getString("UniformAllowance")):new BigDecimal("0"));
		dto.setPfCap(rs.getString("PfCap"));
		dto.setCreatedBy(rs.getString("UpdatedBy"));
		dto.setAadharDocName(rs.getString("AadharDocName"));
		dto.setPhotoName(rs.getString("PhotoName"));
		dto.setPoliceVerificationDocName(rs.getString("PoliceVerificationDocName"));
		dto.setBankDocName(rs.getString("BankDocName"));
		dto.setIdProof2DocName(rs.getString("IdProof2DocName"));
		dto.setMedicalDocName(rs.getString("MedicalDocName"));
		dto.setForm11DocName(rs.getString("Form11DocName"));
		dto.setOtherDocName(rs.getString("OtherDocName"));
		dto.setTrainingDocName(rs.getString("TrainingDocName"));
		dto.setEducationDocName(rs.getString("EducationDocName"));
		dto.setComments(rs.getString("Comments"));
		dto.setAddress(rs.getString("Address"));
		dto.setDoj(rs.getString("DOJ"));
		dto.setDot(rs.getString("DOT"));
	}
	log.info("Exiting from getIndividualContractWorkmenDraftDetails dao method "+transactionId);
	return dto;
}

private Object[] prepareGatePassParameters1(String transId, GatePassMain gatePassMain) {
	
    return new Object[]{
    		
        GatePassType.CREATE.getStatus(),
        gatePassMain.getGatePassStatus(),
        gatePassMain.getAadhaarNumber(),
        gatePassMain.getFirstName(),
        gatePassMain.getLastName(),
        gatePassMain.getDateOfBirth(),
        gatePassMain.getGender(),
        gatePassMain.getRelationName(),
        gatePassMain.getIdMark(),
        gatePassMain.getMobileNumber(),
        gatePassMain.getMaritalStatus(),
        gatePassMain.getPrincipalEmployer(),
        gatePassMain.getContractor(),
        gatePassMain.getWorkorder(),
        gatePassMain.getTrade(),
        gatePassMain.getSkill(),
        gatePassMain.getDepartment(),
        gatePassMain.getSubdepartment(),
        gatePassMain.getEic(),
        gatePassMain.getNatureOfJob(),
        gatePassMain.getWcEsicNo(),
        gatePassMain.getHazardousArea(),
        gatePassMain.getAccessArea(),
        gatePassMain.getUanNumber(),
        gatePassMain.getHealthCheckDate(),
        gatePassMain.getBloodGroup(),
        gatePassMain.getAccommodation(),
        gatePassMain.getAcademic(),
        gatePassMain.getTechnical(),
        gatePassMain.getIfscCode(),
        gatePassMain.getAccountNumber(),
        gatePassMain.getEmergencyName(),
        gatePassMain.getEmergencyNumber(),
        gatePassMain.getWageCategory(),
        gatePassMain.getBonusPayout(),
        gatePassMain.getPfCap(),
        gatePassMain.getZone(),
        gatePassMain.getBasic(),
        gatePassMain.getDa(),
        gatePassMain.getHra(),
        gatePassMain.getWashingAllowance(),
        gatePassMain.getOtherAllowance(),
        gatePassMain.getUniformAllowance(),
        gatePassMain.getAadharDocName(),gatePassMain.getPhotoName(),gatePassMain.getBankDocName(),
        gatePassMain.getPoliceVerificationDocName(),gatePassMain.getIdProof2DocName(),gatePassMain.getMedicalDocName(),
        gatePassMain.getEducationDocName(),gatePassMain.getForm11DocName(),gatePassMain.getTrainingDocName(),gatePassMain.getOtherDocName(),
       0,
        gatePassMain.getComments()!=null?gatePassMain.getComments():"",
        		gatePassMain.getAddress()!=null?gatePassMain.getAddress():"",
        				gatePassMain.getDoj(),gatePassMain.getDot(),
        gatePassMain.getUserId(),transId
    };
}

@Override
public synchronized String updateGatePassIdByTransactionId(String transactionId) {
	String gatePassId = this.generateGatePassId();
	if(null !=gatePassId) {
		Object[] object=new Object[]{gatePassId,transactionId};
		int i = jdbcTemplate.update(WorkmenQueryBank.UPDATE_GATEPASSID,object);
		if(i>0){
			return gatePassId;
		}
		
	}
	return null;
}
@Override
public GatePassMain getIndividualContractWorkmenDetailsByTransId(String transactionId) {
	log.info("Entering into getIndividualContractWorkmenDetails dao method ");
	GatePassMain dto = null;
	log.info("Query to getIndividualContractWorkmenDetails "+WorkmenQueryBank.GET_CONTRACT_WORKMEN_DETAILS_BY_TRANSID);
	SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_CONTRACT_WORKMEN_DETAILS_BY_TRANSID,transactionId);
	if(rs.next()) {
		dto = new GatePassMain();
		dto.setTransactionId(rs.getString("TransactionId"));
		dto.setGatePassId(rs.getString("GatePassId"));
		dto.setUnitId(rs.getString("peId"));
		dto.setGatePassAction(rs.getString("GatePassTypeId"));
		dto.setGatePassStatus(rs.getString("GatePassStatus"));
		dto.setAadhaarNumber(rs.getString("AadharNumber"));
		dto.setFirstName(rs.getString("FirstName"));
		dto.setLastName(rs.getString("LastName"));
		dto.setDateOfBirth(rs.getString("DOB"));
		dto.setGender(rs.getString("Gender"));
		dto.setRelationName(rs.getString("RelativeName"));
		dto.setIdMark(rs.getString("IdMark"));
		dto.setMobileNumber(rs.getString("MobileNumber"));
		dto.setMaritalStatus(rs.getString("MaritalStatus"));
		dto.setPrincipalEmployer(rs.getString("UnitId"));
		dto.setContractor(rs.getString("ContractorId"));
		dto.setWorkorder(rs.getString("WorkorderId"));
		dto.setTrade(rs.getString("TradeId"));
		dto.setSkill(rs.getString("SkillId"));
		dto.setDepartment(rs.getString("DepartmentId"));
		dto.setSubdepartment(rs.getString("AreaId"));
		dto.setEic(rs.getString("EicId"));
		dto.setNatureOfJob(rs.getString("NatureOfJob"));
		dto.setWcEsicNo(rs.getString("WcEsicNo"));
		dto.setHazardousArea(rs.getString("HazardousArea"));
		dto.setAccessArea(rs.getString("AccessAreaId"));
		dto.setUanNumber(rs.getString("UanNumber"));
		dto.setHealthCheckDate(rs.getString("HealthCheckDate"));
		dto.setBloodGroup(rs.getString("BloodGroupId"));
		dto.setAccommodation(rs.getString("Accommodation"));
		dto.setAcademic(rs.getString("AcademicId"));
		dto.setTechnical(rs.getString("Technical"));
		dto.setIfscCode(rs.getString("IfscCode"));
		dto.setAccountNumber(rs.getString("AccountNumber"));
		dto.setEmergencyName(rs.getString("EmergencyContactName"));
		dto.setEmergencyNumber(rs.getString("EmergencyContactNumber"));
		dto.setWageCategory(rs.getString("WorkmenWageCategoryId"));
		dto.setBonusPayout(rs.getString("BonusPayoutId"));
		dto.setZone(rs.getString("ZoneId"));
		dto.setBasic(new BigDecimal(rs.getString("Basic")));
		dto.setDa(new BigDecimal(rs.getString("DA")));
		dto.setHra(new BigDecimal(rs.getString("HRA")));
		dto.setWashingAllowance(new BigDecimal(rs.getString("WashingAllowance")));
		dto.setOtherAllowance(new BigDecimal(rs.getString("OtherAllowance")));
		dto.setUniformAllowance(new BigDecimal(rs.getString("UniformAllowance")));
		dto.setPfCap(rs.getString("PfCap"));
		dto.setCreatedBy(rs.getString("UpdatedBy"));
		dto.setAadharDocName(rs.getString("AadharDocName"));
		dto.setPhotoName(rs.getString("PhotoName"));
		dto.setPoliceVerificationDocName(rs.getString("PoliceVerificationDocName"));
		dto.setBankDocName(rs.getString("BankDocName"));
		dto.setIdProof2DocName(rs.getString("IdProof2DocName"));
		dto.setMedicalDocName(rs.getString("MedicalDocName"));
		dto.setForm11DocName(rs.getString("Form11DocName"));
		dto.setOtherDocName(rs.getString("OtherDocName"));
		dto.setTrainingDocName(rs.getString("TrainingDocName"));
		dto.setEducationDocName(rs.getString("EducationDocName"));
		dto.setComments(rs.getString("Comments"));
		dto.setAddress(rs.getString("Address"));
		dto.setDoj(rs.getString("DOJ"));
		dto.setDot(rs.getString("DOT"));
	}
	log.info("Exiting from getIndividualContractWorkmenDetails dao method "+transactionId);
	return dto;
}

}
