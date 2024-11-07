package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.ContractorDao;
import com.wfd.dot1.cwfm.pojo.CMSContrPemm;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.Contractor;
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

}
