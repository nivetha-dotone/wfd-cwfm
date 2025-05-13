package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.BillConfigDao;
import com.wfd.dot1.cwfm.pojo.HrChecklistItem;
@Service
public class BillConfigServiceImpl implements BillConfigService{

	@Autowired
    private BillConfigDao dao;

    @Override
    public void saveAll(List<String> kronosReports, List<String> statutoryReports, List<HrChecklistItem> checklistItems) {
        dao.clearExisting(); // truncate or delete before saving new

        dao.saveKronosReports(kronosReports);
        dao.saveStatutoryReports(statutoryReports);
        dao.saveChecklistItems(checklistItems);
    }

    @Override
    public List<String> getAllKronosReports() {
        return dao.fetchKronosReports();
    }

    @Override
    public List<String> getAllStatutoryAttachments() {
        return dao.fetchStatutoryReports();
    }

    @Override
    public List<HrChecklistItem> getAllChecklistItems() {
        return dao.fetchChecklistItems();
    }

}
