package com.wfd.dot1.cwfm.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REPORTPROFILE")
public class ReportProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RPTPRFLID")
    private Long rptPrflId;

    @Column(name = "PROFILENAME")
    private String profileName;

    @Column(name = "ISACTIVE")
    private Byte isActive;

    @Column(name = "UPDATEDTM")
    private LocalDateTime updatedDtm;

	public Long getRptPrflId() {
		return rptPrflId;
	}

	public void setRptPrflId(Long rptPrflId) {
		this.rptPrflId = rptPrflId;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
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

