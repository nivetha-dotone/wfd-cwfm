package com.wfd.dot1.cwfm.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.wfd.dot1.cwfm.dto.ApproveRejectGatePassDto;
import com.wfd.dot1.cwfm.dto.ApproverStatusDTO;
import com.wfd.dot1.cwfm.dto.GatePassActionDto;
import com.wfd.dot1.cwfm.dto.GatePassListingDto;
import com.wfd.dot1.cwfm.dto.GatePassStatusLogDto;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.GatePassMain;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.Skill;
import com.wfd.dot1.cwfm.pojo.Trade;
import com.wfd.dot1.cwfm.pojo.Workorder;

public interface WorkmenDao {

	public List<PrincipalEmployer> getAllPrincipalEmployer(String userId);
	
	public List<Contractor> getAllContractorBasedOnPE(String unitId,String userId);
	
	public List<Workorder> getAllWorkordersBasedOnPEAndContractor(String unitId, String contractorId);
	
	public List<Trade> getAllTradesBasedOnPE(String unitId);
	
	public List<Skill> getAllSkill();
	
	public List<CmsGeneralMaster> getAllDepartmentAndSubDepartment(String userId);
	
	public List<MasterUser> getAllEicManager(String unitId,String deptId);
	
	public List<CmsContractorWC> getAllWCBasedOnPEAndCont(String unitId, String contractorId);

	public List<CmsGeneralMaster> getAllGenders();

	public List<CmsGeneralMaster> getAllGeneralMasters();

	public String saveGatePass(GatePassMain gatePassMain);

	public List<GatePassListingDto> getGatePassListingDetails(String unitId,String deptId,String userId, String gatePassTypeId);

	public List<GatePassListingDto> getGatePassListingForApprovers(String userId,int workFlowType,String gatePassTypeId,String deptId,String unitId);

	public GatePassMain getIndividualContractWorkmenDetails(String gatePassId);


	public List<CmsGeneralMaster> getAllGeneralMastersForGatePass(GatePassMain gpm);


	public int getWorkFlowTYpe(String principalEmployer);

	public List<MasterUser> getApproversForGatePass(String createdBy);

	public void saveGatePassApprover(String gatePassId, List<MasterUser> approverList,String createdBy);

	public String approveRejectGatePass(ApproveRejectGatePassDto dto);

	public int getWorkFlowTypeForApprovers(String businessType);

	public boolean updateGatePassMainStatus(String gatePassId, String status);

	public boolean isLastApprover(String roleName,String gatePassTypeId);

	public String gatePassAction(GatePassActionDto dto);

	public void saveGatePassStatusLog(GatePassStatusLogDto dto);

	public List<GatePassListingDto> getGatePassActionListingDetails(String unitId,String deptId,String userId, String gatePassTypeId,String previousGatePassAction);

	public List<MasterUser> getApproversForGatePassAction(String createdBy,String gatepassAction);

	boolean updateGatePassMainStatusAndType(String gatePassId, String status, String gatePassType);

	public List<GatePassListingDto> getWorkmenDetailBasedOnId(String gatePassId);

	public int getDOTTYpe(String principalEmployer);

	Map<String, LocalDate> getValidityDates(String workOrderId, String wcId);

	public List<ApproverStatusDTO> getApprovalDetails(String transactionId);

	public List<Contractor> getAllContractorForAdmin(String unitId);

	String draftGatePass(GatePassMain gatePassMain);
	
	public  String generateTransationId();

	GatePassMain getIndividualContractWorkmenDraftDetails(String transactionId);

	public String updateGatePassIdByTransactionId(String transactionId);

	GatePassMain getIndividualContractWorkmenDetailsByTransId(String transactionId);

	List<GatePassListingDto> getRenewListingDetails(String userId, String gatePassTypeId, String gatePassStatus,
			String deptId, String unitId);

	String renewGatePass(GatePassMain gatePassMain);

	GatePassMain getIndividualContractWorkmenDetailsByGatePassId(String gatePassId);


	List<GatePassListingDto> getGatePassActionListingForApprovers(String roleId, int workFlowType,
			String gatePassTypeId, String deptId, String unitId);

	int getWorkFlowTYpeByTransactionId(String transactionId,String actionId);

	boolean isLastApproverForParallel(String gatePassTypeId, String transactionId, String roleId);

	boolean isLastApproverForParallelGatePassAction(String gatePassTypeId, String gatePassId, String roleId);

	int getWorkFlowTYpeNew(String principalEmployer, String gatePassAction);


	public boolean updateGatePassMainStatusByTransactionId(String transactionId, String status);


}
