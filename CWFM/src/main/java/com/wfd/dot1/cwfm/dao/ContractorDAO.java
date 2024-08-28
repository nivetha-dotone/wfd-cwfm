package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.entity.CMSContractor;
import com.wfd.dot1.cwfm.entity.CMSContractorWC;

public interface ContractorDAO {
    CMSContractor getContractorById(long contractorId);
    List<CMSContractor> getAllContractors();
    List<CMSContractor> getContractorsByPrincipalEmployerId(Long principalEmployerId);
    void addContractor(CMSContractor principalEmployer);
    void updateContractor(CMSContractor principalEmployer);
    List<CMSContractor> searchContractor(String query);
    List<CMSContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseType(Long id, Long principalEmployerId,
			String string);
    List<CMSContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseTypes(Long id, Long principalEmployerId,
			List<String> licenseTypes);
}
