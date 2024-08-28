package com.wfd.dot1.cwfm.service;

import java.util.List;

import com.wfd.dot1.cwfm.entity.WorkOrderLLWC;

public interface WorkOrderLLWCService {
    List<WorkOrderLLWC> getWorkOrdersByContractorId(long contractorId);
    // Other methods
}