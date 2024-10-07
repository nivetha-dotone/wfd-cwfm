package com.wfd.dot1.cwfm.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.GatePassListingDto;
import com.wfd.dot1.cwfm.enums.GatePassStatus;
import com.wfd.dot1.cwfm.enums.GatePassType;
import com.wfd.dot1.cwfm.enums.UserRole;
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

@Repository
public class WorkmenDaoImpl implements WorkmenDao{

	private static final Logger log = LoggerFactory.getLogger(WorkmenDaoImpl.class.getName());
	
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	@Override
	public List<PrincipalEmployer> getAllPrincipalEmployer(String userId) {
		log.info("Entering into getAllPrincipalEmployer dao method "+userId);
		List<PrincipalEmployer> peList= new ArrayList<PrincipalEmployer>();
		log.info("Query to getAllPrincipalEmployer "+WorkmenQueryBank.GET_ALL_PRINCIPAL_EMPLOYER);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_ALL_PRINCIPAL_EMPLOYER, userId);
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
	public List<Contractor> getAllContractorBasedOnPE(String unitId,String userId) {
		log.info("Entering into getAllContractorBasedOnPE dao method "+unitId+" "+userId);
		List<Contractor> contList= new ArrayList<Contractor>();
		log.info("Query to getAllContractorBasedOnPE "+WorkmenQueryBank.GET_ALL_CONTRACTOR_BY_PE);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_ALL_CONTRACTOR_BY_PE,unitId, userId);
		while(rs.next()) {
			Contractor cont = new Contractor();
			cont.setContractorId(rs.getString("contractorid"));
			cont.setContractorName(rs.getString("contractorname"));
			contList.add(cont);
		}
		log.info("Exiting from getAllContractorBasedOnPE dao method "+contList.size());
		return contList;
	}

	@Override
	public List<Workorder> getAllWorkordersBasedOnPEAndContractor(String unitId, String contractorId) {
		log.info("Entering into getAllWorkordersBasedOnPEAndContractor dao method "+unitId+" "+contractorId);
		List<Workorder> woList= new ArrayList<Workorder>();
		log.info("Query to getAllWorkordersBasedOnPEAndContractor "+WorkmenQueryBank.GET_ALL_WORKORDER_BY_PE_AND_CONT);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_ALL_WORKORDER_BY_PE_AND_CONT,unitId, contractorId,contractorId);
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
		log.info("Query to getAllTradesBasedOnPE "+WorkmenQueryBank.GET_ALL_TRADES_BY_PE);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_ALL_TRADES_BY_PE,unitId);
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
		log.info("Query to getAllSkill "+WorkmenQueryBank.GET_ALL_SKILL);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_ALL_SKILL);
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
	public List<MasterUser> getAllEicManager(String userId) {
		log.info("Entering into getAllEicManager dao method ");
		List<MasterUser> eicList= new ArrayList<MasterUser>();
		log.info("Query to getAllEicManager "+WorkmenQueryBank.GET_ALL_EIC);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_ALL_EIC,userId);
		while(rs.next()) {
			MasterUser mu = new MasterUser();
			mu.setUserId(rs.getString("userId"));
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
		log.info("Query to getAllWCBasedOnPEAndCont "+WorkmenQueryBank.GET_ALL_WC);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_ALL_WC,unitId,contractorId);
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
		log.info("Query to getAllGeneralMasters "+WorkmenQueryBank.GET_ALL_CMSGENERALMASTER);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_ALL_CMSGENERALMASTER);
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

	    String gatePassId = generateGatePassId(); 

	    if (gatePassId != null) {
	        log.info("Gatepass generated for Aadhaar: " + gatePassMain.getAadhaarNumber() + " is: " + gatePassId);

	        Object[] parameters = prepareGatePassParameters(gatePassId, gatePassMain); 

	        try {
	            int result = jdbcTemplate.update(WorkmenQueryBank.SAVE_CONTRACT_WORKMEN, parameters);
	            if (result > 0) {
	                log.info("GatePass saved successfully for GatePassId: " + gatePassId);
	            } else {
	                log.warn("Failed to save GatePass for GatePassId: " + gatePassId);
	            }
	        } catch (Exception e) {
	            log.error("Error saving GatePass for GatePassId: " + gatePassId, e);
	            return null;
	        }
	    } else {
	        log.error("Failed to generate GatePassId for Aadhaar: " + gatePassMain.getAadhaarNumber());
	    }

	    return gatePassId;
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

	private Object[] prepareGatePassParameters(String gatePassId, GatePassMain gatePassMain) {
		
	    return new Object[]{
	        gatePassId,
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
	        "aadhar", "img", "bank", "police", "id2", "medical", "education", 
	        "form11", "training", "other",
	        gatePassMain.getWorkFlowType(),
	        gatePassMain.getUserId()
	    };
	}

	@Override
	public List<GatePassListingDto> getGatePassListingDetails(String userId) {
		log.info("Entering into getGatePassListingDetails dao method ");
		List<GatePassListingDto> listDto= new ArrayList<GatePassListingDto>();
		log.info("Query to getGatePassListingDetails "+WorkmenQueryBank.GET_ALL_GATE_PASS_FOR_CREATOR);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_ALL_GATE_PASS_FOR_CREATOR,userId);
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
			dto.setStatus(rs.getString("GatePassStatus"));
			listDto.add(dto);
		}
		log.info("Exiting from getGatePassListingDetails dao method "+listDto.size());
		return listDto;
	}

	@Override
	public List<GatePassListingDto> getGatePassListingForApprovers(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GatePassMain getIndividualContractWorkmenDetails(String gatePassId) {
		log.info("Entering into getIndividualContractWorkmenDetails dao method ");
		GatePassMain dto = null;
		log.info("Query to getIndividualContractWorkmenDetails "+WorkmenQueryBank.GET_CONTRACT_WORKMEN_DETAILS);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_CONTRACT_WORKMEN_DETAILS,gatePassId);
		if(rs.next()) {
			dto = new GatePassMain();
			dto.setGatePassId(rs.getString("GatePassId"));
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
		}
		log.info("Exiting from getIndividualContractWorkmenDetails dao method "+gatePassId);
		return dto;
	}
	
	@Override
	public List<CmsGeneralMaster> getAllGeneralMastersForGatePass(GatePassMain gpm) {
		log.info("Entering into getAllGeneralMastersForGatePass dao method ");
		List<CmsGeneralMaster> gmList= new ArrayList<CmsGeneralMaster>();
		Object[] obj = new Object[] {gpm.getGender(),gpm.getBloodGroup(),gpm.getAccessArea(),gpm.getAcademic(),gpm.getZone(),gpm.getWageCategory(),gpm.getBonusPayout(),gpm.getDepartment(),gpm.getSubdepartment()};
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
		log.info("Query to getWorkFlowTYpe "+WorkmenQueryBank.GET_WORKFLOW_TYPE_BY_PE);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(WorkmenQueryBank.GET_WORKFLOW_TYPE_BY_PE,principalEmployer);
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
			mu.setUserId(rs.getString("UserId"));
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
				ps.setString(3, approverList.get(i).getUserId());
				ps.setInt(4, approverList.get(i).getIndex());
				ps.setInt(5,0);
				ps.setString(6,createdBy);
				
			}

			public int getBatchSize() {
				return approverList.size();
			}
		});
	}

}
