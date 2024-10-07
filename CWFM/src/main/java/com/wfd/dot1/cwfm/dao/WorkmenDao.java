package com.wfd.dot1.cwfm.dao;

import java.util.List;

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

	public List<GatePassListingDto> getGatePassListingDetails(String userId);

	public List<GatePassListingDto> getGatePassListingForApprovers(String userId);

	public GatePassMain getIndividualContractWorkmenDetails(String gatePassId);


	public List<CmsGeneralMaster> getAllGeneralMastersForGatePass(GatePassMain gpm);


	public int getWorkFlowTYpe(String principalEmployer);

	public List<MasterUser> getApproversForGatePass(String createdBy);

	public void saveGatePassApprover(String gatePassId, List<MasterUser> approverList,String createdBy);
}
