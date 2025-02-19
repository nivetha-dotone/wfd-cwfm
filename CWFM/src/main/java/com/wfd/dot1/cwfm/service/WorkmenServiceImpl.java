package com.wfd.dot1.cwfm.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.WorkmenDao;
import com.wfd.dot1.cwfm.dto.ApproveRejectGatePassDto;
import com.wfd.dot1.cwfm.dto.ApproverStatusDTO;
import com.wfd.dot1.cwfm.dto.GatePassActionDto;
import com.wfd.dot1.cwfm.dto.GatePassListingDto;
import com.wfd.dot1.cwfm.dto.GatePassStatusLogDto;
import com.wfd.dot1.cwfm.enums.DotType;
import com.wfd.dot1.cwfm.enums.GatePassStatus;
import com.wfd.dot1.cwfm.enums.GatePassType;
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
	public List<PrincipalEmployer> getAllPrincipalEmployer(String userAccount) {
		
		return workmenDao.getAllPrincipalEmployer(userAccount);
	}
	@Override
	public List<Contractor> getAllContractorBasedOnPE(String unitId,String userAccount) {
		
		return workmenDao.getAllContractorBasedOnPE(unitId, userAccount);
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
	public List<MasterUser> getAllEicManager(String unitId,String deptId) {
		return workmenDao.getAllEicManager(unitId,deptId);
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
			
			int dotTypeId = workmenDao.getDOTTYpe(gatePassMain.getPrincipalEmployer());
			gatePassMain.setDotType(dotTypeId);
			
			String dot = this.getDOT(gatePassMain);
			gatePassMain.setDot(dot);
			if(workFlowTypeId == WorkFlowType.AUTO.getWorkFlowTypeId()) {
				gatePassMain.setGatePassStatus(GatePassStatus.APPROVED.getStatus());
				gatePassId = workmenDao.saveGatePass(gatePassMain); 
				gatePassMain.setGatePassId(gatePassId);
				GatePassStatusLogDto dto =new GatePassStatusLogDto();
				dto.setGatePassId(gatePassId);
				dto.setGatePassType(GatePassType.CREATE.getStatus());
				dto.setStatus(Integer.parseInt(GatePassStatus.APPROVED.getStatus()));
				dto.setComments(gatePassMain.getComments());
				dto.setUpdatedBy(gatePassMain.getUserId());
				workmenDao.saveGatePassStatusLog(dto);
				return gatePassId;
			}else {
				gatePassMain.setGatePassStatus(GatePassStatus.APPROVALPENDING.getStatus());
				gatePassId = workmenDao.saveGatePass(gatePassMain);
				gatePassMain.setGatePassId(gatePassId);
				GatePassStatusLogDto dto =new GatePassStatusLogDto();
				dto.setGatePassId(gatePassId);
				dto.setGatePassType(GatePassType.CREATE.getStatus());
				dto.setStatus(Integer.parseInt(GatePassStatus.APPROVED.getStatus()));
				dto.setComments(gatePassMain.getComments());
				dto.setUpdatedBy(gatePassMain.getUserId());
				workmenDao.saveGatePassStatusLog(dto);
				//get approvers for gatepass
//				List<MasterUser> approversList = workmenDao.getApproversForGatePass(gatePassMain.getCreatedBy());
//				
//				if(null!=approversList && approversList.size()>0){
//					for(MasterUser mu :approversList) {
//						mu.setStatus(GatePassType.CREATE.getStatus());
//						if(mu.getRoleName().equalsIgnoreCase(UserRole.EIC.getName())) {
//								mu.setIndex(1);
//							
//						}else if(mu.getRoleName().equalsIgnoreCase(UserRole.SECURITY.getName())){
//								mu.setIndex(2);
//							
//						}else if(mu.getRoleName().equalsIgnoreCase(UserRole.HR.getName())) {
//								mu.setIndex(3);
//							
//						}
//					}
//					workmenDao.saveGatePassApprover(gatePassId, approversList, gatePassMain.getCreatedBy());
//				}
				
			}
		}catch(Exception e) {
			
		}
		return gatePassId;
	}
	
	@Override
	public List<GatePassListingDto> getGatePassListingDetails(String unitId,String deptId,String userId,String gatePassTypeId) {
		return workmenDao.getGatePassListingDetails(unitId,deptId,userId,gatePassTypeId);
	}
	@Override
	public List<GatePassListingDto> getGatePassListingForApprovers(String unitId ,String deptId,MasterUser user,String gatePassTypeId) {
		int workFlowTypeId = workmenDao.getWorkFlowTYpe(unitId);
			return workmenDao.getGatePassListingForApprovers(user.getRoleId(),workFlowTypeId,gatePassTypeId,deptId,unitId);
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
		GatePassMain gpm = workmenDao.getIndividualContractWorkmenDetails(dto.getGatePassId());
		String result=null;
		//if(gpm.getGatePassAction().equals(GatePassType.CREATE.getStatus()) && gpm.getGatePassStatus().equals(GatePassStatus.APPROVED.getStatus())) {
		 result = workmenDao.approveRejectGatePass(dto);
		GatePassStatusLogDto statusLog = new GatePassStatusLogDto();
		statusLog.setGatePassId(dto.getGatePassId());
		statusLog.setGatePassType(dto.getGatePassType());
		statusLog.setStatus(Integer.parseInt(dto.getStatus()));
		statusLog.setComments(dto.getComments());
		statusLog.setUpdatedBy(dto.getApproverId());
		workmenDao.saveGatePassStatusLog(statusLog);
		boolean status=false;
		if(dto.getStatus().equals(GatePassStatus.REJECTED.getStatus())) {
			if(gpm.getGatePassAction().equals(GatePassType.CREATE.getStatus())) {
			status = workmenDao.updateGatePassMainStatus(dto.getGatePassId(),dto.getStatus());
			}else {
			//rollback status and type to create
			status = workmenDao.updateGatePassMainStatusAndType(dto.getGatePassId(),GatePassStatus.APPROVED.getStatus(),GatePassType.CREATE.getStatus());
			}
		}else {
			//check if last approver, if last approver change the status of gate pass main to approved
			boolean isLastApprover = workmenDao.isLastApprover(dto.getApproverRole(),String.valueOf(dto.getGatePassType()));
			if(isLastApprover) {
				if(dto.getGatePassType().equals(GatePassType.DEBLACKLIST.getStatus()) || dto.getGatePassType().equals(GatePassType.UNBLOCK.getStatus())) {
					status = workmenDao.updateGatePassMainStatusAndType(dto.getGatePassId(),dto.getStatus(),GatePassType.CREATE.getStatus());
				//rollback status and type to create
				}else {
				status = workmenDao.updateGatePassMainStatus(dto.getGatePassId(),dto.getStatus());
				}
			}
		}
//		}else {
//			result="Other gatepass action performed on this gatepass";
//		}
		return result;
	}
	@Override
	public String gatePassAction(GatePassActionDto dto) {
	String result=null;
		try {
			
			//approver logic needs to be added
			 GatePassMain gatePassMain = workmenDao.getIndividualContractWorkmenDetails(dto.getGatePassId());
			if((gatePassMain.getGatePassAction().equals(GatePassType.CREATE.getStatus()) && gatePassMain.getGatePassStatus().equals(GatePassStatus.APPROVED.getStatus()))
					||dto.getGatePassType().equals(GatePassType.UNBLOCK.getStatus()) || dto.getGatePassType().equals(GatePassType.DEBLACKLIST.getStatus())
					) {
				
			int workFlowTypeId = workmenDao.getWorkFlowTYpe(gatePassMain.getUnitId());
			
			if(workFlowTypeId == WorkFlowType.AUTO.getWorkFlowTypeId() || dto.getGatePassType().equalsIgnoreCase(GatePassType.LOSTORDAMAGE.getStatus())) {
				dto.setGatePassStatus(GatePassStatus.APPROVED.getStatus());
				result = workmenDao.gatePassAction(dto);
				 if(null!=result) {
						GatePassStatusLogDto statusLog = new GatePassStatusLogDto();
						statusLog.setGatePassId(dto.getGatePassId());
						statusLog.setGatePassType(dto.getGatePassType());
						statusLog.setStatus(Integer.parseInt(dto.getGatePassStatus()));
						statusLog.setComments(dto.getComments());
						statusLog.setUpdatedBy(dto.getCreatedBy());
						workmenDao.saveGatePassStatusLog(statusLog);
					}
			}else {
					dto.setGatePassStatus(GatePassStatus.APPROVALPENDING.getStatus());
				 result = workmenDao.gatePassAction(dto);
				 if(null!=result) {
						GatePassStatusLogDto statusLog = new GatePassStatusLogDto();
						statusLog.setGatePassId(dto.getGatePassId());
						statusLog.setGatePassType(dto.getGatePassType());
						statusLog.setStatus(Integer.parseInt(dto.getGatePassStatus()));
						statusLog.setComments(dto.getComments());
						statusLog.setUpdatedBy(dto.getCreatedBy());
						workmenDao.saveGatePassStatusLog(statusLog);
					}
//				//get approvers for gatepass
//				 String approverType = null;
//				 String gatePassType=null;
//				 if(dto.getGatePassType().equalsIgnoreCase(GatePassType.CANCEL.getStatus())) {
//					 approverType="cancelapprover";
//					 gatePassType=GatePassType.CANCEL.getStatus();
//				 }else if(dto.getGatePassType().equalsIgnoreCase(GatePassType.BLACKLIST.getStatus())) {
//					 approverType="blacklistapprover";
//					 gatePassType=GatePassType.BLACKLIST.getStatus();
//				 }else if(dto.getGatePassType().equalsIgnoreCase(GatePassType.BLOCK.getStatus())) {
//					 approverType="blockapprover";
//					 gatePassType=GatePassType.BLOCK.getStatus();
//				 }else if(dto.getGatePassType().equalsIgnoreCase(GatePassType.UNBLOCK.getStatus())) {
//					 approverType="unblockapprover";
//					 gatePassType=GatePassType.UNBLOCK.getStatus();
//				 }else if(dto.getGatePassType().equalsIgnoreCase(GatePassType.DEBLACKLIST.getStatus())) {
//					 approverType="deblacklistapprover";
//					 gatePassType=GatePassType.DEBLACKLIST.getStatus();
//				 }
//				List<MasterUser> approversList = workmenDao.getApproversForGatePassAction(gatePassMain.getCreatedBy(),approverType);
//				
//				if(null!=approversList && approversList.size()>0){
//					for(MasterUser mu :approversList) {
//						mu.setStatus(gatePassType);
//						if(mu.getRoleName().equalsIgnoreCase(UserRole.EIC.getName())) {
//								mu.setIndex(1);
//								
//						}else if(mu.getRoleName().equalsIgnoreCase(UserRole.SECURITY.getName())){
//								mu.setIndex(3);
//							
//						}else if(mu.getRoleName().equalsIgnoreCase(UserRole.HR.getName())) {
//								mu.setIndex(2);
//							
//						}
//					}
//					workmenDao.saveGatePassApprover(dto.getGatePassId(), approversList, gatePassMain.getCreatedBy());
//					
//				}  
				 
 			}//end workflow type
			}else {
				//other action performed on gatepass
				if(gatePassMain.getGatePassAction().equals(GatePassType.CANCEL.getStatus())) {
					result="Cancellation request already initiated for gatepassid :"+dto.getGatePassId();
				}else if(gatePassMain.getGatePassAction().equals(GatePassType.BLOCK.getStatus())) {
					result="Block request already initiated for gatepassid :"+dto.getGatePassId();
				}else if(gatePassMain.getGatePassAction().equals(GatePassType.UNBLOCK.getStatus())) {
					result="Unblock request already initiated for gatepassid :"+dto.getGatePassId();
				}else if(gatePassMain.getGatePassAction().equals(GatePassType.BLACKLIST.getStatus())) {
					result="Blacklist request already initiated for gatepassid :"+dto.getGatePassId();
				}else if(gatePassMain.getGatePassAction().equals(GatePassType.DEBLACKLIST.getStatus())) {
					result="Deblacklist request already initiated for gatepassid :"+dto.getGatePassId();
				}else if(gatePassMain.getGatePassAction().equals(GatePassType.LOSTORDAMAGE.getStatus())) {
					result="Lost or Damage request already initiated for gatepassid :"+dto.getGatePassId();
				}
				
			}
			
			
		}catch(Exception e) {
			
		}
		
		return result;
	}
	@Override
	public List<GatePassListingDto> getGatePassActionListingDetails(String unitId,String deptId,String userId, String gatePassTypeId,String previousGatePassAction) {
		return workmenDao.getGatePassActionListingDetails(unitId,deptId,userId,gatePassTypeId,previousGatePassAction);
	}
	@Override
	public List<GatePassListingDto> getWorkmenDetailBasedOnId(String gatePassId) {
		// TODO Auto-generated method stub
		return workmenDao.getWorkmenDetailBasedOnId(gatePassId);
	}
	
	private String getDOT(GatePassMain gatePassMain) {
		String dot=null;
		int dotTypeId = gatePassMain.getDotType();
		 Map<String, LocalDate>  validityDates = workmenDao.getValidityDates(gatePassMain.getWorkorder(), gatePassMain.getWcEsicNo());
	        
		int retirementAge = 60; // Define the retirement age
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfBirth = LocalDate.parse(gatePassMain.getDateOfBirth(), formatter);
        LocalDate retirementDate = dateOfBirth.plusYears(retirementAge);
        LocalDate woDate = validityDates.get("WO");
        LocalDate wcDate = validityDates.get("WC");
        
		if(dotTypeId == DotType.LLWCWO.getStatus()) {
			
		}else if(dotTypeId == DotType.LLWC.getStatus()) {
			
		}else if(dotTypeId == DotType.LLWO.getStatus()) {
			
		}else if(dotTypeId == DotType.WCWO.getStatus()) {
			List<LocalDate> dates = Arrays.asList(retirementDate, woDate, wcDate);
	        LocalDate earliestDate = dates.stream()
	             .filter(date -> date != null) 
	             .min(LocalDate::compareTo)    
	             .orElse(null); 
	         dot = earliestDate != null ? earliestDate.format(formatter) : "No valid date available";

		}else if(dotTypeId == DotType.LL.getStatus()) {
			
		}else if(dotTypeId == DotType.WC.getStatus()) {
			List<LocalDate> dates = Arrays.asList(retirementDate, wcDate);
	        LocalDate earliestDate = dates.stream()
	             .filter(date -> date != null) 
	             .min(LocalDate::compareTo)    
	             .orElse(null);
	        dot = earliestDate != null ? earliestDate.format(formatter) : "No valid date available";
		}else if(dotTypeId == DotType.WO.getStatus()) {
			List<LocalDate> dates = Arrays.asList(retirementDate, woDate);
	        LocalDate earliestDate = dates.stream()
	             .filter(date -> date != null) 
	             .min(LocalDate::compareTo)    
	             .orElse(null); 
	        dot = earliestDate != null ? earliestDate.format(formatter) : "No valid date available";
		}
		
		return dot;
	}
	@Override
	public List<ApproverStatusDTO> getApprovalDetails(String gatePassId) {
		// TODO Auto-generated method stub
		return workmenDao.getApprovalDetails( gatePassId);
	}
	
	@Override
	public List<Contractor> getAllContractorForAdmin(String unitId) {
		
		return workmenDao.getAllContractorForAdmin(unitId);
	}

}
