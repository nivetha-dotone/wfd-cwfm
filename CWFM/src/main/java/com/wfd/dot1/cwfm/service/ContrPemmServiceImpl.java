package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.ContrPemmDAO;
import com.wfd.dot1.cwfm.entity.CMSContrPemm;
@Service
public class ContrPemmServiceImpl implements ContrPemmService{

	 private ContrPemmDAO contrPemmDAO;

	    public ContrPemmServiceImpl(ContrPemmDAO contrPemmDAO) {
	        this.contrPemmDAO = contrPemmDAO;
	    }

	    @Override
	    public List<CMSContrPemm> getMappingsByContractorId(long contractorId) {
	        return contrPemmDAO.getMappingsByContractorId(contractorId);
	    }

		@Override
		public List<CMSContrPemm> getMappingsByContractorIdAndUnitId(long contractorId, long unitId) {
			// TODO Auto-generated method stub
			 return contrPemmDAO.getMappingsByContractorIdAndUnitId(contractorId,unitId);
		}

		@Override
		public CMSContrPemm getMappingByContractorIdAndUnitId(Long id, Long principalEmployerId) {
			 return contrPemmDAO.getMappingByContractorIdAndUnitId(id,principalEmployerId);
		}
}
