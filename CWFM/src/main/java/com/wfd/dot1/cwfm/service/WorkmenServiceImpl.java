package com.wfd.dot1.cwfm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.WorkmenDao;
import com.wfd.dot1.cwfm.dto.ApproveRejectGatePassDto;
import com.wfd.dot1.cwfm.dto.GatePassListingDto;
import com.wfd.dot1.cwfm.enums.GatePassStatus;
import com.wfd.dot1.cwfm.enums.UserRole;
import com.wfd.dot1.cwfm.enums.WorkFlowType;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.GatePassMain;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.Skill;
import com.wfd.dot1.cwfm.pojo.Trade;
import com.wfd.dot1.cwfm.pojo.Workorder;

@Service
public class WorkmenServiceImpl implements WorkmenService{

	@Autowired
	WorkmenDao workmenDao;
	@Override
	public List<PrincipalEmployer> getAllPrincipalEmployer(String userId) {
		
		return workmenDao.getAllPrincipalEmployer(userId);
	}
	@Override
	public List<Contractor> getAllContractorBasedOnPE(String unitId,String userId) {
		
		return workmenDao.getAllContractorBasedOnPE(unitId, userId);
	}
	@Override
	public List<Workorder> getAllWorkordersBasedOnPEAndContractor(String unitId, String contractorId) {
		return workmenDao.getAllWorkordersBasedOnPEAndContractor(unitId, contractorId);
	}
	@Override
	public List<Trade> getAllTradesBasedOnPE(String unitId) {
		return workmenDao.getAllTradesBasedOnPE(unitId);
	}
	@Override
	public List<Skill> getAllSkill() {
		return workmenDao.getAllSkill();
	}
	@Override
	public List<CmsGeneralMaster> getAllDepartmentAndSubDepartment(String userId) {
		return workmenDao.getAllDepartmentAndSubDepartment(userId);
	}
	@Override
	public List<MasterUser> getAllEicManager(String userId) {
		return workmenDao.getAllEicManager(userId);
	}
	@Override
	public List<CmsContractorWC> getAllWCBasedOnPEAndCont(String unitId, String contractorId) {
		return workmenDao.getAllWCBasedOnPEAndCont(unitId, contractorId);
	}
	@Override
	public List<CmsGeneralMaster> getAllGenders() {
		return workmenDao.getAllGenders();
	}
	@Override
	public List<CmsGeneralMaster> getAllGeneralMaster() {
		return workmenDao.getAllGeneralMasters();
	}
	@Override
	public String saveGatePass(GatePassMain gatePassMain) {
		String gatePassId =null;
		try {
			int workFlowTypeId = workmenDao.getWorkFlowTYpe(gatePassMain.getPrincipalEmployer());
			gatePassMain.setWorkFlowType(workFlowTypeId);
			
			if(workFlowTypeId == WorkFlowType.AUTO.getWorkFlowTypeId()) {
				gatePassMain.setGatePassStatus(GatePassStatus.APPROVED.getStatus());
				return workmenDao.saveGatePass(gatePassMain);
			}else {
				gatePassMain.setGatePassStatus(GatePassStatus.APPROVALPENDING.getStatus());
				gatePassId = workmenDao.saveGatePass(gatePassMain);
				//get approvers for gatepass
				List<MasterUser> approversList = workmenDao.getApproversForGatePass(gatePassMain.getCreatedBy());
				
				if(null!=approversList && approversList.size()>0){
					for(MasterUser mu :approversList) {
						if(mu.getRoleName().equalsIgnoreCase(UserRole.EIC.getName())) {
								mu.setIndex(1);
							
						}else if(mu.getRoleName().equalsIgnoreCase(UserRole.SECURITY.getName())){
								mu.setIndex(2);
							
						}else if(mu.getRoleName().equalsIgnoreCase(UserRole.HR.getName())) {
								mu.setIndex(3);
							
						}
					}
					workmenDao.saveGatePassApprover(gatePassId, approversList, gatePassMain.getCreatedBy());
				}
			}
		}catch(Exception e) {
			
		}
		return gatePassId;
	}
	@Override
	public List<GatePassListingDto> getGatePassListingDetails(String userId) {
		return workmenDao.getGatePassListingDetails(userId);
	}
	@Override
	public List<GatePassListingDto> getGatePassListingForApprovers(MasterUser user) {
			int workFlowTypeId = workmenDao.getWorkFlowTypeForApprovers(user.getBusinessType());
			return workmenDao.getGatePassListingForApprovers(user.getUserId(),workFlowTypeId);
	}
	@Override
	public GatePassMain getIndividualContractWorkmenDetails(String gatePassId) {
		return workmenDao.getIndividualContractWorkmenDetails(gatePassId);
	}
	@Override
	public List<CmsGeneralMaster> getAllGeneralMasterForGatePass(GatePassMain gatePassMainObj) {
		return workmenDao.getAllGeneralMastersForGatePass(gatePassMainObj);
	}
	@Override
	public String approveRejectGatePass(ApproveRejectGatePassDto dto) {
		String result = workmenDao.approveRejectGatePass(dto);
		boolean status=false;
		if(dto.getStatus().equals(GatePassStatus.REJECTED.getStatus())) {
			status = workmenDao.updateGatePassMainStatus(dto.getGatePassId(),dto.getStatus());
		}else {
			//check if last approver, if last approver change the status of gate pass main to approved
			boolean isLastApprover = workmenDao.isLastApprover(dto.getGatePassId(),dto.getApproverId());
			if(isLastApprover) {
				status = workmenDao.updateGatePassMainStatus(dto.getGatePassId(),dto.getStatus());
			}
		}
		return result;
	}
	

}
