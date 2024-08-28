package com.wfd.dot1.cwfm.service;

import java.util.List;

import com.wfd.dot1.cwfm.entity.CMSGMType;
import com.wfd.dot1.cwfm.entity.GeneralMaster;
import com.wfd.dot1.cwfm.entity.ReportPreferences;
import com.wfd.dot1.cwfm.entity.ReportPreferencesSelection;

public interface ReportPreferencesService {
//	 List<ReportPreferences> getAllReports();
//	    void addReport(ReportPreferences report);
	    List<GeneralMaster> getAllParameters();
	    void addReport(ReportPreferences report);
	    List<Object[]> getReportInfo();
		List<CMSGMType> getAllGmTypes();
		void addReportPreferenceSelection(ReportPreferencesSelection selection);
		GeneralMaster findById(Long gmTypeId);
		ReportPreferences getReportPreferenceById(Long reportId);
	    List<ReportPreferences> getAllReportPreferences();
	    List<ReportPreferences> getAllReportPreferencesWithParameters(Long reportRefId);
		List<ReportPreferences> getReportList();
		List<Object[]> getAllReportPreferencesWithReportId(Long reportRefId);
		List<String> getParameterValuesByType(Long reportRefId, String parameterType);
}
