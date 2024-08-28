package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.ContractorWCDAO;
import com.wfd.dot1.cwfm.entity.CMSContractorWC;
@Service
public class ContractorWCServiceImpl implements ContractorWCService {

	 private ContractorWCDAO contractorWCDAO;

	    public ContractorWCServiceImpl(ContractorWCDAO contractorWCDAO) {
	        this.contractorWCDAO = contractorWCDAO;
	    }

	    @Override
	    public List<CMSContractorWC> getWorkOrdersByContractorId(long contractorId) {
	        return contractorWCDAO.getWorkOrdersByContractorId(contractorId);
	    }
}

