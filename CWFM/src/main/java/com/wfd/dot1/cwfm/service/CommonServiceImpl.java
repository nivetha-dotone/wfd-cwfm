package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.CommonDao;
import com.wfd.dot1.cwfm.entity.CMSContractorWC;
import com.wfd.dot1.cwfm.entity.CMSGMType;
import com.wfd.dot1.cwfm.entity.CMSPerson;
import com.wfd.dot1.cwfm.entity.CMSWorkOrder;
import com.wfd.dot1.cwfm.entity.GeneralMaster;
import com.wfd.dot1.cwfm.entity.State;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonDao commonDAO;

    @Override
    public State getStateById(int stateId) {
        return commonDAO.getStateById(stateId);
    }

    @Override
    public List<State> getAllStates() {
        return commonDAO.getAllStates();
    }
    @Override
	public List<CMSContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseType(Long id, Long principalEmployerId,
			String string) {
		return commonDAO.getMappingsByContractorIdAndUnitIdAndLicenseType(id, principalEmployerId, string);
	}

	@Override
	public List<CMSContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseTypes(Long id,
			Long principalEmployerId, List<String> licenseTypes) {
		return commonDAO.getMappingsByContractorIdAndUnitIdAndLicenseTypes(id, principalEmployerId, licenseTypes);
	}
    // Implement other methods by delegating to DAO layer

	@Override
	public List<CMSWorkOrder> getWorkOrdersByContractorIdAndUnitId(Long id, Long principalEmployerId) {
		return commonDAO.getWorkOrdersByContractorIdAndUnitId(id, principalEmployerId);
	}
	@Override
    public CMSWorkOrder getWorkorderById(Long id) {
        return commonDAO.getWorkorderById(id);
    }

	@Override
	public CMSGMType findById(Long gmTypeId) {
		 return commonDAO.findById(gmTypeId);
	}

	@Override
	public List<CMSPerson> getAllPersons() {
		return commonDAO.getAllPersons();
	}

	@Override
	public List<CMSPerson> getAllPersonsByPrincipalEmployerAndContractor(Long principalEmployerId, Long contractorId) {
		// TODO Auto-generated method stub
		return commonDAO.getAllPersonsByPrincipalEmployerAndContractor(principalEmployerId,contractorId);
	}

	@Override
	public GeneralMaster findByGMId(Integer bloodGroupId) {
		// TODO Auto-generated method stub
		return commonDAO.findByGMId(bloodGroupId);
	}

	@Override
	public List<GeneralMaster> getGeneralMasterOptionsByName(String string) {
		// TODO Auto-generated method stub
		return commonDAO.getGeneralMasterOptionsByName(string);
	}
}