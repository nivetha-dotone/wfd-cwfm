package com.wfd.dot1.cwfm.pojo;

import java.util.Date;

public class CMSContrPemm {
	private Long refId;
    private Long contractorId;
    private Long unitId;
    private String managerNm;
    private String managerEmail;
    private String managerMobile;
    private Date periodStartDt;
    private Date periodEndDt;
    private String pfNum;
    private Date pfApplyDt;
    private String esicNum;
    private boolean rcValidated;

    public Long getRefId() {
        return refId;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }

    public Long getContractorId() {
        return contractorId;
    }

    public void setContractorId(Long contractorId) {
        this.contractorId = contractorId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
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
    
   
    private Contractor contractor;

	public Contractor getContractor() {
		return contractor;
	}

	public void setContractor(Contractor contractor) {
		this.contractor = contractor;
	}
}
