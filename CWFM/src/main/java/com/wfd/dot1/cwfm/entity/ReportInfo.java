package com.wfd.dot1.cwfm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REPORTINFO")
public class ReportInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REFID")
    private Long refId;

    @Column(name = "RPTREFID")
    private Long rptRefId;

    @Column(name = "SPNAMEWITHPARAM")
    private String spNameWithParam;

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public Long getRptRefId() {
		return rptRefId;
	}

	public void setRptRefId(Long rptRefId) {
		this.rptRefId = rptRefId;
	}

	public String getSpNameWithParam() {
		return spNameWithParam;
	}

	public void setSpNameWithParam(String spNameWithParam) {
		this.spNameWithParam = spNameWithParam;
	}

    // Getters and setters
}
