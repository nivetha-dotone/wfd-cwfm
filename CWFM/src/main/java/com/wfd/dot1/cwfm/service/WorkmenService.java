package com.wfd.dot1.cwfm.service;

import java.util.List;
import java.util.Map;

import com.wfd.dot1.cwfm.dto.ApproveRejectGatePassDto;
import com.wfd.dot1.cwfm.dto.ApproverStatusDTO;
import com.wfd.dot1.cwfm.dto.GatePassActionDto;
import com.wfd.dot1.cwfm.dto.GatePassListingDto;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.ContractWorkmenExportDto;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.DeptMapping;
import com.wfd.dot1.cwfm.pojo.GatePassMain;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.Skill;
import com.wfd.dot1.cwfm.pojo.Trade;
import com.wfd.dot1.cwfm.pojo.WageDetailsDto;
import com.wfd.dot1.cwfm.pojo.Workorder;

public interface WorkmenService {

	public List<PrincipalEmployer> getAllPrincipalEmployer(String userAccount);
	
	public List<Contractor> getAllContractorBasedOnPE(String unitId,String userId);

	public List<Workorder> getAllWorkordersBasedOnPEAndContractor(String unitId, String contractorId);

	public List<Trade> getAllTradesBasedOnPE(String unitId);

	public List<Skill> getAllSkill();

	public List<CmsGeneralMaster> getAllDepartmentAndSubDepartment(String userId);

	public List<MasterUser> getAllEicManager(String unitId,String deptId);

	public List<CmsContractorWC> getAllWCBasedOnPEAndCont(String unitId, String contractorId,String workorderId);

	public List<CmsGeneralMaster> getAllGenders();

	public List<CmsGeneralMaster> getAllGeneralMaster();

	public String saveGatePass(GatePassMain gatePassMain);

	public List<GatePassListingDto> getGatePassListingDetails(String unitId,String deptId,String userId,String gatePassTypeId,String type);

	public List<GatePassListingDto> getGatePassListingForApprovers(String unitId,String deptId,MasterUser user, String gatePassTypeId,String type);

	public GatePassMain getIndividualContractWorkmenDetails(String gatePassId);

	public List<CmsGeneralMaster> getAllGeneralMasterForGatePass(GatePassMain gatePassMainObj);

	public String approveRejectGatePass(ApproveRejectGatePassDto dto);

	public String gatePassAction(GatePassActionDto dto);
	
	public List<GatePassListingDto> getGatePassActionListingDetails(String unitId,String deptId,String userId,String gatePassTypeId,String previousGatePassAction);

	public List<GatePassListingDto> getWorkmenDetailBasedOnId(String gatePassId);

	public List<ApproverStatusDTO> getApprovalDetails(String gatePassId,String unitId);

	public List<Contractor> getAllContractorForAdmin(String principalEmployerId);

	String draftGatePass(GatePassMain gatePassMain);

	String generateTransactionId();

	GatePassMain getIndividualContractWorkmenDraftDetails(String transactionId);

	public int getWorkFlowTYpe(String principalEmployer);

	List<GatePassListingDto> getRenewListingDetails(String userId, String gatePassTypeId, String gatePassStatus,
			String deptId, String unitId);

	String renewGatePass(GatePassMain gatePassMain);


	List<GatePassListingDto> getGatePassActionListingForApprovers(String unitId, String deptId, MasterUser user,
			String gatePassTypeId);

	Map<String, Object> verifyOtpWithSurepass(String clientId, String otp);


	boolean isAadhaarExists(String aadhaarNumber);
	
	boolean isAadharDuplicate(String aadharNumber);

	public List<ContractWorkmenExportDto> getContractWorkmenExportData(String unitId);


	public boolean isAadharExists(String aadharNumber,String transactionId);


	

	public List<WageDetailsDto> getWageMasterExportData(String unitId);

	public String getOtherAadharLinkedToUan(String uan, String aadharNumber);

	public String getOtherAadharLinkedTopfNumber(String pfNumber, String aadharNumber);

	public void createDraftGatepass(String transactionId, String userId);

	List<GatePassListingDto> getGatePassActionListingDetailsForDashboardNav(String unitId, String deptId, String userId,
			String gatePassTypeId);

	public List<DeptMapping> getAllSkills(String unitId, String tradeId);

	public List<DeptMapping> getAllDepartmentsOnPE(String unitId);

	public List<DeptMapping> getAllSubDepartments(String unitId, String departmentId);
	
	String getTransactionIdByGPId(String gatepassid,String gatepasstypeid);
	GatePassMain getIndividualContractWorkmenDetailsByGatePassId(String gatepassid);

}	
