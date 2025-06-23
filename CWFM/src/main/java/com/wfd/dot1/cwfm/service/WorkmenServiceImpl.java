package com.wfd.dot1.cwfm.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.wfd.dot1.cwfm.dao.WorkmenDao;
import com.wfd.dot1.cwfm.dto.ApproveRejectGatePassDto;
import com.wfd.dot1.cwfm.dto.ApproverStatusDTO;
import com.wfd.dot1.cwfm.dto.GatePassActionDto;
import com.wfd.dot1.cwfm.dto.GatePassListingDto;
import com.wfd.dot1.cwfm.dto.GatePassStatusLogDto;
import com.wfd.dot1.cwfm.enums.DotType;
import com.wfd.dot1.cwfm.enums.GatePassStatus;
import com.wfd.dot1.cwfm.enums.GatePassType;
import com.wfd.dot1.cwfm.enums.WorkFlowType;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.ContractWorkmenExportDto;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.GatePassMain;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.Skill;
import com.wfd.dot1.cwfm.pojo.Trade;
import com.wfd.dot1.cwfm.pojo.WageDetailsDto;
import com.wfd.dot1.cwfm.pojo.Workorder;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

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
		String transactionId =null;
		try {
			int workFlowTypeId = workmenDao.getWorkFlowTYpeNew(gatePassMain.getPrincipalEmployer(),GatePassType.CREATE.getStatus());
			gatePassMain.setWorkFlowType(workFlowTypeId);
			
			int dotTypeId = workmenDao.getDOTTYpe(gatePassMain.getPrincipalEmployer());
			gatePassMain.setDotType(dotTypeId);
			
			String dot = this.getDOT(gatePassMain);
			gatePassMain.setDot(dot);
			gatePassMain.setGatePassAction(GatePassType.CREATE.getStatus());
			if(workFlowTypeId == WorkFlowType.AUTO.getWorkFlowTypeId()) {
				gatePassMain.setGatePassStatus(GatePassStatus.APPROVED.getStatus());
				 transactionId = workmenDao.saveGatePass(gatePassMain); 
				 String gatePassId = workmenDao.updateGatePassIdByTransactionId(transactionId);
				gatePassMain.setGatePassId(gatePassId);
				GatePassStatusLogDto dto =new GatePassStatusLogDto();
				dto.setTransactionId(transactionId);
				dto.setGatePassId(gatePassId);
				dto.setGatePassType(GatePassType.CREATE.getStatus());
				dto.setStatus(Integer.parseInt(GatePassStatus.APPROVED.getStatus()));
				dto.setComments(gatePassMain.getComments());
				dto.setUpdatedBy(gatePassMain.getUserId());
				workmenDao.saveGatePassStatusLog(dto);
				return transactionId;
			}else {
				gatePassMain.setGatePassStatus(GatePassStatus.APPROVALPENDING.getStatus());
				transactionId = workmenDao.saveGatePass(gatePassMain);
				gatePassMain.setGatePassId(" ");
				GatePassStatusLogDto dto =new GatePassStatusLogDto();
				dto.setTransactionId(transactionId);
				dto.setGatePassId(" ");
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
		return transactionId;
	}
	
	@Override
	public List<GatePassListingDto> getGatePassListingDetails(String unitId,String deptId,String userId,String gatePassTypeId,String type) {
		return workmenDao.getGatePassListingDetails(unitId,deptId,userId,gatePassTypeId,type);
	}
	@Override
	public List<GatePassListingDto> getGatePassListingForApprovers(String unitId ,String deptId,MasterUser user,String gatePassTypeId,String type) {
		int workFlowTypeId = workmenDao.getWorkFlowTYpeNew(unitId,gatePassTypeId);
			return workmenDao.getGatePassListingForApprovers(user.getRoleId(),workFlowTypeId,gatePassTypeId,deptId,unitId,type);
	}
	@Override
	public GatePassMain getIndividualContractWorkmenDetails(String transactionId) {
		return workmenDao.getIndividualContractWorkmenDetails(transactionId);
	}
	@Override
	public List<CmsGeneralMaster> getAllGeneralMasterForGatePass(GatePassMain gatePassMainObj) {
		return workmenDao.getAllGeneralMastersForGatePass(gatePassMainObj);
	}
	@Override
	public String approveRejectGatePass(ApproveRejectGatePassDto dto) {
		
		GatePassMain gpm = workmenDao.getIndividualContractWorkmenDetailsByTransId(dto.getTransactionId());
		String result=null;
		//if(gpm.getGatePassAction().equals(GatePassType.CREATE.getStatus()) && gpm.getGatePassStatus().equals(GatePassStatus.APPROVED.getStatus())) {
		 result = workmenDao.approveRejectGatePass(dto);
		GatePassStatusLogDto statusLog = new GatePassStatusLogDto();
		statusLog.setTransactionId(dto.getTransactionId());
		statusLog.setGatePassId(dto.getGatePassId());
		statusLog.setGatePassType(dto.getGatePassType());
		statusLog.setStatus(Integer.parseInt(dto.getStatus()));
		statusLog.setComments(dto.getComments());
		statusLog.setUpdatedBy(dto.getApproverId());
		workmenDao.saveGatePassStatusLog(statusLog);
		boolean status=false;
		if(dto.getStatus().equals(GatePassStatus.REJECTED.getStatus())) {
			if(gpm.getGatePassAction().equals(GatePassType.CREATE.getStatus())) {
			status = workmenDao.updateGatePassMainStatusByTransactionId(dto.getTransactionId(),dto.getStatus());
			}else {
			//rollback status and type to create
			status = workmenDao.updateGatePassMainStatusAndType(dto.getGatePassId(),GatePassStatus.APPROVED.getStatus(),GatePassType.CREATE.getStatus());
			}
		}else {
			boolean isLastApprover=false;
			int workFlowTypeId = workmenDao.getWorkFlowTYpeByTransactionId(dto.getTransactionId(),dto.getGatePassType());
			if(workFlowTypeId==WorkFlowType.SEQUENTIAL.getWorkFlowTypeId()) {
				 isLastApprover = workmenDao.isLastApprover(dto.getApproverRole(),String.valueOf(dto.getGatePassType()));
			}else if(workFlowTypeId==WorkFlowType.PARALLEL.getWorkFlowTypeId()) {
				if(GatePassType.CREATE.getStatus().equals(dto.getGatePassType())) {
				 isLastApprover = workmenDao.isLastApproverForParallel(String.valueOf(dto.getGatePassType()),dto.getTransactionId(),dto.getRoleId());
				}else {
				 isLastApprover = workmenDao.isLastApproverForParallelGatePassAction(String.valueOf(dto.getGatePassType()),dto.getGatePassId(),dto.getRoleId());
				}
			}
			
			//check if last approver, if last approver change the status of gate pass main to approved
			
			if(isLastApprover) {
				if(dto.getGatePassType().equals(GatePassType.DEBLACKLIST.getStatus()) || dto.getGatePassType().equals(GatePassType.UNBLOCK.getStatus())) {
					status = workmenDao.updateGatePassMainStatusAndType(dto.getGatePassId(),dto.getStatus(),GatePassType.CREATE.getStatus());
				//rollback status and type to create
				}else {
					String gatePassId=dto.getGatePassId();
					if(dto.getGatePassType().equals(GatePassType.CREATE.getStatus())) {
						//create gatepassId on final approval
						gatePassId= workmenDao.updateGatePassIdByTransactionId(dto.getTransactionId());
					}
					
				status = workmenDao.updateGatePassMainStatus(gatePassId,dto.getStatus());
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
			 GatePassMain gatePassMain = workmenDao.getIndividualContractWorkmenDetailsByGatePassId(dto.getGatePassId());
			if((gatePassMain.getGatePassAction().equals(GatePassType.CREATE.getStatus()) && gatePassMain.getGatePassStatus().equals(GatePassStatus.APPROVED.getStatus()))
					||dto.getGatePassType().equals(GatePassType.UNBLOCK.getStatus()) || dto.getGatePassType().equals(GatePassType.DEBLACKLIST.getStatus())
					) {
				
			int workFlowTypeId = workmenDao.getWorkFlowTYpeNew(gatePassMain.getUnitId(),dto.getGatePassType());
			
			if(workFlowTypeId == WorkFlowType.AUTO.getWorkFlowTypeId() || dto.getGatePassType().equalsIgnoreCase(GatePassType.LOSTORDAMAGE.getStatus())) {
				dto.setGatePassStatus(GatePassStatus.APPROVED.getStatus());
				result = workmenDao.gatePassAction(dto);
				if(dto.getGatePassType().equals(GatePassType.DEBLACKLIST.getStatus()) || dto.getGatePassType().equals(GatePassType.UNBLOCK.getStatus())) {
					 workmenDao.updateGatePassMainStatusAndType(dto.getGatePassId(),dto.getGatePassStatus(),GatePassType.CREATE.getStatus());
				//rollback status and type to create
				}else {
					String gatePassId=dto.getGatePassId();
					
					
				 workmenDao.updateGatePassMainStatus(gatePassId,dto.getGatePassStatus());
				}
				 if(null!=result) {
						GatePassStatusLogDto statusLog = new GatePassStatusLogDto();
						statusLog.setGatePassId(dto.getGatePassId());
						statusLog.setTransactionId(dto.getTransactionId());;
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
						statusLog.setTransactionId(dto.getTransactionId());
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
	public List<ApproverStatusDTO> getApprovalDetails(String transactionId,String unitId) {
		// TODO Auto-generated method stub
		return workmenDao.getApprovalDetails( transactionId,unitId);
	}
	
	@Override
	public List<Contractor> getAllContractorForAdmin(String unitId) {
		
		return workmenDao.getAllContractorForAdmin(unitId);
	}
	
	@Override
	public String draftGatePass(GatePassMain gatePassMain) {
		String transactionId =null;
		try {
			
				gatePassMain.setGatePassStatus(GatePassStatus.DRAFT.getStatus());
				gatePassMain.setGatePassId(" ");
				gatePassMain.setGatePassAction(GatePassType.CREATE.getStatus());
				transactionId = workmenDao.draftGatePass(gatePassMain); 
				return transactionId;
		}catch(Exception e) {
			
		}
		return transactionId;
	}
	
	@Override
	public String generateTransactionId() {
		return workmenDao.generateTransationId();
	}
	
	@Override
	public GatePassMain getIndividualContractWorkmenDraftDetails(String transactionId) {
		return workmenDao.getIndividualContractWorkmenDraftDetails(transactionId);
	}
	@Override
	public int getWorkFlowTYpe(String principalEmployer) {
		// TODO Auto-generated method stub
		return workmenDao.getWorkFlowTYpe(principalEmployer);
	}

	@Override
	public List<GatePassListingDto> getRenewListingDetails(String userId,String gatePassTypeId,String gatePassStatus,String deptId,String unitId) {
		return workmenDao.getRenewListingDetails( userId, gatePassTypeId, gatePassStatus, deptId, unitId) ;
	}
	
	@Override
	public String renewGatePass(GatePassMain gatePassMain) {
		String transactionId =gatePassMain.getTransactionId();
		try {
			int workFlowTypeId = workmenDao.getWorkFlowTYpe(gatePassMain.getPrincipalEmployer());
			gatePassMain.setWorkFlowType(workFlowTypeId);
			
			int dotTypeId = workmenDao.getDOTTYpe(gatePassMain.getPrincipalEmployer());
			gatePassMain.setDotType(dotTypeId);
			
			String dot = this.getDOT(gatePassMain);
			gatePassMain.setDot(dot);
			gatePassMain.setGatePassAction(GatePassType.RENEW.getStatus());
			if(workFlowTypeId == WorkFlowType.AUTO.getWorkFlowTypeId()) {
				gatePassMain.setGatePassStatus(GatePassStatus.APPROVED.getStatus());
				 transactionId = workmenDao.renewGatePass(gatePassMain); 
				 //String gatePassId = workmenDao.updateGatePassIdByTransactionId(transactionId);
				//gatePassMain.setGatePassId(gatePassId);
				GatePassStatusLogDto dto =new GatePassStatusLogDto();
				dto.setTransactionId(transactionId);
				dto.setGatePassId(gatePassMain.getGatePassId());
				dto.setGatePassType(GatePassType.RENEW.getStatus());
				dto.setStatus(Integer.parseInt(GatePassStatus.APPROVED.getStatus()));
				dto.setComments(gatePassMain.getComments());
				dto.setUpdatedBy(gatePassMain.getUserId());
				workmenDao.saveGatePassStatusLog(dto);
				return transactionId;
			}else {
				gatePassMain.setGatePassStatus(GatePassStatus.APPROVALPENDING.getStatus());
				transactionId = workmenDao.renewGatePass(gatePassMain);
				GatePassStatusLogDto dto =new GatePassStatusLogDto();
				dto.setTransactionId(transactionId);
				dto.setGatePassId(gatePassMain.getGatePassId());
				dto.setGatePassType(GatePassType.RENEW.getStatus());
				dto.setStatus(Integer.parseInt(GatePassStatus.APPROVED.getStatus()));
				dto.setComments(gatePassMain.getComments());
				dto.setUpdatedBy(gatePassMain.getUserId());
				workmenDao.saveGatePassStatusLog(dto);
	
				
			}
		}catch(Exception e) {
			
		}
		return transactionId;
	}
	
	@Override
	public List<GatePassListingDto> getGatePassActionListingForApprovers(String unitId ,String deptId,MasterUser user,String gatePassTypeId) {
		int workFlowTypeId = workmenDao.getWorkFlowTYpeNew(unitId,gatePassTypeId);
			return workmenDao.getGatePassActionListingForApprovers(user.getRoleId(),workFlowTypeId,gatePassTypeId,deptId,unitId);
	}

	
	public String getSurePassURL() {
	    return QueryFileWatcher.getQuery("SUREPASS_VERIFY_OTP_URL");
	}
    
    public String getToken() {
	    return QueryFileWatcher.getQuery("TOKEN");
	}
    
    public String getGenderIdFromCode(String code, List<CmsGeneralMaster> genderList) {
        for (CmsGeneralMaster gm : genderList) {
            if (code.equalsIgnoreCase("M") && gm.getGmName().equalsIgnoreCase("MALE")) {
                return String.valueOf(gm.getGmId());
            } else if (code.equalsIgnoreCase("F") && gm.getGmName().equalsIgnoreCase("FEMALE")) {
                return String.valueOf(gm.getGmId());
            }
        }
        return ""; // or null if not found
    }

    
	@Override
	public Map<String, Object> verifyOtpWithSurepass(String clientId, String otp) {
        String url =getSurePassURL();
        String bearerToken = getToken();

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", bearerToken);

        Map<String, String> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("otp", otp);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            Map<String, Object> responseBody = response.getBody();
            Map<String, Object> data = (Map<String, Object>) responseBody.get("data");

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("fullName", data.get("full_name"));
            result.put("dob", data.get("dob"));
            
        	List<CmsGeneralMaster> gmList = this.getAllGeneralMaster();
            Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList.stream()
                    .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

            List<CmsGeneralMaster> genderList = groupedByGmType.get("GENDER");

            String genderCode = (String) data.get("gender"); // "M" or "F"
            String genderId = getGenderIdFromCode(genderCode, genderList);

            result.put("gender", genderId); // send this to frontend

            //result.put("gender", data.get("gender"));

            // Construct address string
            Map<String, Object> addr = (Map<String, Object>) data.get("address");
            String address = String.join(", ",
                    Objects.toString(addr.get("house"), ""),
                    Objects.toString(addr.get("street"), ""),
                    Objects.toString(addr.get("loc"), ""),
                    Objects.toString(addr.get("dist"), ""),
                    Objects.toString(addr.get("state"), ""));

            result.put("address", address);
            return result;

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "OTP verification failed: " + e.getStatusText());
            return error;
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Unexpected error: " + e.getMessage());
            return error;
        }
    }
	@Override
	public boolean isAadhaarExists(String aadhaarNumber) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isAadharDuplicate(String aadharNumber) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ContractWorkmenExportDto> getContractWorkmenExportData(String unitId) {
		// TODO Auto-generated method stub
		return workmenDao.getContractWorkmenExportData(unitId);
	}
	@Override
	public boolean isAadharExists(String aadharNumber,String transactionId) {
		// TODO Auto-generated method stub
		return workmenDao.isAadharExists(aadharNumber,transactionId);
	}

	/*
	 * @Override public boolean checkAadharDuplicate(String aadhaarNumber) { return
	 * workmenDao.isAadharExists(aadhaarNumber); }
	 */
	
	@Override
	public List<WageDetailsDto> getWageMasterExportData(String unitId) {
		// TODO Auto-generated method stub
		return workmenDao.getWageMasterExportData(unitId);
	}
}
