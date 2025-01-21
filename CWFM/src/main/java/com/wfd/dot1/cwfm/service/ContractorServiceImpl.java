package com.wfd.dot1.cwfm.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.ContractorDao;
import com.wfd.dot1.cwfm.pojo.CMSContrPemm;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.ContractorRegistration;
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
		return contrDao.saveReg(contreg);
	}
	@Override
	public List<ContractorRegistration> getContractorRegistrationList(String userId) {
		// TODO Auto-generated method stub
		return contrDao.getContractorRegistrationList(userId);
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
	 public List<ContractorRegistration> viewContractorAddDetails( String contractorregId) {
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
}
