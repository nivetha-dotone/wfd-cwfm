package com.wfd.dot1.cwfm.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name = "CMSCONTRPEMM")
public class CMSContrPemm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long refId;
    private long contractorId;
    private long unitId;
    private String managerNm;
    private String managerEmail;
    private String managerMobile;
    private Date periodStartDt;
    private Date periodEndDt;
    private String pfNum;
    private Date pfApplyDt;
    private String esicNum;
    private boolean rcValidated;

    public long getRefId() {
        return refId;
    }

    public void setRefId(long refId) {
        this.refId = refId;
    }

    public long getContractorId() {
        return contractorId;
    }

    public void setContractorId(long contractorId) {
        this.contractorId = contractorId;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public String getManagerNm() {
        return managerNm;
    }

    public void setManagerNm(String managerNm) {
        this.managerNm = managerNm;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getManagerMobile() {
        return managerMobile;
    }

    public void setManagerMobile(String managerMobile) {
        this.managerMobile = managerMobile;
    }

    public Date getPeriodStartDt() {
        return periodStartDt;
    }

    public void setPeriodStartDt(Date periodStartDt) {
        this.periodStartDt = periodStartDt;
    }

    public Date getPeriodEndDt() {
        return periodEndDt;
    }

    public void setPeriodEndDt(Date periodEndDt) {
        this.periodEndDt = periodEndDt;
    }

    public String getPfNum() {
        return pfNum;
    }

    public void setPfNum(String pfNum) {
        this.pfNum = pfNum;
    }

    public Date getPfApplyDt() {
        return pfApplyDt;
    }

    public void setPfApplyDt(Date pfApplyDt) {
        this.pfApplyDt = pfApplyDt;
    }

    public String getEsicNum() {
        return esicNum;
    }

    public void setEsicNum(String esicNum) {
        this.esicNum = esicNum;
    }

    public boolean getRcValidated() {
        return rcValidated;
    }

    public void setRcValidated(boolean b) {
        this.rcValidated = b;
    }
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contractorId", insertable = false, updatable = false)
    private CMSContractor contractor;

	public CMSContractor getContractor() {
		return contractor;
	}

	public void setContractor(CMSContractor contractor) {
		this.contractor = contractor;
	}
}
