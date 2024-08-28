package com.wfd.dot1.cwfm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wfd.dot1.cwfm.entity.CMSGMType;
import com.wfd.dot1.cwfm.entity.GeneralMaster;
import com.wfd.dot1.cwfm.entity.ReportPreferences;
import com.wfd.dot1.cwfm.entity.ReportPreferencesSelection;

@Repository
public class ReportPreferencesDaoImpl implements ReportPreferencesDao{
	 @Autowired
	 @PersistenceContext
	    private EntityManager entityManager;
	 @Autowired
	    private SessionFactory sessionFactory;
	 @Override
	    @Transactional
	    public void addReportPreferenceSelection(ReportPreferencesSelection selection) {
		 entityManager.persist(selection);
	    }
	 
	    @Transactional
	    public List<GeneralMaster> getAllParameters() {
	        String hql = "SELECT gmtype.gmType, gm.gmname FROM GeneralMaster gm " +
	                     "INNER JOIN CMSGMType gmtype ON gm.gmtypeid = gmtype.gmTypeId";
	        return entityManager.createQuery(hql).getResultList();
	    }
	    @Override
	    public GeneralMaster findById(Long id) {
	        return entityManager.find(GeneralMaster.class, id);
	    }
//	    @Transactional
//	    public List<String> getAllGmTypes() {
//	        String hql = "SELECT gmType FROM CMSGMType";
//	        return entityManager.createQuery(hql, String.class).getResultList();
//	    }
	    @Transactional
	    public List<CMSGMType> getAllGmTypes() {
	        String hql = "SELECT gmType FROM CMSGMType gmType";
	        return entityManager.createQuery(hql, CMSGMType.class).getResultList();
	    }

	    @Override
	    @Transactional
	    public void addReport(ReportPreferences report) {
	        entityManager.persist(report);
	        entityManager.flush();
	    }
	    
	    @Override
	    @Transactional
	    public List<Object[]> getReportInfo() {
	        String hql = "SELECT rpt.rptName, rpt.rptLink, rpt.rptDescription FROM ReportPreferences rpt";
	        return entityManager.createQuery(hql).getResultList();
	    }

	    @Override
	    @Transactional
	    public List<ReportPreferences> getReportList() {
	        String hql = "SELECT rpt FROM ReportPreferences rpt";
	        return entityManager.createQuery(hql, ReportPreferences.class).getResultList();
	    }
	    
//
//	 @Transactional
//	    @Override
//	    public void addReport(ReportPreferences report) {
//	        String sql = "INSERT INTO reportpreferences (rptlink, rptname, rptdescription, isactive, updatedtm) " +
//	                     "VALUES (?, ?, ?, ?, ?)";
//	        Object[] params = {
//	            report.getRptLink(),
//	            report.getRptName(),
//	            report.getRptDescription(),
//	            report.getIsActive(),
//	            LocalDateTime.now() // Assuming updatedDtm is set to current timestamp
//	        };
//	        jdbcTemplate.update(sql, params);
//
//	        // Get the ID of the inserted report
//	        Long rptRefId = jdbcTemplate.queryForObject("SELECT MAX(rptrefid) FROM reportpreferences", Long.class);
//
//	        // Insert selections into REPORTPREFERENCESSELECTION
//	        for (Long parameterId : report.getSelectedParameterIds()) {
//	            String selectionSql = "INSERT INTO reportpreferencesselection (rptrefid, rptselection, updatedtm) " +
//	                                  "VALUES (?, ?, ?)";
//	            Object[] selectionParams = {
//	                rptRefId,
//	                parameterId,
//	                LocalDateTime.now() // Assuming updatedDtm is set to current timestamp
//	            };
//	            jdbcTemplate.update(selectionSql, selectionParams);
//	        }
//	    }

	    @Override
	    public ReportPreferences getReportPreferenceById(Long reportId) {
	        return entityManager.find(ReportPreferences.class, reportId);
	    }
//	    @Override
//	    public List<Object[]> getAllReportPreferencesWithReportId(Long reportRefId) {
//	        String query = "SELECT RP.rptName, RP.rptDescription, GM.gmname AS ParameterName, "
//	                + "GM.gmdescription AS ParameterDescription, GM.gmtypeid AS ParameterTypeID, "
//	                + "GMT.gmType AS ParameterType "
//	                + "FROM ReportPreferences RP "
//	                + "JOIN ReportPreferencesSelection RPS ON RP.rptRefId = RPS.rptRefId "
//	                + "JOIN GeneralMaster GM ON RPS.rptSelection = GM.gmid "
//	                + "JOIN CMSGMType GMT ON GM.gmtypeid = GMT.gmTypeId "
//	                + "WHERE RP.rptRefId = :reportRefId";
//
//	        return entityManager.createQuery(query)
//	                .setParameter("reportRefId", reportRefId)
//	                .getResultList();
//	    }
	    
	    @Override
	    public List<Object[]> getAllReportPreferencesWithReportId(Long reportRefId) {
	        String query = "SELECT RP.rptName, RP.rptDescription, GM.gmname AS ParameterName, "
	                + "GM.gmdescription AS ParameterDescription, GM.gmtypeid AS ParameterTypeID, "
	                + "GMT.gmType AS ParameterType, GM.gmType AS GmType "
	                + "FROM ReportPreferences RP "
	                + "JOIN ReportPreferencesSelection RPS ON RP.rptRefId = RPS.rptRefId "
	                + "JOIN GeneralMaster GM ON RPS.rptSelection = GM.gmid "
	                + "JOIN CMSGMType GMT ON GM.gmtypeid = GMT.gmTypeId "
	                + "WHERE RP.rptRefId = :reportRefId";

	        return entityManager.createQuery(query)
	                .setParameter("reportRefId", reportRefId)
	                .getResultList();
	    }

	    @Override
	    public List<String> getParameterValuesByType(Long reportRefId, String parameterType) {
	        String query = "SELECT GM.gmname FROM GeneralMaster GM " +
	                       "JOIN ReportPreferencesSelection RPS ON GM.gmid = RPS.rptSelection " +
	                       "JOIN ReportPreferences RP ON RPS.rptRefId = RP.rptRefId " +
	                       "WHERE RP.rptRefId = :reportRefId AND GM.gmType = :parameterType";

	        return entityManager.createQuery(query, String.class)
	                            .setParameter("reportRefId", reportRefId)
	                            .setParameter("parameterType", parameterType)
	                            .getResultList();
	    }
	    @Override
	    public List<ReportPreferences> getAllReportPreferencesWithParameters(Long reportRefId) {
	        String query = "SELECT RP.rptName, RP.rptDescription, GM.gmname AS ParameterName, "
	                + "GM.gmdescription AS ParameterDescription, GM.gmtypeid AS ParameterTypeID, "
	                + "GMT.gmType AS ParameterType "
	                + "FROM ReportPreferences RP "
	                + "JOIN ReportPreferencesSelection RPS ON RP.rptRefId = RPS.rptRefId "
	                + "JOIN GeneralMaster GM ON RPS.rptSelection = GM.gmid "
	                + "JOIN CMSGMType GMT ON GM.gmtypeid = GMT.gmTypeId "
	                + "WHERE RP.rptRefId = :reportRefId";
	        
	        return entityManager.createQuery(query, ReportPreferences.class)
	                .setParameter("reportRefId", reportRefId)
	                .getResultList();
	    }
//	    @Override
//	    public List<ReportPreferences> getAllReportPreferencesWithParameters(Long reportRefId) {
//	        String query = "SELECT RP.RPTNAME, RP.RPTDESCRIPTION, GM.GMNAME AS ParameterName, "
//	                + "GM.GMDESCRIPTION AS ParameterDescription, GM.GMTYPEID AS ParameterTypeID, "
//	                + "GMT.GMTYPE AS ParameterType "
//	                + "FROM REPORTPREFERENCES RP "
//	                + "JOIN REPORTPREFERENCESSELECTION RPS ON RP.RPTREFID = RPS.RPTREFID "
//	                + "JOIN CMSGENERALMASTER GM ON RPS.RPTSELECTION = GM.GMID "
//	                + "JOIN CMSGMTYPE GMT ON GM.GMTYPEID = GMT.GMTYPEID "
//	                + "WHERE RP.RPTREFID = :reportRefId";
//	        
//	        return entityManager.createQuery(query, ReportPreferences.class)
//	                .setParameter("reportRefId", reportRefId)
//	                .getResultList();
//	    }
	    
	    @Override
	    public List<ReportPreferences> getAllReportPreferences() {
	        String hql = "FROM ReportPreference";
	        return entityManager.createQuery(hql, ReportPreferences.class).getResultList();
	    }
}
