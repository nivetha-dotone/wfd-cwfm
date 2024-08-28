package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.entity.CMSContractorWC;

public interface ContractorWCDAO {
	 List<CMSContractorWC> getWorkOrdersByContractorId(long contractorId);
}
