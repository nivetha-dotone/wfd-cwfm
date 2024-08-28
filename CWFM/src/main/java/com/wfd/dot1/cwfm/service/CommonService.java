package com.wfd.dot1.cwfm.service;

import java.util.List;

import com.wfd.dot1.cwfm.entity.CMSContractorWC;
import com.wfd.dot1.cwfm.entity.CMSGMType;
import com.wfd.dot1.cwfm.entity.CMSPerson;
import com.wfd.dot1.cwfm.entity.CMSWorkOrder;
import com.wfd.dot1.cwfm.entity.GeneralMaster;
import com.wfd.dot1.cwfm.entity.State;

public interface CommonService {
	  State getStateById(int stateId);
	    List<State> getAllStates();
		List<CMSContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseType(Long id, Long principalEmployerId,
				String string);
		List<CMSContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseTypes(Long id, Long principalEmployerId,
				List<String> licenseTypes);
		List<CMSWorkOrder> getWorkOrdersByContractorIdAndUnitId(Long id, Long principalEmployerId);
		CMSWorkOrder getWorkorderById(Long id);
		CMSGMType findById(Long gmTypeId);
		List<CMSPerson> getAllPersons();
		List<CMSPerson> getAllPersonsByPrincipalEmployerAndContractor(Long principalEmployerId, Long contractorId);
		GeneralMaster findByGMId(Integer bloodGroupId);
		List<GeneralMaster> getGeneralMasterOptionsByName(String string);
}
