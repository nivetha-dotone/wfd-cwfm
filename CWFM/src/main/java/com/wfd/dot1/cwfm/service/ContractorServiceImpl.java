package com.wfd.dot1.cwfm.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.dao.BillVerificationDao;
import com.wfd.dot1.cwfm.dao.ContractorDao;
import com.wfd.dot1.cwfm.dto.ApproveRejectContRenewDto;
import com.wfd.dot1.cwfm.dto.CMSWageCostDTO;
import com.wfd.dot1.cwfm.dto.RenewalDTO;
import com.wfd.dot1.cwfm.dto.RenewalDocumentDTO;
import com.wfd.dot1.cwfm.enums.GatePassStatus;
import com.wfd.dot1.cwfm.enums.WorkFlowType;
import com.wfd.dot1.cwfm.pojo.BillStatusLogDto;
import com.wfd.dot1.cwfm.pojo.CMSContrPemm;
import com.wfd.dot1.cwfm.pojo.CMSContractorRegistrationLLWC;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.ContractorComplianceDto;
import com.wfd.dot1.cwfm.pojo.ContractorRegistration;
import com.wfd.dot1.cwfm.pojo.ContractorRegistrationPolicy;
import com.wfd.dot1.cwfm.pojo.ContractorRenewal;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.Workorder;
@Service
public class ContractorServiceImpl implements ContractorService{

	@Autowired
	ContractorDao contrDao;
	
	@Override
	public Contractor getContractorById(String contractorId) {
		// TODO Auto-generated method stub
		return contrDao.getContractorById(contractorId);
	}
	@Override
	public List<Workorder> getWorkOrdersByContractorIdAndUnitId(String contractorId, String unitId) {
		// TODO Auto-generated method stub
		return contrDao.getWorkOrdersByContractorIdAndUnitId(contractorId,unitId);
	}
	@Override
	public CMSContrPemm getMappingByContractorIdAndUnitId(String contractorId, String principalEmployerId) {
		// TODO Auto-generated method stub
		return contrDao.getMappingByContractorIdAndUnitId(contractorId,principalEmployerId);
	}
	@Override
	public List<CmsContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseType(String contractorId,
			String principalEmployerId, String string) {
		// TODO Auto-generated method stub
		return contrDao.getcontrsByContractorIdAndUnitIdAndLicenseType( contractorId,
				 principalEmployerId,  string);
	}
	@Override
	public List<CmsContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseTypes(String contractorId,
			String principalEmployerId, List<String> licenseTypes) {
		// TODO Auto-generated method stub
		return contrDao.getMappingsByContractorIdAndUnitIdAndLicenseTypes( contractorId,
				 principalEmployerId,  licenseTypes);
	}
	@Override
	public String saveReg(ContractorRegistration contreg) {
		// TODO Auto-generated method stub
		contreg.setStatus("1");
		return contrDao.saveReg(contreg);
	}
	@Override
	public List<ContractorRegistration> getContractorRegistrationList(String userId) {
		// TODO Auto-generated method stub
		return contrDao.getContractorRegistrationList(userId);
	}
	
	@Override
	public List<ContractorRegistration> getContractorRenewList(String userId) {
		// TODO Auto-generated method stub
		return contrDao.getContractorRenewList(userId);
	}

	@Override
	public ContractorRenewal getContractorDetails(String contractorRenewId) {
		// TODO Auto-generated method stub
		return contrDao.getContractorDetails(contractorRenewId);
	}
	@Override
	 public ContractorRegistration viewContractorDetails( String contractorregId) {
		// TODO Auto-generated method stub
	        return contrDao.viewContractorDetails(contractorregId);
	    }
	@Override
	public ContractorRegistration getContractorViewDetails(String contractorregId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<ContractorRegistration> getContractorRenewalList(String userId) {
		// TODO Auto-generated method stub
		return contrDao.getContractorRenewalList(userId);
	}
	@Override
	 public List<ContractorRegistrationPolicy> viewContractorAddDetails( String contractorregId) {
		// TODO Auto-generated method stub
	        return contrDao.viewContractorAddDetails(contractorregId);
	    }
	@Override
	 public ContractorRenewal viewContractorRenewDetails( String contractorRenewId) {
		// TODO Auto-generated method stub
	        return contrDao.viewContractorRenewDetails(contractorRenewId);
	    }
	@Override
	 public List<ContractorRenewal> viewContractorRenewAddDetails( String contractorRenewId) {
		// TODO Auto-generated method stub
	        return contrDao.viewContractorRenewAddDetails(contractorRenewId);
	    }
	@Override
	 public List<MasterUser> getRoleList( String userId) {
		// TODO Auto-generated method stub
	        return contrDao.getRoleList(userId);
	    }
	@Override
	public String saveRole(MasterUser user) {
		// TODO Auto-generated method stub
		return contrDao.saveRole(user);
	}
	@Override
	public String saveRenew(ContractorRenewal contrenew) {
		// TODO Auto-generated method stub
		return contrDao.saveRenew(contrenew);
	}
	@Override
    public String generateUniqueContractorId() {
        Random random = new Random();
        String contractorregId;
        do {
            // Generate a 9-digit number starting with '9'
        	contractorregId = "9" + (100000000 + random.nextInt(900000000));
        } while (contrDao.checkIfIdExists(contractorregId)); // Ensure uniqueness
        return contractorregId;
    }
	@Override
	public String generateContractorRegistrationId() {
		return contrDao.generateContractorRegistrationId();
	}
	@Override
	public List<Contractor> getAllContractorForReg(String unitId) {
		return contrDao.getAllContractorForReg(unitId);
	}
	@Override
	public Contractor getAllContractorDetailForReg(String unitId, String contractorId) {
		
		return contrDao.getAllContractorDetailForReg(unitId,contractorId);
	}
	@Override
	public void savePolicies(List<ContractorRegistrationPolicy> policies,ContractorRegistration contreg) {
		 contrDao.savePolicies(policies,contreg);
		
	}
	@Override
	public List<Workorder> getAllWorkordersBasedOnPEAndContractor(String unitId, String contractorId) {
		// TODO Auto-generated method stub
		return contrDao.getAllWorkordersBasedOnPEAndContractor(unitId,contractorId);
	}
	@Override
	public List<ContractorRegistration> getContractorMasterExportData(String unitId) {
		// TODO Auto-generated method stub
		return contrDao.getContractorMasterExportData(unitId);
	}
	@Override
	public List<ContractorComplianceDto> getContractorComplianceExportData(String unitId) {
		// TODO Auto-generated method stub
		return contrDao.getContractorComplianceExportData(unitId);
	}
	
	@Override
	public ContractorRegistration getAllContractorDetailForRenewal(String unitId, String contractorId) {
		
		return contrDao.getAllContractorDetailForRenewal(unitId,contractorId);
	}
	
	 private static final String ROOT_DIRECTORY = "D:/wfd_cwfm/contractor_docs/";
	 public String uploadDocuments( MultipartFile aadharFile,
			 String userId,
			 String contractorRegId) {

		 // Create directory path
		 String directoryPath =ROOT_DIRECTORY + userId + "/"+contractorRegId+"/";

		 try {
			 // Ensure the directory exists, if not create it
			 Path path = Paths.get(directoryPath);
			 if (!Files.exists(path)) {
				 Files.createDirectories(path);
			 }

			 // Save Aadhar PDF
			 if (!aadharFile.isEmpty()) {
				 String aadharFilePath = directoryPath +aadharFile.getOriginalFilename();
				 saveFile(aadharFile, aadharFilePath);
			 }

			 

			 // Return success message
			 return "success";

		 } catch (IOException e) {
			 e.printStackTrace();
			 return "failed";
		 }
	 }
	 private void saveFile(MultipartFile file, String path) throws IOException {
	        byte[] bytes = file.getBytes();
	        Path filePath = Paths.get(path);
	        Files.write(filePath, bytes);
	    }
	 @Autowired
	 BillVerificationDao billDao;
	@Override
	public void saveRenewal(String jsonData, MultipartFile aadharFile, MultipartFile panFile,
			List<MultipartFile> attachments, String username) {
		ObjectMapper mapper = new ObjectMapper();
        ContractorRegistration reg=new ContractorRegistration();
        RenewalDTO renewal = new RenewalDTO();
        int workFlowTypeId=billDao.getWorkflowType("Contractor", String.valueOf(reg.getPrincipalEmployer()));
		if(workFlowTypeId == WorkFlowType.AUTO.getWorkFlowTypeId()) {
			reg.setStatus(GatePassStatus.APPROVED.getStatus());
		}else {
			reg.setStatus(GatePassStatus.APPROVALPENDING.getStatus());
		}
		try {
			//contreg = mapper.readValue(jsonData, ContractorRegistration.class);
			  renewal = mapper.readValue(jsonData, RenewalDTO.class);
			  reg.setContractorregId(String.valueOf(renewal.getContractorregId()));
			  reg.setVendorCode(renewal.getVendorCode());
		        reg.setContractorId(String.valueOf(renewal.getContractorId()));
		        reg.setContractorName(renewal.getContractorName());
		        reg.setPrincipalEmployer(renewal.getPrincipalEmployer());
		        reg.setManagerName(renewal.getManagerName());
		        reg.setTotalStrength(renewal.getTotalStrength());
		        reg.setRcMaxEmp(renewal.getRcMaxEmp());
		        reg.setNatureOfWork(renewal.getNatureOfWork());
		        reg.setLocofWork(renewal.getLocofWork());
		        reg.setPfNum(renewal.getPfNum());
		        reg.setRcVerified(renewal.getRcVerified());
		        reg.setMainContractor(renewal.getMainContractor());
		        reg.setContractType(renewal.getContractType());
		        reg.setContractFrom(renewal.getContractFrom());
		        reg.setContractTo(renewal.getContractTo());
		        reg.setEmail(renewal.getEmail());
		        reg.setMobile(renewal.getMobile());
		        reg.setAadhar(renewal.getAadhar());
		        reg.setGst(renewal.getGst());
		        reg.setAddress(renewal.getAddress());
		        reg.setPfApplyDate(renewal.getPfApplyDate());
		        reg.setAadharDoc(aadharFile.getOriginalFilename());
				reg.setPanDoc(panFile.getOriginalFilename());
				reg.setCreatedBy(username);
				reg.setRequestType("Renew");

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
		
         String regId =contrDao.saveReg(reg);

         
         if (regId != null) {
        	 uploadDocuments(aadharFile,username,regId);
        	 uploadDocuments(panFile,username,regId);
             List<ContractorRegistrationPolicy> policies = new ArrayList<ContractorRegistrationPolicy>();
             List<RenewalDocumentDTO> dto= renewal.getRenewalDocuments();

             // Set regId and file details in each policy
             for (int i = 0; i < dto.size(); i++) {
            	 RenewalDocumentDTO d = dto.get(i);
            	 
            	 ContractorRegistrationPolicy policy = new ContractorRegistrationPolicy();
            	 policy.setContractorRegId(regId);
            	 policy.setDocumentNumber(d.getDocumentNumber());
            	 policy.setDocumentType(d.getDocumentType());
            	 policy.setCoverage(Integer.parseInt(d.getCoverage()));
            	 policy.setValidFrom(d.getValidFrom());
            	 policy.setValidTo(d.getValidTo());
            	 policy.setPanIndia(d.isPanIndia());
            	 policy.setSubApplicable(d.isSubApplicable());
            	 
                 MultipartFile file = attachments.get(i);
                 if (file != null && !file.isEmpty()) {
                     policy.setFileName(file.getOriginalFilename());
                     // Optionally: save file bytes or store to disk
                     uploadDocuments(file,username,regId);
                 } else {
                     policy.setFileName(null);
                 }
                 policies.add(policy);
             }

             this.savePolicies(policies, reg);
             
        
        
		
	}else {
		//renewal  failed
	}
	}
	@Override
	public ContractorRegistration getAllAvailableWoAndLicense(String unitId, String contractorId, String contractorCode,
			String regId) {
		
		return contrDao.getWOAndLicense(contractorId,contractorCode,unitId,regId);
	}
	@Override
	public ContractorRegistration getContractorRegistration(String contractorRegId) {
        return contrDao.getContractorRegistration(contractorRegId);
    }
	@Override
    public List<ContractorRegistrationPolicy> getPolicies(String contractorRegId) {
        return contrDao.getPoliciesByContractorRegId(contractorRegId);
    }
	@Override
    public List<CMSContractorRegistrationLLWC> getLLWC(String contractorRegId) {
        return contrDao.getLLWCByContractorRegId(contractorRegId);
    }
	@Override
	public List<ContractorRegistration> getContRenewList(String userId, String deptId, String principalEmployerId) {
		// TODO Auto-generated method stub
		return contrDao.getContRenewList(userId,deptId,principalEmployerId);
	}
	@Override
	public List<ContractorRegistration> getContRenewListForApprovers(String principalEmployerId, String deptId,
			MasterUser user) {
		int workFlowType=contrDao.getWorkflowType("CONTRACTOR RENEWAL", principalEmployerId);
		return contrDao.getContRenewListForApprovers(user.getRoleId(), workFlowType, deptId, principalEmployerId);
	}
	@Override
	public String approveRejectContRenew(ApproveRejectContRenewDto dto) {
		ContractorRegistration gpm = contrDao.getContractorRegistration(dto.getTransactionId());
		String result=null;
		 result = contrDao.approveRejectContRenew(dto);
		
		boolean status=false;
		if(dto.getStatus().equals(GatePassStatus.REJECTED.getStatus())) {
			status = contrDao.updateContStatusByTransactionId(dto.getTransactionId(),dto.getStatus());
		}else {
			boolean isLastApprover=false;
			int workFlowTypeId = contrDao.getWorkFlowTYpeByTransactionId(dto.getTransactionId());
			if(workFlowTypeId==WorkFlowType.SEQUENTIAL.getWorkFlowTypeId()) {
				 isLastApprover = contrDao.isLastApprover(dto.getApproverRole());
			}else if(workFlowTypeId==WorkFlowType.PARALLEL.getWorkFlowTypeId()) {
				 isLastApprover = contrDao.isLastApproverForParallel(dto.getTransactionId(),dto.getRoleId());
				
			}
			
			//check if last approver, if last approver change the status of gate pass main to approved
			
			if(isLastApprover) {
				status = contrDao.updateContStatusByTransactionId(dto.getTransactionId(),dto.getStatus());
				
			}
		}

		return result;
	}
}
