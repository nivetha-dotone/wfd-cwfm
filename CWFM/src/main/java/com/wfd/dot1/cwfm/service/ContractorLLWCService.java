package com.wfd.dot1.cwfm.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.ContractorRegistrationLLWCDAO;
import com.wfd.dot1.cwfm.dao.WorkorderDao;
import com.wfd.dot1.cwfm.dto.WorkOrderInfoDTO;
import com.wfd.dot1.cwfm.pojo.CMSContractorRegistrationLLWC;
import com.wfd.dot1.cwfm.pojo.Workorder;

@Service
public class ContractorLLWCService {

    @Autowired
    private ContractorRegistrationLLWCDAO llwcDAO;

    @Autowired
    private WorkorderDao workOrderDAO;

    public void saveLLWCRecords(WorkOrderInfoDTO dto, String createdBy) {
        List<String> selectedWOsRaw = dto.getSelectedWOs();

        List<String> cleanedWOs = selectedWOsRaw.stream()
                .map(code -> code.replaceAll("\\(.*\\)", "").trim()) // strip (New)
                .collect(Collectors.toList());

        Map<String, Workorder> workOrderMap = workOrderDAO.getWorkOrdersByCodes(cleanedWOs);

        List<CMSContractorRegistrationLLWC> recordsToInsert = new ArrayList<>();

        Timestamp now = new Timestamp(System.currentTimeMillis());

        for (String rawWO : selectedWOsRaw) {
            String cleanWO = rawWO.replaceAll("\\(.*\\)", "").trim();
            Workorder wo = workOrderMap.get(cleanWO);
            if (wo == null) continue;

            for (String license : dto.getLicenseId()) {
                String[] parts = license.split("_", 2);
                if (parts.length < 2) continue;

                String licenseType = parts[0];
                String wcCode = parts[1];

                // Check for duplicate
                if (llwcDAO.exists(dto.getContractorRegId(), Integer.parseInt(wo.getWorkorderId()), wcCode)) {
                    continue;
                }

                CMSContractorRegistrationLLWC record = new CMSContractorRegistrationLLWC();
               // record.setContractorRegLLWCId(llwcDAO.getNextId());
                record.setContractorRegId(dto.getContractorRegId());
                record.setContractorId(Integer.parseInt(dto.getContractorId()));
                record.setUnitId(Integer.parseInt(dto.getUnitId()));
                record.setWorkOrderId(Integer.parseInt(wo.getWorkorderId()));
                record.setWorkOrderNumber(wo.getSapWorkorderNumber());
                record.setLicenseType(licenseType);
                record.setWcCode(wcCode);
                record.setCreatedBy(createdBy);
                record.setUpdatedBy(createdBy);
                record.setCreatedDtm(now);
                record.setUpdatedDtm(now);

                recordsToInsert.add(record);
            }
        }

        if (!recordsToInsert.isEmpty()) {
            llwcDAO.insertLLWCRecords(recordsToInsert);
            //
        }
    }
}

