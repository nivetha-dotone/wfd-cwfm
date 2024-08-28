package com.wfd.dot1.cwfm.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "REPORTPREFERENCESSELECTION")
public class ReportPreferencesSelection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SELID")
    private Long selId;

    @Column(name = "RPTREFID")
    private Long rptRefId;

    @Column(name = "RPTSELECTION")
    private Long rptSelection;

    
    @ManyToOne
    @JoinColumn(name = "RPTREFID", insertable = false, updatable = false) // Assuming this is the foreign key column referencing the report preferences table
    private ReportPreferences reportPreferences;

    @ManyToOne
    @JoinColumn(name = "RPTSELECTION", insertable = false, updatable = false) // Assuming this is the foreign key column referencing the general master table
    private GeneralMaster generalMaster;

    @Column(name = "UPDATEDTM")
    private LocalDateTime updatedDtm;

	public Long getSelId() {
		return selId;
	}

	public void setSelId(Long selId) {
		this.selId = selId;
	}

	public Long getRptRefId() {
		return rptRefId;
	}

	public void setRptRefId(Long rptRefId) {
		this.rptRefId = rptRefId;
	}

	public Long getRptSelection() {
		return rptSelection;
	}

	public void setRptSelection(Long rptSelection) {
		this.rptSelection = rptSelection;
	}

	public LocalDateTime getUpdatedDtm() {
		return updatedDtm;
	}

	public void setUpdatedDtm(LocalDateTime updatedDtm) {
		this.updatedDtm = updatedDtm;
	}

	public ReportPreferences getReportPreferences() {
		return reportPreferences;
	}

	public void setReportPreferences(ReportPreferences reportPreferences) {
		this.reportPreferences = reportPreferences;
	}

	public GeneralMaster getGeneralMaster() {
		return generalMaster;
	}

	public void setGeneralMaster(GeneralMaster generalMaster) {
		this.generalMaster = generalMaster;
	}

    // Getters and setters
}

