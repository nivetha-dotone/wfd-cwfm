package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.ContractorDAO;
import com.wfd.dot1.cwfm.entity.CMSContractor;
import com.wfd.dot1.cwfm.entity.CMSContractorWC;
@Service
public class ContractorServiceImpl implements ContractorService {

	 private ContractorDAO contractorDAO;

	    public ContractorServiceImpl(ContractorDAO contractorDAO) {
	        this.contractorDAO = contractorDAO;
	    }

	    @Override
	    public CMSContractor getContractorById(long contractorId) {
	        return contractorDAO.getContractorById(contractorId);
	    }

	    @Override
	    public List<CMSContractor> getAllContractors() {
	        return contractorDAO.getAllContractors();
	    }
	    @Override
	    public List<CMSContractor> getContractorsByPrincipalEmployerId(Long principalEmployerId) {
	        return contractorDAO.getContractorsByPrincipalEmployerId(principalEmployerId);
	    }
	    
	    @Override
	    public void addContractor(CMSContractor contractor) {
	        contractorDAO.addContractor(contractor);
	    }

	    @Override
	    public void updateContractor(CMSContractor contractor) {
	        contractorDAO.updateContractor(contractor);
	    }

	    @Override
	    public List<CMSContractor> searchContractor(String query) {
	        return contractorDAO.searchContractor(query);
	    }

		@Override
		public List<CMSContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseType(Long id, Long principalEmployerId,
				String string) {
			return contractorDAO.getMappingsByContractorIdAndUnitIdAndLicenseType(id, principalEmployerId, string);
		}

		@Override
		public List<CMSContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseTypes(Long id,
				Long principalEmployerId, List<String> licenseTypes) {
			return contractorDAO.getMappingsByContractorIdAndUnitIdAndLicenseTypes(id, principalEmployerId, licenseTypes);
		}
}
