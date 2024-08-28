package com.wfd.dot1.cwfm.service;

import java.util.List;

import com.wfd.dot1.cwfm.entity.CMSContrPemm;

public interface ContrPemmService {
	 List<CMSContrPemm> getMappingsByContractorId(long contractorId);
	 List<CMSContrPemm> getMappingsByContractorIdAndUnitId(long contractorId, long unitId);
	CMSContrPemm getMappingByContractorIdAndUnitId(Long id, Long principalEmployerId);
}
