package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.BillVerificationDao;
import com.wfd.dot1.cwfm.dto.CMSWageCostDTO;
import com.wfd.dot1.cwfm.dto.ChecklistItemDTO;
import com.wfd.dot1.cwfm.enums.GatePassStatus;
import com.wfd.dot1.cwfm.enums.WorkFlowType;
import com.wfd.dot1.cwfm.pojo.BillReportFile;
import com.wfd.dot1.cwfm.pojo.BillStatusLogDto;
import com.wfd.dot1.cwfm.pojo.BillVerification;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
@Service
public class BillVerificationServiceImpl implements BillVerificationService{

	@Autowired
	BillVerificationDao billDao;
	
	@Override
	public List<CMSWageCostDTO> getBillVerificationList(String userId, String deptId, String unitId) {
		// TODO Auto-generated method stub
		return billDao.getBillVerificationList(userId,deptId,unitId);
	}
	@Override
	 public BillVerification viewbillDetails( String transactionId) {
		// TODO Auto-generated method stub
	        return billDao.viewbillDetails(transactionId);
	    }
	@Override
	 public BillVerification viewbillReportsDetails( String transactionId) {
		// TODO Auto-generated method stub
	        return billDao.viewbillReportsDetails(transactionId);
	    }
	@Override
	 public BillVerification viewbillhrclearanceDetails( String transactionId) {
		// TODO Auto-generated method stub
	        return billDao.viewbillhrclearanceDetails(transactionId);
	    }
	@Override
	 public BillVerification viewbillprecomments( String transactionId) {
		// TODO Auto-generated method stub
	        return billDao.viewbillprecomments(transactionId);
	    }
	@Override
	 public BillVerification viewbilleicDetails( String transactionId) {
		// TODO Auto-generated method stub
	        return billDao.viewbilleicDetails(transactionId);
	    }
	
	@Override
    public String generateWCTransactionId() {
    	return billDao.generateWCTransactionId();
    }
	@Override
	public List<PrincipalEmployer> getPEDetailByUser(String userAccount) {
		// TODO Auto-generated method stub
		return billDao.getPEDetailByUser(userAccount);
	}
	@Override
	public String save(CMSWageCostDTO workflowData) {
		String result=null;
		int workFlowTypeId=billDao.getWorkflowType("Bill Verification", String.valueOf(workflowData.getUnitId()));
		if(workFlowTypeId == WorkFlowType.AUTO.getWorkFlowTypeId()) {
			workflowData.setStatus(Integer.parseInt(GatePassStatus.APPROVED.getStatus()));
			result= billDao.saveBill(workflowData);
			if(result!=null)
			{
				BillStatusLogDto dto =new BillStatusLogDto();
				dto.setTransactionId(String.valueOf(workflowData.getWcTransId()));
				dto.setStatus(workflowData.getStatus());
				dto.setComments(workflowData.getComments());
				dto.setUpdatedBy(workflowData.getCreatedBy());
				billDao.saveBillStatusLog(dto);
			}
		}else {
			workflowData.setStatus(Integer.parseInt(GatePassStatus.APPROVALPENDING.getStatus()));
			result= billDao.saveBill(workflowData);
			if(result!=null)
			{
				BillStatusLogDto dto =new BillStatusLogDto();
				dto.setTransactionId(String.valueOf(workflowData.getWcTransId()));
				dto.setStatus(workflowData.getStatus());
				dto.setComments(workflowData.getComments());
				dto.setUpdatedBy(workflowData.getCreatedBy());
				billDao.saveBillStatusLog(dto);
			}
		}
		return result;
	}
	@Override
	public List<CMSWageCostDTO> getBillVerificationListForApprovers(String principalEmployerId, String deptId,
			MasterUser user) {
		// TODO Auto-generated method stub
		int workFlowType=billDao.getWorkflowType("Bill Verification", principalEmployerId);
		
		return billDao.getBillVerificationListForApprovers( user.getRoleId(), workFlowType, deptId, principalEmployerId);
	}
	@Override
	public CMSWageCostDTO getIndividualBillDetails(String transactionId) {
		// TODO Auto-generated method stub
		return billDao.getIndividualBVRDetails(transactionId);
	}
	
	public void saveFile(BillReportFile b) {
		 billDao.saveFile(b);
	}
	
	public List<BillReportFile> findByTransactionIdAndType(String transactionId, String type){
		return billDao.findByTransactionIdAndType(Long.parseLong(transactionId), type);
	}
	
	@Override
	public void saveChecklist(List<ChecklistItemDTO> checklistItems, String wcTransId) {
		billDao.saveChecklist(checklistItems,  wcTransId);
	}

}
