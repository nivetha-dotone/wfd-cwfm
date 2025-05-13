package com.wfd.dot1.cwfm.service;

import java.util.List;

import com.wfd.dot1.cwfm.pojo.HrChecklistItem;

public interface BillConfigService {

	void saveAll(List<String> kronosReports, List<String> statutoryReports, List<HrChecklistItem> checklistItems);

    List<String> getAllKronosReports();
    List<String> getAllStatutoryAttachments();
    List<HrChecklistItem> getAllChecklistItems();
}
