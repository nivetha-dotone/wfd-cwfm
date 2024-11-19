package com.wfd.dot1.cwfm.service;

import java.util.List;

import com.wfd.dot1.cwfm.dto.ApproveRejectGatePassDto;
import com.wfd.dot1.cwfm.dto.ApproverStatusDTO;
import com.wfd.dot1.cwfm.dto.GatePassActionDto;
import com.wfd.dot1.cwfm.dto.GatePassListingDto;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.GatePassMain;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.Skill;
import com.wfd.dot1.cwfm.pojo.Trade;
import com.wfd.dot1.cwfm.pojo.Workorder;

public interface WorkmenService {

	public List<PrincipalEmployer> getAllPrincipalEmployer(String userId);
	
	public List<Contractor> getAllContractorBasedOnPE(String unitId,String userId);

	public List<Workorder> getAllWorkordersBasedOnPEAndContractor(String unitId, String contractorId);

	public List<Trade> getAllTradesBasedOnPE(String unitId);

	public List<Skill> getAllSkill();

	public List<CmsGeneralMaster> getAllDepartmentAndSubDepartment(String userId);

	public List<MasterUser> getAllEicManager(String userId);

	public List<CmsContractorWC> getAllWCBasedOnPEAndCont(String unitId, String contractorId);

	public List<CmsGeneralMaster> getAllGenders();

	public List<CmsGeneralMaster> getAllGeneralMaster();

	public String saveGatePass(GatePassMain gatePassMain);

	public List<GatePassListingDto> getGatePassListingDetails(String userId,String gatePassTypeId);

	public List<GatePassListingDto> getGatePassListingForApprovers(MasterUser userId,String gatePassTypeId);

	public GatePassMain getIndividualContractWorkmenDetails(String gatePassId);

	public List<CmsGeneralMaster> getAllGeneralMasterForGatePass(GatePassMain gatePassMainObj);

	public String approveRejectGatePass(ApproveRejectGatePassDto dto);

	public String gatePassAction(GatePassActionDto dto);
	
	public List<GatePassListingDto> getGatePassActionListingDetails(String userId,String gatePassTypeId,String previousGatePassAction);

	public List<GatePassListingDto> getWorkmenDetailBasedOnId(String gatePassId);

	public List<ApproverStatusDTO> getApprovalDetails(String gatePassId);


}	
