package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wfd.dot1.cwfm.dao.ReportPreferencesDao;
import com.wfd.dot1.cwfm.entity.CMSGMType;
import com.wfd.dot1.cwfm.entity.GeneralMaster;
import com.wfd.dot1.cwfm.entity.ReportPreferences;
import com.wfd.dot1.cwfm.entity.ReportPreferencesSelection;

@Service
public class ReportPreferencesServiceImpl implements ReportPreferencesService{
	 @Autowired
	    private ReportPreferencesDao reportPreferencesDao;

	    
	    @Override
	    public List<GeneralMaster> getAllParameters() {
	        return reportPreferencesDao.getAllParameters();
	    }

	    @Override
	    public void addReport(ReportPreferences report) {
	        reportPreferencesDao.addReport(report);
	    }

		@Override
		public List<Object[]> getReportInfo() {
			return reportPreferencesDao.getReportInfo();
		}

		@Override
		public List<CMSGMType> getAllGmTypes() {
			return reportPreferencesDao.getAllGmTypes();
		}

		@Override
		public void addReportPreferenceSelection(ReportPreferencesSelection selection) {
			 reportPreferencesDao.addReportPreferenceSelection(selection);
			
		}

		@Override
		public GeneralMaster findById(Long gmTypeId) {
			return reportPreferencesDao.findById(gmTypeId);
		}
		 @Override
		    @Transactional
		    public ReportPreferences getReportPreferenceById(Long reportId) {
		        return reportPreferencesDao.getReportPreferenceById(reportId);
		    }

		    @Override
		    @Transactional
		    public List<ReportPreferences> getAllReportPreferences() {
		        return reportPreferencesDao.getAllReportPreferences();
		    }
		    
		    @Override
		    @Transactional
		    public List<ReportPreferences> getReportList() {
		        return reportPreferencesDao.getReportList();
		    }

			@Override
			public List<ReportPreferences> getAllReportPreferencesWithParameters(Long reportRefId) {
				return reportPreferencesDao.getAllReportPreferencesWithParameters(reportRefId);
			}

			@Override
			public List<Object[]> getAllReportPreferencesWithReportId(Long reportRefId) {
				return reportPreferencesDao.getAllReportPreferencesWithReportId( reportRefId);
			}
			@Override
		    public List<String> getParameterValuesByType(Long reportRefId, String parameterType) {
		        return reportPreferencesDao.getParameterValuesByType(reportRefId, parameterType);
		    }
			
}
