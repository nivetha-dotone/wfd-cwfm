package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.pojo.CMSContrPemm;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.Workorder;

public interface ContractorDao {

	public Contractor getContractorById(String contractorId);

	public List<Workorder> getWorkOrdersByContractorIdAndUnitId(String contractorId, String unitId);

	public CMSContrPemm getMappingByContractorIdAndUnitId(String contractorId, String principalEmployerId);

	

	public List<CmsContractorWC> getcontrsByContractorIdAndUnitIdAndLicenseType(String contractorId,
			String principalEmployerId, String string);

	public List<CmsContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseTypes(String contractorId,
			String principalEmployerId, List<String> licenseTypes);


}