package com.wfd.dot1.cwfm.service;

import java.util.List;

import com.wfd.dot1.cwfm.entity.CMSContractorWC;

public interface ContractorWCService {
	List<CMSContractorWC> getWorkOrdersByContractorId(long contractorId);
    // Other methods
}
