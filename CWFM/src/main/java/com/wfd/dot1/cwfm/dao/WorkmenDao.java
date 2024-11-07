package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.dto.ApproveRejectGatePassDto;
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
	
	public List<MasterUser> getAllEicManager(String userId);
	
	public List<CmsContractorWC> getAllWCBasedOnPEAndCont(String unitId, String contractorId);

	public List<CmsGeneralMaster> getAllGenders();

	public List<CmsGeneralMaster> getAllGeneralMasters();

	public String saveGatePass(GatePassMain gatePassMain);

	public List<GatePassListingDto> getGatePassListingDetails(String userId, String gatePassTypeId);

	public List<GatePassListingDto> getGatePassListingForApprovers(String userId,int workFlowType,String gatePassTypeId);

	public GatePassMain getIndividualContractWorkmenDetails(String gatePassId);


	public List<CmsGeneralMaster> getAllGeneralMastersForGatePass(GatePassMain gpm);


	public int getWorkFlowTYpe(String principalEmployer);

	public List<MasterUser> getApproversForGatePass(String createdBy);

	public void saveGatePassApprover(String gatePassId, List<MasterUser> approverList,String createdBy);

	public String approveRejectGatePass(ApproveRejectGatePassDto dto);

	public int getWorkFlowTypeForApprovers(String businessType);

	public boolean updateGatePassMainStatus(String gatePassId, String status);

	public boolean isLastApprover(String gatePassId, String approverId,String gatePassType);

	public String gatePassAction(GatePassActionDto dto);

	public void saveGatePassStatusLog(GatePassStatusLogDto dto);

	public List<GatePassListingDto> getGatePassActionListingDetails(String userId, String gatePassTypeId,String previousGatePassAction);

	public List<MasterUser> getApproversForGatePassAction(String createdBy,String gatepassAction);

	boolean updateGatePassMainStatusAndType(String gatePassId, String status, String gatePassType);

}
