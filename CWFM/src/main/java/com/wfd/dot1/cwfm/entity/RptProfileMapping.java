package com.wfd.dot1.cwfm.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RPTPROFILEMAPPING")
public class RptProfileMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRFLMAPID")
    private Long prflMapId;

    @Column(name = "RPTPRFLID")
    private Long rptPrflId;

    @Column(name = "RPTREFID")
    private Long rptRefId;

    @Column(name = "ISACTIVE")
    private Byte isActive;

    @Column(name = "UPDATEDTM")
    private LocalDateTime updatedDtm;

	public Long getPrflMapId() {
		return prflMapId;
	}

	public void setPrflMapId(Long prflMapId) {
		this.prflMapId = prflMapId;
	}

	public Long getRptPrflId() {
		return rptPrflId;
	}

	public void setRptPrflId(Long rptPrflId) {
		this.rptPrflId = rptPrflId;
	}

	public Long getRptRefId() {
		return rptRefId;
	}

	public void setRptRefId(Long rptRefId) {
		this.rptRefId = rptRefId;
	}

	public Byte getIsActive() {
		return isActive;
	}

	public void setIsActive(Byte isActive) {
		this.isActive = isActive;
	}

	public LocalDateTime getUpdatedDtm() {
		return updatedDtm;
	}

	public void setUpdatedDtm(LocalDateTime updatedDtm) {
		this.updatedDtm = updatedDtm;
	}

    // Getters and setters
}
