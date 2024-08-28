package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.entity.WorkOrderLLWC;

public interface WorkOrderLLWCDAO {
    List<WorkOrderLLWC> getWorkOrdersByContractorId(long contractorId);
    // Other methods
}