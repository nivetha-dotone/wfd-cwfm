package com.wfd.dot1.cwfm.service;

import java.util.List;

import com.wfd.dot1.cwfm.dao.WorkOrderLLWCDAO;
import com.wfd.dot1.cwfm.entity.WorkOrderLLWC;

public class WorkOrderLLWCServiceImpl implements WorkOrderLLWCService {

	  private WorkOrderLLWCDAO workOrderLLWCDAO;

	    public WorkOrderLLWCServiceImpl(WorkOrderLLWCDAO workOrderLLWCDAO) {
	        this.workOrderLLWCDAO = workOrderLLWCDAO;
	    }

	    @Override
	    public List<WorkOrderLLWC> getWorkOrdersByContractorId(long contractorId) {
	        return workOrderLLWCDAO.getWorkOrdersByContractorId(contractorId);
	    }
}