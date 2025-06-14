package com.wfd.dot1.cwfm.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.wfd.dot1.cwfm.pojo.ContractWorkmenExportDto;
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
	 
	 public String getWorkflowTypeNew() {
		    return QueryFileWatcher.getQuery("GET_WORKFLOW_TYPE_BY_PE_ACTIONID");
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
	 
	 public String getMaxGatePassIdQuery() {
		 return QueryFileWatcher.getQuery("GET_NEXT_GATEPASSID_SEQ");
	 }
	 
	 public String getAllRenewForContractor() {
		 return QueryFileWatcher.getQuery("GET_ALL_RENEW_FOR_CREATOR");
	 }
	 
	 public String getAllGatePassActionForSquential() {
		    return QueryFileWatcher.getQuery("GET_ALL_GATE_PASS_ACTION_FOR_SEQUENTIAL_APPROVER");
		}
	 
	 public String getAllGatePassActionForParallel() {
		    return QueryFileWatcher.getQuery("GET_ALL_GATE_PASS_ACTION_FOR_PARALLEL_APPROVER");
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
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,unitId, contractorId);
		while(rs.next()) {
			Workorder wo = new Workorder();
			wo.setWorkorderId(rs.getString("WORKORDERID"));
			wo.setName(rs.getString("NAME"));
			wo.setValidFrom(rs.getString("VALIDFROM"));
			wo.setValidTo(rs.getString("VALIDDT"));
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
	public String getUpdateContractWorkmen() {
		 return QueryFileWatcher.getQuery("UPDATE_CONTRACT_WORKMEN"); 
	}
	
	public String getSaveContractWorkmen() {
		 return QueryFileWatcher.getQuery("SAVE_CONTRACT_WORKMEN"); 
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
	    		 String query = getUpdateContractWorkmen();
		            int result = jdbcTemplate.update(query, parameters);
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
	        	String query = getSaveContractWorkmen();
	            int result = jdbcTemplate.update(query, parameters);
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
	    String maxTestReqId = null;	
	    DecimalFormat decimalFormat = new DecimalFormat("00");
	    try {
	    	String query = getMaxGatePassIdQuery();
	        SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
	        if(rs.next()){
				
				maxTestReqId=String.valueOf(rs.getInt(1));
				log.info("maxTestReqId"+maxTestReqId);
				
			}
	        if(maxTestReqId==null  || maxTestReqId.equals("0")){
				
	        	gatePassId ="GP700001";
			}else{
							
				long incrMaxId = Long.parseLong(maxTestReqId)+1;
				gatePassId = "GP" + decimalFormat.format(incrMaxId);
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
			
			 rs = jdbcTemplate.queryForRowSet(query,deptId,unitId,roleId,gatePassTypeId);
		}else {
			query=this.getAllGatePassForParallel();
			log.info("Query to getGatePassListingForApprovers "+query);
			 rs = jdbcTemplate.queryForRowSet(query,roleId,gatePassTypeId,roleId,gatePassTypeId,deptId,unitId);
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

	public String getContractWorkmenDetails() {
		 return QueryFileWatcher.getQuery("GET_CONTRACT_WORKMEN_DETAILS");
	}
	@Override
	public GatePassMain getIndividualContractWorkmenDetails(String transactionId) {
		log.info("Entering into getIndividualContractWorkmenDetails dao method ");
		GatePassMain dto = null;
		String query = getContractWorkmenDetails();
		log.info("Query to getIndividualContractWorkmenDetails "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,transactionId);
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
	
	public String getAllCmsGeneralMasterForGatePass() {
		return QueryFileWatcher.getQuery("GET_ALL_CMSGENERALMASTER_FOR_GATE_PASS");
	}
	@Override
	public List<CmsGeneralMaster> getAllGeneralMastersForGatePass(GatePassMain gpm) {
		log.info("Entering into getAllGeneralMastersForGatePass dao method ");
		List<CmsGeneralMaster> gmList= new ArrayList<CmsGeneralMaster>();
		String query = getAllCmsGeneralMasterForGatePass();
		Object[] obj = new Object[] {gpm.getGender()!=null?gpm.getGender():' ',
				gpm.getBloodGroup()!=null?gpm.getBloodGroup():' ',
						gpm.getAccessArea()!=null?gpm.getAccessArea():' ',
								gpm.getAcademic()!=null?gpm.getAcademic():' ',
										gpm.getZone()!=null?gpm.getZone():' ',
												gpm.getWageCategory()!=null?gpm.getWageCategory():' ',gpm.getBonusPayout()!=null?gpm.getBonusPayout():' ',
														gpm.getDepartment()!=null?gpm.getDepartment():' ',gpm.getSubdepartment()!=null?gpm.getSubdepartment():' '};
		log.info("Query to getAllGeneralMastersForGatePass "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,obj);
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
	public List<MasterUser> getApproversForGatePass(String createdBy) {//not required anymore
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
	
	@Override//not required anymore
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

	public String getSaveGatepassApprovalStatus() {
		return QueryFileWatcher.getQuery("SAVE_GATEPASS_APPROVAL_STATUS");
	}
	@Override
	public String approveRejectGatePass(ApproveRejectGatePassDto dto) {
		 String result = null; 


		        Object[] parameters = new Object[] {dto.getTransactionId(),dto.getGatePassId(),dto.getApproverId(),dto.getApproverRole(),Integer.parseInt(dto.getStatus()),dto.getComments(),Integer.parseInt(dto.getGatePassType()),dto.getRoleId()}; 

		        try {
		        	String query = getSaveGatepassApprovalStatus();
		            int status = jdbcTemplate.update(query, parameters);
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

	public String getWorkflowTYpeByBT() {
		return QueryFileWatcher.getQuery("GET_WORKFLOW_TYPE_BY_BT");
	}
	@Override
	public int getWorkFlowTypeForApprovers(String businessType) {
		log.info("Entering into getWorkFlowTYpe dao method ");
		int workflowTypeId = 0;
		String query = getWorkflowTYpeByBT();
		log.info("Query to getWorkFlowTYpe "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,businessType);
		if(rs.next()) {
			workflowTypeId = rs.getInt("WorkflowType");
		}
		log.info("Exiting from getWorkFlowTYpe dao method "+businessType);
		return workflowTypeId;
	}

	public String getUpdateGatepassMainStatus() {
		return QueryFileWatcher.getQuery("UPDATE_GATEPASSMAIN_STATUS");
	}
	@Override
	public synchronized boolean updateGatePassMainStatus(String gatePassId, String status) {
		boolean res=false;
		Object[] object=new Object[]{status,gatePassId};
		String query= getUpdateGatepassMainStatus();
		int i = jdbcTemplate.update(query,object);
		if(i>0){
			res=true;
		}
		return res;
	}
	
	public String getUpdateGatepassMainStatusByTransactionId() {
		return QueryFileWatcher.getQuery("UPDATE_GATEPASSMAIN_STATUS_BY_TRANSACTION_ID");
	}
	@Override
	public synchronized boolean updateGatePassMainStatusByTransactionId(String transactionId, String status) {
		boolean res=false;
		Object[] object=new Object[]{status,transactionId};
		String query= getUpdateGatepassMainStatusByTransactionId();
		int i = jdbcTemplate.update(query,object);
		if(i>0){
			res=true;
		}
		return res;
	}
	
	public String getUpdateGatepassMainStatusType() {
		return QueryFileWatcher.getQuery("UPDATE_GATEPASSMAIN_STATUS_TYPE");
	}
	@Override
	public synchronized boolean updateGatePassMainStatusAndType(String gatePassId, String status,String gatePassType) {
		boolean res=false;
		Object[] object=new Object[]{status,gatePassType,gatePassId};
		String query = getUpdateGatepassMainStatusType();
		int i = jdbcTemplate.update(query,object);
		if(i>0){
			res=true;
		}
		return res;
	}

	@Override
	public boolean isLastApprover(String roleName, String  gatePassTypeId) {
		boolean status=false;
		
		SqlRowSet rs = jdbcTemplate.queryForRowSet(this.getLastApproverQuery(),gatePassTypeId,gatePassTypeId);
		if(rs.next()){
			if(roleName.equals(rs.getString("Role_Name")))
				status = true;
		}
		log.info("exit from isLastApprover method = "+status);
		return status; 
	}
	
	public String getUpdateGatepassAction() {
		return QueryFileWatcher.getQuery("UPDATE_GATE_PASS_ACTION");
	}

	@Override
	public String gatePassAction(GatePassActionDto dto) {
		 String result = null; 


	        Object[] parameters = new Object[] {dto.getGatePassType(),dto.getGatePassStatus(),dto.getCreatedBy(),dto.getComments(),dto.getGatePassId()}; 
 
	        try {
	        	String query = getUpdateGatepassAction();
	            int status = jdbcTemplate.update(query, parameters);
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

	public String getSaveGatePassStatusLog() {
		return QueryFileWatcher.getQuery("SAVE_GATEPASS_STATUSLOG");
	}
	@Override
	public void saveGatePassStatusLog(GatePassStatusLogDto dto) {
		log.info("Entered into saveGatePassStatusLog for gatePassId: "+dto.getGatePassId() );

        Object[] parameters = new Object[] {dto.getTransactionId(),dto.getGatePassId(),dto.getGatePassType(),dto.getStatus(),dto.getComments(),dto.getUpdatedBy()};

        try {
        	String query = getSaveGatePassStatusLog();
            int result = jdbcTemplate.update(query,parameters );
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
	
	@Override//not required anymore
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
	
	public String getGatepassById() {
		return QueryFileWatcher.getQuery("GET_GATE_PASS_BY_ID");
	}
	@Override
	public List<GatePassListingDto> getWorkmenDetailBasedOnId(String gatePassId) {
		log.info("Entering into getWorkmenDetailBasedOnId dao method ");
		List<GatePassListingDto> listDto= new ArrayList<GatePassListingDto>();
		String query = getGatepassById();
		log.info("Query to getWorkmenDetailBasedOnId "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,gatePassId);
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
	public String getValidityOfWoWc() {
		return QueryFileWatcher.getQuery("GET_VALIDITY_OF_WO_WC");
	}
	@Override
	 public Map<String, LocalDate> getValidityDates(String workOrderId, String wcId) {
		 Map<String, LocalDate> validityDates = new HashMap<>();
		 String query = getValidityOfWoWc();
		 SqlRowSet rs = jdbcTemplate.queryForRowSet(query,workOrderId,wcId);
		 while(rs.next()) {
			 LocalDate validTill = rs.getDate("validTill").toLocalDate();
             String source = rs.getString("source");
             validityDates.put(source, validTill);
			}
		 return validityDates;
	 }

	@Override
	public List<ApproverStatusDTO> getApprovalDetails(String transactionId,String unitId) {
		 // Fetch approvers from GATEPASSAPPROVERINFO
        List<ApproverInfo> approverList = this.getApproversByGatePassId(GatePassType.CREATE.getStatus(),unitId);

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

	private List<ApproverInfo> getApproversByGatePassId(String gatePassTypeId,String unitId) {
		 SqlRowSet rs = jdbcTemplate.queryForRowSet(getApproverHierarchy(),gatePassTypeId,unitId);
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
		    		 String query = this.getUpdateContractWorkmen();
			            int result = jdbcTemplate.update(query, parameters);
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
			        	String query = this.getSaveContractWorkmen();
			            int result = jdbcTemplate.update(query, parameters);
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
	
	public String getMaxTransactionId() {
		return QueryFileWatcher.getQuery("GET_MAX_TRANSACTION_ID");
	}
	@Override
	public String generateTransationId() {
	    String transactionId = null;
	    try {
	    	String query = getMaxTransactionId();
	        SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
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
	        gatePassMain.getGatePassAction(),
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
public String getContractWorkmenDraftDetails() {
	return QueryFileWatcher.getQuery("GET_CONTRACT_WORKMEN_DRAFT_DETAILS");
}

@Override
public GatePassMain getIndividualContractWorkmenDraftDetails(String transactionId) {
	log.info("Entering into getIndividualContractWorkmenDraftDetails dao method ");
	GatePassMain dto = null;
	String query = getContractWorkmenDraftDetails();
	log.info("Query to getIndividualContractWorkmenDraftDetails "+query);
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query,transactionId);
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
    		
        gatePassMain.getGatePassAction(),
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

public String getUpdateGatepassid() {
	return QueryFileWatcher.getQuery("UPDATE_GATEPASSID");
}
@Override
public synchronized String updateGatePassIdByTransactionId(String transactionId) {
	String gatePassId = this.generateGatePassId();
	if(null !=gatePassId) {
		Object[] object=new Object[]{gatePassId,transactionId};
		String query = getUpdateGatepassid();
		int i = jdbcTemplate.update(query,object);
		if(i>0){
			return gatePassId;
		}
		
	}
	return null;
}
public String getContractWorkmenDetailsByTransId() {
	return QueryFileWatcher.getQuery("GET_CONTRACT_WORKMEN_DETAILS_BY_TRANSID");
}
@Override
public GatePassMain getIndividualContractWorkmenDetailsByTransId(String transactionId) {
	log.info("Entering into getIndividualContractWorkmenDetails dao method ");
	GatePassMain dto = null;
	String query = getContractWorkmenDetailsByTransId();
	log.info("Query to getIndividualContractWorkmenDetails "+query);
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query,transactionId);
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
public List<GatePassListingDto> getRenewListingDetails(String userId,String gatePassTypeId,String gatePassStatus,String deptId,String unitId) {
	log.info("Entering into getRenewListingDetails dao method ");
	List<GatePassListingDto> listDto= new ArrayList<GatePassListingDto>();
	String query =getAllRenewForContractor();
	log.info("Query to getRenewListingDetails "+query);
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query,userId,gatePassTypeId,gatePassStatus,deptId,unitId);
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
		else if(gatePassType.equals(GatePassType.RENEW.getStatus())) {
			dto.setGatePassType("Renew");
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
public String renewGatePass(GatePassMain gatePassMain) {
    log.info("Entering into saveGatePass dao method");
    String transId=gatePassMain.getTransactionId();
   
	
    
    	
    	Object[] parameters = prepareGatePassParameters1(gatePassMain.getTransactionId(), gatePassMain); 
    	 try {
    		 String query = this.getUpdateContractWorkmen();
	            int result = jdbcTemplate.update(query, parameters);
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
   
    return transId;
}

public String getContractWorkmenDetailsByGpId() {
	return QueryFileWatcher.getQuery("GET_CONTRACT_WORKMEN_DETAILS_BY_GPID");
}
@Override
public GatePassMain getIndividualContractWorkmenDetailsByGatePassId(String gatePassId) {
	log.info("Entering into getIndividualContractWorkmenDetails dao method ");
	GatePassMain dto = null;
	String query = getContractWorkmenDetailsByGpId();
	log.info("Query to getIndividualContractWorkmenDetails "+query);
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query,gatePassId);
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
	log.info("Exiting from getIndividualContractWorkmenDetails dao method "+gatePassId);
	return dto;
}

@Override
public List<GatePassListingDto> getGatePassActionListingForApprovers(String roleId,int workFlowType,String gatePassTypeId,String deptId,String unitId) {
	log.info("Entering into getGatePassListingForApprovers dao method ");
	List<GatePassListingDto> listDto= new ArrayList<GatePassListingDto>();
	SqlRowSet rs =null;
	String query=null;
	if(workFlowType == WorkFlowType.SEQUENTIAL.getWorkFlowTypeId()) {
		query=this.getAllGatePassActionForSquential();
		log.info("Query to getGatePassListingForApprovers "+query);
		
		 rs = jdbcTemplate.queryForRowSet(query,gatePassTypeId,gatePassTypeId,gatePassTypeId,roleId,deptId,unitId);
	}else {
		query=this.getAllGatePassActionForParallel();
		log.info("Query to getGatePassListingForApprovers "+query);
		 rs = jdbcTemplate.queryForRowSet(query,roleId,gatePassTypeId,roleId,gatePassTypeId,deptId,unitId);
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

public String getWorkflowTypeByTransactionIdQuery() {
	return QueryFileWatcher.getQuery("GET_WORKFLOW_TYPE_BY_TRANSACTION_ID");
}
@Override
public int getWorkFlowTYpeByTransactionId(String transactionId,String actionId) {
	log.info("Entering into getWorkFlowTYpe dao method ");
	int workflowTypeId = 0;
	String query =getWorkflowTypeByTransactionIdQuery();
	log.info("Query to getWorkFlowTYpe "+query);
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query,transactionId,actionId);
	if(rs.next()) {
		workflowTypeId = rs.getInt("WorkflowType");
	}
	log.info("Exiting from getWorkFlowTYpe dao method "+transactionId);
	return workflowTypeId;
}
public String getIsLastApproverForParallel() {
	return QueryFileWatcher.getQuery("IS_LAST_APPROVER_FOR_PARALLEL");
}
@Override
public boolean isLastApproverForParallel(String gatePassTypeId, String transactionId, String roleId) {
    boolean status = false;

    String query = getIsLastApproverForParallel();

    try {
        // Ensure proper data type conversion
        int actionId = Integer.parseInt(gatePassTypeId);
        int approverRoleId = Integer.parseInt(roleId);

        SqlRowSet rs = jdbcTemplate.queryForRowSet(query, actionId, transactionId,actionId, approverRoleId);
        if (rs.next()) {
            String result = rs.getString("IsLastApprover");
            status = "YES".equals(result);
        }
    } catch (NumberFormatException e) {
        log.error("Invalid number format: gatePassTypeId={}, roleId={}", gatePassTypeId, roleId, e);
    } catch (Exception e) {
        log.error("Error executing isLastApproverForParallel query", e);
    }

    log.info("Exit from isLastApproverForParallel method = " + status);
    return status;
}

public String getIsLastApproverForParallelGatePassAction() {
	return QueryFileWatcher.getQuery("IS_LAST_APPROVER_FOR_GATEPASS_ACTION");
}
@Override
public boolean isLastApproverForParallelGatePassAction(String gatePassTypeId, String gatePassId, String roleId) {
    boolean status = false;

    String query = getIsLastApproverForParallelGatePassAction();

    try {
        // Ensure proper data type conversion
        int actionId = Integer.parseInt(gatePassTypeId);
        int approverRoleId = Integer.parseInt(roleId);

        SqlRowSet rs = jdbcTemplate.queryForRowSet(query, actionId, gatePassId,actionId, approverRoleId);
        if (rs.next()) {
            String result = rs.getString("IsLastApprover");
            status = "YES".equals(result);
        }
    } catch (NumberFormatException e) {
        log.error("Invalid number format: gatePassTypeId={}, roleId={}", gatePassTypeId, roleId, e);
    } catch (Exception e) {
        log.error("Error executing isLastApproverForParallel query", e);
    }

    log.info("Exit from isLastApproverForParallel method = " + status);
    return status;
}

@Override
public int getWorkFlowTYpeNew(String principalEmployer,String gatePassAction) {
	log.info("Entering into getWorkFlowTYpeNew dao method ");
	int workflowTypeId = 0;
	String query = getWorkflowTypeNew();
	log.info("Query to getWorkFlowTYpeNew "+query);
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query,principalEmployer,gatePassAction);
	if(rs.next()) {
		workflowTypeId = rs.getInt("WorkflowType");
	}
	log.info("Exiting from getWorkFlowTYpeNew dao method "+principalEmployer);
	return workflowTypeId;
}


public String getContractWorkmenExportQuery() {
	return QueryFileWatcher.getQuery("WORKMEN_EXPORT");
}
@Override
public List<ContractWorkmenExportDto> getContractWorkmenExportData(String unitId) {
	List<ContractWorkmenExportDto> dto = new ArrayList<ContractWorkmenExportDto>();
	String query = getContractWorkmenExportQuery();
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query,unitId,unitId);
	while(rs.next()) {
		ContractWorkmenExportDto obj = new ContractWorkmenExportDto();
		obj.setPersonNumber(rs.getString("GatePassId"));
		obj.setFirstName(rs.getString("FirstName"));
		obj.setLastName(rs.getString("LastName"));
		obj.setMiddleName(rs.getString("middleName"));
		obj.setShortName(rs.getString("shortName"));
		obj.setBadgeNumber(rs.getString("badgeNumber"));
		obj.setHireDate(rs.getString("hireDate"));
		obj.setBirthDate(rs.getString("birthDate"));
		obj.setPhone1(rs.getString("phone1"));
		obj.setPhone2(rs.getString("phone2"));
		obj.setPhone3(rs.getString("phone3"));
		obj.setEmail(rs.getString("email"));
		obj.setAddress(rs.getString("Address"));
		obj.setCity(rs.getString("city"));
		obj.setState(rs.getString("State"));
		obj.setCountry(rs.getString("postalCode"));
		obj.setPostalCode(rs.getString("country"));
		obj.setEmploymentStatus(rs.getString("employmentStatus"));
		
		obj.setEmploymentStatusEffectiveDate(rs.getString("employmentStatusEffectiveDate"));
		obj.setReportsToManager(rs.getString("ReportingManager"));
		obj.setWorkerType(rs.getString("workType"));
		obj.setUserAccountName(rs.getString("userAccountName"));
		obj.setUserAccountStatus(rs.getString("userAccountStatus"));
		obj.setUserPassword(rs.getString("userPassword"));
		obj.setCompany(rs.getString("company"));
		obj.setLocation(rs.getString("location"));
		obj.setPlantLocation(rs.getString("plantLocation"));
		obj.setDepartment(rs.getString("department"));
		obj.setSection(rs.getString("section"));
		obj.setSubSection(rs.getString("subSection"));
		obj.setContractorCOde(rs.getString("contractorCode"));
		obj.setHome8(rs.getString("home8"));
		obj.setHome9(rs.getString("home9"));
		obj.setCategory(rs.getString("category"));
		obj.setSupervisorId(rs.getString("supervisorId"));
		obj.setCostCenter(rs.getString("costCenter"));
		obj.setWorkorder(rs.getString("workorder"));
		obj.setHome4(rs.getString("home4"));
		obj.setHome5(rs.getString("home5"));
		obj.setHome6(rs.getString("home6"));
		obj.setCategoryEffectiveDate(rs.getString("categoryEffectiveDate"));
		obj.setGender(rs.getString("gender"));
		obj.setAadhar(rs.getString("AadharNumber"));
		obj.setNameAsAadhar(rs.getString("aadharName"));
		obj.setRelativeName(rs.getString("RelativeName"));
		obj.setPermanentAddress(rs.getString("Address"));
		obj.setPermanentDistrict(rs.getString("permanentDistrict"));
		obj.setPermanentState(rs.getString("permanentState"));
		obj.setPermanentpincode(rs.getString("permanentPincode"));
		obj.setIdMark(rs.getString("IdMark"));
		obj.setUanNumber(rs.getString("UanNumber"));
		obj.setMaritalStatus(rs.getString("MaritalStatus"));
		obj.setTechnical(rs.getString("Technical"));
		obj.setAcademic(rs.getString("academic"));
		obj.setShoeSize(rs.getString("shoesize"));
		obj.setBloodGroup(rs.getString("bloodGroup"));
		obj.setWorkmenType(rs.getString("workmenType"));
		obj.setNatureOfJob(rs.getString("NatureOfJob"));
		obj.setEsicNo(rs.getString("WcEsicNo"));
		obj.setPanNumber(rs.getString("panNumber"));
		obj.setPfNumber(rs.getString("pfNumber"));
		obj.setBankAccountNumber(rs.getString("AccountNumber"));
		obj.setBankName(rs.getString("bankName"));
		obj.setIfscCode(rs.getString("IfscCode"));
		obj.setFutureUse2(rs.getString("future2"));
		obj.setFutureUse3(rs.getString("future3"));
		obj.setFutureUse4(rs.getString("future4"));
		obj.setFutureUse5(rs.getString("future5"));
		obj.setFutureUse6(rs.getString("future6"));
		obj.setFutureUse7(rs.getString("future7"));
		obj.setFutureUse8(rs.getString("future8"));
		obj.setFutureDate1(rs.getString("futureDate1"));
		obj.setFutureDate2(rs.getString("futureDate2"));
		obj.setFutureDate3(rs.getString("futureDate3"));
		obj.setFutureDate4(rs.getString("futureDate4"));
		obj.setFutureDate5(rs.getString("futureDate5"));
		obj.setSkill(rs.getString("skill"));
		obj.setProLevel(rs.getString("proLevel"));
		obj.setSkillDate(rs.getString("skillDate"));
		obj.setCert1(rs.getString("cert1"));
		obj.setCert1StartDate(rs.getString("cert1StartDate"));
		obj.setCert1EndDate(rs.getString("cert1EndDate"));
		
		obj.setCert2(rs.getString("cert2"));
		obj.setCert2StartDate(rs.getString("cert2StartDate"));
		obj.setCert2EndDate(rs.getString("cert2EndDate"));
		
		obj.setCert3(rs.getString("cert3"));
		obj.setCert3StartDate(rs.getString("cert3StartDate"));
		obj.setCert3EndDate(rs.getString("cert3EndDate"));
		
		obj.setCert4(rs.getString("cert4"));
		obj.setCert4StartDate(rs.getString("cert4StartDate"));
		obj.setCert4EndDate(rs.getString("cert4EndDate"));
		dto.add(obj);
	}
	return dto;
}


public String getAadharExistsQuery() {
	return QueryFileWatcher.getQuery("AADHAR_EXISTS");
}

@Override
public boolean isAadharExists(String aadharNumber,String transactionId) {
	String sql = getAadharExistsQuery();
    Integer count = jdbcTemplate.queryForObject(sql, new Object[]{aadharNumber,transactionId}, Integer.class);
    return count != null && count > 0;
}



}
