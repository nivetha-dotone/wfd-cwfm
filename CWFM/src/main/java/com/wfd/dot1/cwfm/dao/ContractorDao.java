package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.pojo.CMSContrPemm;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.ContractorRegistration;
import com.wfd.dot1.cwfm.pojo.ContractorRegistrationPolicy;
import com.wfd.dot1.cwfm.pojo.ContractorRenewal;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.Workorder;

public interface ContractorDao {

	public Contractor getContractorById(String contractorId);

	public List<Workorder> getWorkOrdersByContractorIdAndUnitId(String contractorId, String unitId);

	public CMSContrPemm getMappingByContractorIdAndUnitId(String contractorId, String principalEmployerId);

	

	public List<CmsContractorWC> getcontrsByContractorIdAndUnitIdAndLicenseType(String contractorId,
			String principalEmployerId, String string);

	public List<CmsContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseTypes(String contractorId,
			String principalEmployerId, List<String> licenseTypes);

	public String saveReg(ContractorRegistration contreg);

	public ContractorRenewal getContractorDetails(String contractorRenewId);
	
	public List<ContractorRegistration> getContractorRegistrationList();

	public List<ContractorRegistration> getContractorRegistrationList(String contractorregId);

	public ContractorRegistration viewContractorDetails(String contractorregId);

	public List<ContractorRegistration> getContractorRenewalList(String contractorregId);

	public List<ContractorRegistration> viewContractorAddDetails(String contractorregId);
	
	public ContractorRenewal viewContractorRenewDetails(String contractorRenewId);

	public List<ContractorRenewal> viewContractorRenewAddDetails(String contractorRenewId);

	public List<MasterUser> getRoleList(String userId);

	public String saveRole(MasterUser user);

	public String saveRenew(ContractorRenewal contrenew);

	public boolean checkIfIdExists(String contractorregId);

	public String generateContractorRegistrationId();

	public List<Contractor> getAllContractorForReg(String unitId);

	public Contractor getAllContractorDetailForReg(String unitId, String contractorId);

	public void savePolicies(List<ContractorRegistrationPolicy> policies,ContractorRegistration contreg);
	   
	}


